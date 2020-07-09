package io.github.happytimor.mybatis.helper.single.database.test;

import io.github.happytimor.mybatis.helper.core.function.SqlFunction;
import io.github.happytimor.mybatis.helper.core.metadata.Page;
import io.github.happytimor.mybatis.helper.core.wrapper.SelectWrapper;
import io.github.happytimor.mybatis.helper.single.database.test.domain.AgeInfo;
import io.github.happytimor.mybatis.helper.single.database.test.domain.User;
import io.github.happytimor.mybatis.helper.single.database.test.service.GenerateService;
import io.github.happytimor.mybatis.helper.single.database.test.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

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

    private final static Logger logger = LoggerFactory.getLogger(MethodTests.class);
    @Resource
    private GenerateService generateService;

    @Resource
    private UserService userService;

    /**
     * 单条插入测试
     */
    @Test
    public void insert() {
        User user = this.generateService.generateOne();
        assert user.getId() == null;
        this.userService.insert(user);
        assert user.getId() != null;

        String strangeName = user.getStrangeName();
        assert strangeName != null && !strangeName.equals("");

        User dbExistsUser = this.userService.selectById(user.getId());
        assert Objects.equals(user, dbExistsUser);

        User strangeUser = this.userService.selectOne(new SelectWrapper<User>()
                .eq(User::getStrangeName, strangeName));
        assert strangeUser != null;

        this.userService.deleteById(user.getId());
        User dbUnExistsUser = this.userService.selectById(user.getId());
        assert dbUnExistsUser == null;
    }

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

    /**
     * 条数查询测试
     */
    @Test
    public void selectCount() {
        this.generateService.generateBatch(((flag, userList) -> {
            long dbCount = this.userService.selectCount(new SelectWrapper<User>().eq(User::getFlag, flag));
            assert userList.size() == dbCount;

            List<User> dbUserList = this.userService.selectList(new SelectWrapper<User>().eq(User::getFlag, flag));
            for (User user : dbUserList) {
                long onlyOne = this.userService.selectCount(new SelectWrapper<User>()
                        .eq(User::getId, user.getId())
                        .eq(User::getAge, user.getAge())
                        .eq(User::getFlag, user.getFlag())
                        .eq(User::getName, user.getName())
                        .eq(User::getLastLoginTime, user.getLastLoginTime())
                        .eq(User::getMarried, user.getMarried())
                        .eq(User::getUserGrade, user.getUserGrade())
                );
                assert onlyOne == 1;
            }
        }));
    }

    /**
     * 唯一索引冲突更新测试
     */
    @Test
    public void insertOrUpdateWithUniqueIndex() {
        this.generateService.generateBatch((flag, userList) -> {
            List<User> dbUserList = this.userService.selectList(new SelectWrapper<User>().eq(User::getFlag, flag));
            for (User user : dbUserList) {
                //唯一索引冲突更新测试(id冲突)
                User newUser = this.generateService.generateOne(flag);
                newUser.setId(user.getId());
                this.userService.insertOrUpdateWithUniqueIndex(newUser);

                User dbNewUser = this.userService.selectById(user.getId());
                assert Objects.equals(dbNewUser, newUser);
            }
        });
    }

    /**
     * 单值查询
     */
    @Test
    public void selectSingleValue() {
        this.generateService.generateBatch((flag, userList) -> {
            int maxAge = -1;
            int sumAge = 0;
            for (User user : userList) {
                if (maxAge < user.getAge()) {
                    maxAge = user.getAge();
                }
                sumAge += user.getAge();
            }

            Number dbCount = this.userService.selectSingleValue(new SelectWrapper<User>()
                    .select(SqlFunction.count(User::getId))
                    .eq(User::getFlag, flag));
            assert dbCount.intValue() == userList.size();

            Number dbMaxAge = this.userService.selectSingleValue(new SelectWrapper<User>()
                    .select(SqlFunction.max(User::getAge))
                    .eq(User::getFlag, flag));
            assert dbMaxAge.intValue() == maxAge;

            Number dbSumAge = this.userService.selectSingleValue(new SelectWrapper<User>()
                    .select(SqlFunction.sum(User::getAge))
                    .eq(User::getFlag, flag));
            assert dbSumAge.intValue() == sumAge;

        });
    }

    /**
     * 对象转换测试
     */
    @Test
    public void selectObjectList() {
        generateService.generateBatch(1000, (flag, userList) -> {
            Map<Integer, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, x -> x));
            List<User> objectList = userService.selectObjectList(User.class, new SelectWrapper<User>()
                    .eq(User::getFlag, flag)
            );
            for (User user : objectList) {
                assert user.equals(userMap.get(user.getId()));
            }
        });
    }

    @Test
    public void selectObject() {
        generateService.generateBatch(1000, (flag, userList) -> {
            for (User user : userList) {
                User object = userService.selectObject(User.class, new SelectWrapper<User>()
                        .eq(User::getFlag, flag)
                        .eq(User::getId, user.getId())
                );
                assert user.equals(object);
            }
        });
    }

    /**
     * 分页测试
     */
    @Test
    public void selectPage() {
        this.generateService.generateBatch((flag, userList) -> {
            List<User> dbUserList = this.userService.selectList(new SelectWrapper<User>()
                    .eq(User::getFlag, flag)
                    .orderByAsc(User::getId)
            );

            int pageSize = 10;
            int pageNo = 0;
            while (true) {
                pageNo++;
                Page<User> page = this.userService.selectPage(pageNo, pageSize, new SelectWrapper<User>()
                        .eq(User::getFlag, flag)
                        .orderByAsc(User::getId));
                assert page.getTotal() == userList.size();

                List<User> records = page.getRecords();
                if (CollectionUtils.isEmpty(records)) {
                    break;
                }
                //数据库分页和内存分页做比较
                long sum = records.stream().map(User::getId).reduce(0, Integer::sum);
                List<User> partList = dbUserList.subList((pageNo - 1) * pageSize, Math.min(pageNo * pageSize, userList.size()));
                long checkSum = partList.stream().map(User::getId).reduce(0, Integer::sum);
                assert sum == checkSum;
            }
        });
    }

}
