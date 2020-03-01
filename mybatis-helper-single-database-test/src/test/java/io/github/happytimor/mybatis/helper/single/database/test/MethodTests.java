package io.github.happytimor.mybatis.helper.single.database.test;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import io.github.happytimor.mybatis.helper.core.wrapper.SelectWrapper;
import io.github.happytimor.mybatis.helper.single.database.test.domain.User;
import io.github.happytimor.mybatis.helper.single.database.test.service.GenerateService;
import io.github.happytimor.mybatis.helper.single.database.test.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 方法测试
 *
 * @author chenpeng
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MethodTests {

    @Resource
    private GenerateService generateService;

    @Resource
    private UserService userService;

    /**
     * 单主键查询
     */
    @Test
    public void selectById() {
        this.generateService.generateBatch((flag, userList) -> {
            List<User> dbUserList = this.userService.selectList(new SelectWrapper<User>().eq(User::getFlag, flag));
            for (User user : dbUserList) {
                User selectByIdUser = this.userService.selectById(user.getId());
                assert Objects.equals(user, selectByIdUser);
            }
        });
    }

    /**
     * 多主键批量查询
     */
    @Test
    public void selectByIdList() {
        this.generateService.generateBatch((flag, userList) -> {
            List<User> dbUserList = this.userService.selectList(new SelectWrapper<User>().eq(User::getFlag, flag).orderByAsc(User::getId));
            List<Integer> idList = dbUserList.stream().map(User::getId).collect(Collectors.toList());

            List<User> selectByIdListUserList = this.userService.selectByIdList(idList);
            for (int i = 0; i < selectByIdListUserList.size(); i++) {
                User selectByIdUser = selectByIdListUserList.get(i);
                User dbUser = dbUserList.get(i);
                assert Objects.equals(dbUser, selectByIdUser);
            }

            User user = dbUserList.get(0);
            List<User> users = this.userService.selectByIdList(Collections.singletonList(user.getId()));
            assert users.size() == 1 && Objects.equals(user, users.get(0));
        });
    }

    /**
     * 单主键删除
     */
    @Test
    public void deleteById() {
        this.generateService.generateBatch((flag, userList) -> {
            List<User> dbUserList = this.userService.selectList(new SelectWrapper<User>().eq(User::getFlag, flag).orderByAsc(User::getId));
            int restCount = dbUserList.size();
            for (User user : dbUserList) {
                assert this.userService.deleteById(user.getId());
                restCount--;
                Number dbRestCount = this.userService.selectCount(new SelectWrapper<User>().eq(User::getFlag, flag));
                assert dbRestCount.intValue() == restCount;
            }
        });
    }

    /**
     * 多主键批量删除
     */
    @Test
    public void deleteByIdList() {
        this.generateService.generateBatch((flag, userList) -> {
            List<User> dbUserList = this.userService.selectList(new SelectWrapper<User>().eq(User::getFlag, flag).orderByAsc(User::getId));
            Map<Integer, List<User>> ageMap = dbUserList.stream().collect(Collectors.groupingBy(User::getAge));

            int restCount = dbUserList.size();
            for (List<User> tmpDeleteList : ageMap.values()) {
                List<Integer> idList = tmpDeleteList.stream().map(User::getId).collect(Collectors.toList());
                assert this.userService.deleteByIdList(idList) == idList.size();
                restCount -= idList.size();
                Number dbRestCount = this.userService.selectCount(new SelectWrapper<User>().eq(User::getFlag, flag));
                assert dbRestCount.intValue() == restCount;
            }
        });
    }

    /**
     * 单主键更新测试
     */
    @Test
    public void updateById() {
        this.generateService.generateBatch((flag, userList) -> {
            List<User> dbUserList = this.userService.selectList(new SelectWrapper<User>().eq(User::getFlag, flag).orderByAsc(User::getId));
            for (User user : dbUserList) {
                User newUser = this.generateService.generateOne(flag);
                newUser.setId(user.getId());
                BeanUtils.copyProperties(newUser, user);
                this.userService.updateById(user);
            }

            List<User> updateUserList = this.userService.selectList(new SelectWrapper<User>().eq(User::getFlag, flag).orderByAsc(User::getId));
            for (int i = 0; i < updateUserList.size(); i++) {
                assert Objects.equals(updateUserList.get(i), dbUserList.get(i));
            }
        });
    }

    /**
     * 多主键更新测试
     */
    @Test
    public void batchUpdateById() {
        this.generateService.generateBatch((flag, userList) -> {
            List<User> dbUserList = this.userService.selectList(new SelectWrapper<User>().eq(User::getFlag, flag).orderByAsc(User::getId));
            Map<Integer, List<User>> ageMap = dbUserList.stream().collect(Collectors.groupingBy(User::getAge));
            for (List<User> tmpUserList : ageMap.values()) {
                for (User user : tmpUserList) {
                    User newUser = this.generateService.generateOne(flag);
                    newUser.setId(user.getId());
                    BeanUtils.copyProperties(newUser, user);
                }
                this.userService.batchUpdateById(tmpUserList);
            }

            List<User> updateUserList = this.userService.selectList(new SelectWrapper<User>().eq(User::getFlag, flag).orderByAsc(User::getId));
            updateUserList.sort(Comparator.comparing(User::getId));
            for (int i = 0; i < updateUserList.size(); i++) {
                assert Objects.equals(updateUserList.get(i), dbUserList.get(i));
            }
        });
    }
}
