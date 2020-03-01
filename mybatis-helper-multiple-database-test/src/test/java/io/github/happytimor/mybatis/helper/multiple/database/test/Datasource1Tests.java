package io.github.happytimor.mybatis.helper.multiple.database.test;

import io.github.happytimor.mybatis.helper.core.metadata.Page;
import io.github.happytimor.mybatis.helper.core.wrapper.DeleteWrapper;
import io.github.happytimor.mybatis.helper.core.wrapper.SelectWrapper;
import io.github.happytimor.mybatis.helper.core.wrapper.UpdateWrapper;
import io.github.happytimor.mybatis.helper.multiple.database.test.domain.UserInfo;
import io.github.happytimor.mybatis.helper.multiple.database.test.service.UserInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author chenpeng
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Datasource1Tests {
    @Resource
    private UserInfoService userInfoService;

    /**
     * 单条插入、根据主键查找、根据主键删除联合测试
     */
    @Test
    public void insert() {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("mybatis-helper");
        userInfoService.insert(userInfo);
        assert userInfo.getId() != null;

        UserInfo dbUserInfo = userInfoService.selectById(userInfo.getId());
        assert dbUserInfo.getName().equals(userInfo.getName());

        boolean deleteSuccess = userInfoService.deleteById(userInfo.getId());
        assert deleteSuccess;

        dbUserInfo = userInfoService.selectById(userInfo.getId());
        assert dbUserInfo == null;
    }

    /**
     * 根据id更新测试
     */
    @Test
    public void updateById() {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("mybatis-helper");
        userInfoService.insert(userInfo);
        assert userInfo.getId() != null;

        String newName = "mybatis-helper-" + System.currentTimeMillis();
        userInfo.setName(newName);
        userInfo.setAge(22);
        userInfo.setMarried(true);
        boolean updateSuccess = userInfoService.updateById(userInfo);
        assert updateSuccess;

        UserInfo dbUserInfo = userInfoService.selectById(userInfo.getId());
        assert newName.equals(dbUserInfo.getName()) && dbUserInfo.getAge() == 22 && dbUserInfo.getMarried();
        boolean deleteSuccess = userInfoService.deleteById(dbUserInfo.getId());
        assert deleteSuccess;
    }

    /**
     * 自定义条件查询测试
     */
    @Test
    public void selectList() {
        String name = "mybatis-helper-" + System.currentTimeMillis();
        UserInfo userInfo = new UserInfo();
        userInfo.setName(name);
        userInfoService.insert(userInfo);
        assert userInfo.getId() != null;

        //测试各种条件能否生效
        List<UserInfo> userList = userInfoService.selectList(new SelectWrapper<UserInfo>()
                .eq(UserInfo::getName, name + System.currentTimeMillis())
                .gt(UserInfo::getAge, 12)
                .lt(UserInfo::getAge, 13)
                .isNotNull(UserInfo::getMarried)
                .isNull(UserInfo::getAge)
                .between(UserInfo::getAge, 12, 15)
                .notBetween(UserInfo::getAge, 14, 19)
                .like(UserInfo::getName, "mybatis")
                .notLike(UserInfo::getName, "mybatis")
                .likeLeft(UserInfo::getName, "mybatis")
                .likeRight(UserInfo::getName, "mybatis")
        );
        assert userList.isEmpty();


        userList = userInfoService.selectList(new SelectWrapper<UserInfo>()
                .select(UserInfo::getName)
                .eq(UserInfo::getName, name)
        );

        assert userList.size() == 1 && userList.get(0).getName().equals(name);


        //selectOne测试
        UserInfo dbUserInfo = userInfoService.selectOne(new SelectWrapper<UserInfo>()
                .select(UserInfo::getId, UserInfo::getName)
                .eq(UserInfo::getName, name)
        );
        assert dbUserInfo.getName().equals(name);

        boolean deleteSuccess = userInfoService.deleteById(dbUserInfo.getId());
        assert deleteSuccess;
    }

    /**
     * 自定义更新测试
     */
    public void update() {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("mybatis-helper");
        userInfoService.insert(userInfo);
        assert userInfo.getId() != null;

        String newName = "mybatis-helper-" + System.currentTimeMillis();


        //自定义条件更新测试(填充很多的查询条件,主要用于检测条件是否能正常组成sql)
        int updateCount = userInfoService.update(new UpdateWrapper<UserInfo>().set(UserInfo::getAge, 12).set(UserInfo::getMarried, true).set(UserInfo::getLastLoginTime, "2019-09-09")
                .eq(UserInfo::getName, newName + System.currentTimeMillis())
                .gt(UserInfo::getAge, 12)
                .lt(UserInfo::getAge, 13)
                .isNotNull(UserInfo::getMarried)
                .isNull(UserInfo::getAge)
                .between(UserInfo::getAge, 12, 15)
                .notBetween(UserInfo::getAge, 14, 19)
                .like(UserInfo::getName, "mybatis")
                .notLike(UserInfo::getName, "mybatis")
                .likeLeft(UserInfo::getName, "mybatis")
                .likeRight(UserInfo::getName, "mybatis")
        );
        assert updateCount == 0;

        updateCount = userInfoService.update(new UpdateWrapper<UserInfo>().set(UserInfo::getAge, 12).set(UserInfo::getMarried, true).set(UserInfo::getLastLoginTime, "2019-09-09")
                .eq(UserInfo::getName, newName)
        );

        UserInfo dbUserInfo = userInfoService.selectById(userInfo.getId());
        assert updateCount == 1 && dbUserInfo.getAge() == 12 && "2019-09-09".equals(dbUserInfo.getLastLoginTime());
        boolean deleteSuccess = userInfoService.deleteById(dbUserInfo.getId());
        assert deleteSuccess;
    }

    /**
     * 唯一索引冲突更新测试
     */
    @Test
    public void insertOrUpdateWithUniqueIndex() {
        String name = "mybatis-helper-" + System.currentTimeMillis();
        UserInfo userInfo = new UserInfo();
        userInfo.setName(name);
        userInfoService.insert(userInfo);
        assert userInfo.getId() != null;


        //唯一索引冲突更新测试
        userInfo.setName("mybatis-helper");
        boolean updateSuccess = userInfoService.insertOrUpdateWithUniqueIndex(userInfo);
        assert updateSuccess;
        UserInfo dbUserInfo = userInfoService.selectById(userInfo.getId());
        assert "mybatis-helper".equals(dbUserInfo.getName());

        boolean deleteSuccess = userInfoService.deleteById(dbUserInfo.getId());
        assert deleteSuccess;
    }

    /**
     * 批量插入、根据主键批量删除、根据主键批量更新联合测试
     */
    @Test
    public void batchOperation() {
        List<UserInfo> list = new ArrayList<>();
        long now = System.currentTimeMillis();
        int total = new Random().nextInt(1000) + 10;

        for (int i = 0; i < total; i++) {
            list.add(new UserInfo("mybatis-helper-" + now + i));
        }
        userInfoService.batchInsert(list);
        List<UserInfo> userList = userInfoService.selectList(new SelectWrapper<UserInfo>().select(UserInfo::getId).likeLeft(UserInfo::getName, "mybatis-helper-" + now));
        assert userList.size() == total;

        //批量查找
        List<Integer> userIdList = userList.stream().map(UserInfo::getId).distinct().collect(Collectors.toList());
        userList = userInfoService.selectByIdList(userIdList);
        assert userList.size() == total;


        //根据id批量更新
        for (UserInfo userInfo : userList) {
            userInfo.setName("mybatis-helper");
        }
        boolean updateSuucess = userInfoService.batchUpdateById(userList);
        assert updateSuucess;
        userList = userInfoService.selectByIdList(userIdList);
        for (UserInfo userInfo : userList) {
            assert "mybatis-helper".equals(userInfo.getName());
        }


        //根据id批量删除
        int deleteCount = userInfoService.deleteByIdList(userIdList);
        assert deleteCount == total;

        //校验批量删除是否成功
        userList = userInfoService.selectByIdList(userIdList);
        assert userList.size() == 0;

    }

    /**
     * 自定义条件删除测试
     */
    @Test
    public void delete() {
        String name = "mybatis-helper-" + System.currentTimeMillis();
        UserInfo userInfo = new UserInfo();
        userInfo.setName(name);
        userInfo.setAge(11);
        userInfo.setMarried(true);
        userInfo.setLastLoginTime(LocalDateTime.now().withNano(0));
        userInfoService.insert(userInfo);
        assert userInfo.getId() != null;

        int deleteCount = userInfoService.delete(new DeleteWrapper<UserInfo>()
                .eq(UserInfo::getName, name)
                .likeLeft(UserInfo::getName, "mybatis-helper")
                .eq(UserInfo::getAge, 11)
                .eq(UserInfo::getLastLoginTime, userInfo.getLastLoginTime())
        );
        assert deleteCount == 1;

        userInfo = userInfoService.selectById(userInfo.getId());
        assert userInfo == null;
    }

    /**
     * 查询总数测试
     */
    @Test
    public void count() {
        List<UserInfo> list = new ArrayList<>();
        long now = System.currentTimeMillis();
        int total = new Random().nextInt(1000) + 10;
        for (int i = 0; i < total; i++) {
            list.add(new UserInfo("mybatis-helper-" + now + i));
        }
        userInfoService.batchInsert(list);
        List<UserInfo> userList = userInfoService.selectList(new SelectWrapper<UserInfo>()
                .select(UserInfo::getId)
                .likeLeft(UserInfo::getName, "mybatis-helper-" + now)
        );
        assert userList.size() == total;

        //总数查询测试
        long count = userInfoService.selectCount(new SelectWrapper<UserInfo>().likeLeft(UserInfo::getName, "mybatis-helper-" + now));
        assert count == total;


        List<Integer> userIdList = userList.stream().map(UserInfo::getId).distinct().collect(Collectors.toList());
        assert userIdList.size() == total;

        //根据id批量删除
        int deleteCount = userInfoService.deleteByIdList(userIdList);
        assert deleteCount == total;
    }

    /**
     * 分页测试
     */
    @Test
    public void selectPage() {
        List<UserInfo> list = new ArrayList<>();
        long now = System.currentTimeMillis();
        int total = new Random().nextInt(1000) + 10;
        for (int i = 0; i < total; i++) {
            list.add(new UserInfo("mybatis-helper-" + now + i));
        }
        userInfoService.batchInsert(list);

        Page<UserInfo> page = userInfoService.selectPage(1, 10, new SelectWrapper<UserInfo>()
                .likeLeft(UserInfo::getName, "mybatis-helper-" + now)
        );
        assert page.getRecords().size() == 10 && page.getTotal() == total;

        List<UserInfo> userList = userInfoService.selectList(new SelectWrapper<UserInfo>()
                .select(UserInfo::getId)
                .likeLeft(UserInfo::getName, "mybatis-helper-" + now)
        );
        assert userList.size() == total;


        List<Integer> userIdList = userList.stream().map(UserInfo::getId).distinct().collect(Collectors.toList());
        assert userIdList.size() == total;

        userInfoService.deleteByIdList(userIdList);
    }

    /**
     * sql注入攻击测试
     */
    @Test
    public void sqlInject() {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("mybatis-helper");
        userInfoService.insert(userInfo);
        assert userInfo.getId() != null;

        String injectSql = "mybatis-helper or 1=1";

        List<UserInfo> users = userInfoService.selectList(new SelectWrapper<UserInfo>()
                .eq(UserInfo::getName, injectSql)
                .or().like(UserInfo::getName, injectSql)
                .or().likeLeft(UserInfo::getName, injectSql)
                .or().likeRight(UserInfo::getName, injectSql)
        );

        assert users.isEmpty();

        boolean deleteSuccess = userInfoService.deleteById(userInfo.getId());
        assert deleteSuccess;
    }
}
