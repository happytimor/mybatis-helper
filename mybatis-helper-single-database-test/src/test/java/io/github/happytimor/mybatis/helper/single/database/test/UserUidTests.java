package io.github.happytimor.mybatis.helper.single.database.test;

import io.github.happytimor.mybatis.helper.core.metadata.Page;
import io.github.happytimor.mybatis.helper.core.wrapper.DeleteWrapper;
import io.github.happytimor.mybatis.helper.core.wrapper.SelectWrapper;
import io.github.happytimor.mybatis.helper.core.wrapper.UpdateWrapper;
import io.github.happytimor.mybatis.helper.single.database.test.domain.UserUid;
import io.github.happytimor.mybatis.helper.single.database.test.service.UserUidService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 测试环境: 单表,有主键但是主键名称不是id
 *
 * @author chenpeng
 * @date 2019-09-07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserUidTests {
    @Resource
    private UserUidService userUidService;

    /**
     * 单条插入、根据主键查找、根据主键删除联合测试
     */
    @Test
    public void insert() {
        UserUid userUid = new UserUid();
        userUid.setName("mybatis-helper");
        userUidService.insert(userUid);
        assert userUid.getUid() != null;

        UserUid dbUser = userUidService.selectById(userUid.getUid());
        assert dbUser.getName().equals(userUid.getName());

        boolean deleteSuccess = userUidService.deleteById(userUid.getUid());
        assert deleteSuccess;

        dbUser = userUidService.selectById(userUid.getUid());
        assert dbUser == null;
    }

    /**
     * 根据id更新测试
     */
    @Test
    public void updateById() {
        UserUid userUid = new UserUid();
        userUid.setName("mybatis-helper");
        userUidService.insert(userUid);
        assert userUid.getUid() != null;

        String newName = "mybatis-helper-" + System.currentTimeMillis();
        userUid.setName(newName);
        userUid.setAge(22);
        userUid.setMarried(true);
        boolean updateSuccess = userUidService.updateById(userUid);
        assert updateSuccess;

        UserUid dbUser = userUidService.selectById(userUid.getUid());
        assert newName.equals(dbUser.getName()) && dbUser.getAge() == 22 && dbUser.getMarried();
        boolean deleteSuccess = userUidService.deleteById(dbUser.getUid());
        assert deleteSuccess;
    }

    /**
     * 自定义条件查询测试
     */
    @Test
    public void selectList() {
        String name = "mybatis-helper-" + System.currentTimeMillis();
        UserUid userUid = new UserUid();
        userUid.setName(name);
        userUidService.insert(userUid);
        assert userUid.getUid() != null;

        //测试各种条件能否生效
        List<UserUid> userList = userUidService.selectList(new SelectWrapper<UserUid>()
                .eq(UserUid::getName, name + System.currentTimeMillis())
                .gt(UserUid::getAge, 12)
                .lt(UserUid::getAge, 13)
                .isNotNull(UserUid::getMarried)
                .isNull(UserUid::getAge)
                .between(UserUid::getAge, 12, 15)
                .notBetween(UserUid::getAge, 14, 19)
                .like(UserUid::getName, "mybatis")
                .notLike(UserUid::getName, "mybatis")
                .likeLeft(UserUid::getName, "mybatis")
                .likeRight(UserUid::getName, "mybatis")
        );
        assert userList.isEmpty();


        userList = userUidService.selectList(new SelectWrapper<UserUid>()
                .select(UserUid::getName)
                .eq(UserUid::getName, name)
        );

        assert userList.size() == 1 && userList.get(0).getName().equals(name);


        //selectOne测试
        UserUid dbUser = userUidService.selectOne(new SelectWrapper<UserUid>()
                .select(UserUid::getUid, UserUid::getName)
                .eq(UserUid::getName, name)
        );
        assert dbUser.getName().equals(name);

        boolean deleteSuccess = userUidService.deleteById(dbUser.getUid());
        assert deleteSuccess;
    }

    /**
     * 自定义更新测试
     */
    public void update() {
        UserUid userUid = new UserUid();
        userUid.setName("mybatis-helper");
        userUidService.insert(userUid);
        assert userUid.getUid() != null;

        String newName = "mybatis-helper-" + System.currentTimeMillis();


        //自定义条件更新测试(填充很多的查询条件,主要用于检测条件是否能正常组成sql)
        int updateCount = userUidService.update(new UpdateWrapper<UserUid>().set(UserUid::getAge, 12).set(UserUid::getMarried, true).set(UserUid::getBirthday, "2019-09-09")
                .eq(UserUid::getName, newName + System.currentTimeMillis())
                .gt(UserUid::getAge, 12)
                .lt(UserUid::getAge, 13)
                .isNotNull(UserUid::getMarried)
                .isNull(UserUid::getAge)
                .between(UserUid::getAge, 12, 15)
                .notBetween(UserUid::getAge, 14, 19)
                .like(UserUid::getName, "mybatis")
                .notLike(UserUid::getName, "mybatis")
                .likeLeft(UserUid::getName, "mybatis")
                .likeRight(UserUid::getName, "mybatis")
        );
        assert updateCount == 0;

        updateCount = userUidService.update(new UpdateWrapper<UserUid>().set(UserUid::getAge, 12).set(UserUid::getMarried, true).set(UserUid::getBirthday, "2019-09-09")
                .eq(UserUid::getName, newName)
        );

        UserUid dbUser = userUidService.selectById(userUid.getUid());
        assert updateCount == 1 && dbUser.getAge() == 12 && "2019-09-09".equals(dbUser.getBirthday());
        boolean deleteSuccess = userUidService.deleteById(dbUser.getUid());
        assert deleteSuccess;
    }

    /**
     * 唯一索引冲突更新测试
     */
    @Test
    public void insertOrUpdateWithUniqueIndex() {
        String name = "mybatis-helper-" + System.currentTimeMillis();
        UserUid userUid = new UserUid();
        userUid.setName(name);
        userUidService.insert(userUid);
        assert userUid.getUid() != null;


        //唯一索引冲突更新测试
        userUid.setName("mybatis-helper");
        boolean updateSuccess = userUidService.insertOrUpdateWithUniqueIndex(userUid);
        assert updateSuccess;
        UserUid dbUser = userUidService.selectById(userUid.getUid());
        assert "mybatis-helper".equals(dbUser.getName());

        boolean deleteSuccess = userUidService.deleteById(dbUser.getUid());
        assert deleteSuccess;
    }

    /**
     * 批量插入、根据主键批量删除、根据主键批量更新联合测试
     */
    @Test
    public void batchOperation() {
        List<UserUid> list = new ArrayList<>();
        long now = System.currentTimeMillis();
        int total = new Random().nextInt(1000) + 10;

        for (int i = 0; i < total; i++) {
            list.add(new UserUid("mybatis-helper-" + now + i));
        }
        userUidService.batchInsert(list);
        List<UserUid> userList = userUidService.selectList(new SelectWrapper<UserUid>().select(UserUid::getUid).likeRight(UserUid::getName, "mybatis-helper-" + now));
        assert userList.size() == total;

        //批量查找
        List<Integer> userIdList = userList.stream().map(UserUid::getUid).distinct().collect(Collectors.toList());
        userList = userUidService.selectByIdList(userIdList);
        assert userList.size() == total;


        //根据id批量更新
        for (UserUid userUid : userList) {
            userUid.setName("mybatis-helper");
        }
        boolean updateSuucess = userUidService.batchUpdateById(userList);
        assert updateSuucess;
        userList = userUidService.selectByIdList(userIdList);
        for (UserUid userUid : userList) {
            assert "mybatis-helper".equals(userUid.getName());
        }


        //根据id批量删除
        int deleteCount = userUidService.deleteByIdList(userIdList);
        assert deleteCount == total;

        //校验批量删除是否成功
        userList = userUidService.selectByIdList(userIdList);
        assert userList.size() == 0;

    }

    /**
     * 自定义条件删除测试
     */
    @Test
    public void delete() {
        String name = "mybatis-helper-" + System.currentTimeMillis();
        UserUid userUid = new UserUid();
        userUid.setName(name);
        userUid.setAge(11);
        userUid.setMarried(true);
        userUid.setBirthday("1999-09-09");
        userUidService.insert(userUid);
        assert userUid.getUid() != null;

        int deleteCount = userUidService.delete(new DeleteWrapper<UserUid>()
                .eq(UserUid::getName, name)
                .likeRight(UserUid::getName, "mybatis-helper")
                .eq(UserUid::getAge, 11)
                .eq(UserUid::getBirthday, userUid.getBirthday())
        );
        assert deleteCount == 1;

        userUid = userUidService.selectById(userUid.getUid());
        assert userUid == null;
    }

    /**
     * 查询总数测试
     */
    @Test
    public void count() {
        List<UserUid> list = new ArrayList<>();
        long now = System.currentTimeMillis();
        int total = new Random().nextInt(1000) + 10;
        for (int i = 0; i < total; i++) {
            list.add(new UserUid("mybatis-helper-" + now + i));
        }
        userUidService.batchInsert(list);
        List<UserUid> userList = userUidService.selectList(new SelectWrapper<UserUid>()
                .select(UserUid::getUid)
                .likeRight(UserUid::getName, "mybatis-helper-" + now)
        );
        assert userList.size() == total;

        //总数查询测试
        long count = userUidService.selectCount(new SelectWrapper<UserUid>().likeRight(UserUid::getName, "mybatis-helper-" + now));
        assert count == total;


        List<Integer> userIdList = userList.stream().map(UserUid::getUid).distinct().collect(Collectors.toList());
        assert userIdList.size() == total;

        //根据id批量删除
        int deleteCount = userUidService.deleteByIdList(userIdList);
        assert deleteCount == total;
    }

    /**
     * 分页测试
     */
    @Test
    public void selectPage() {
        List<UserUid> list = new ArrayList<>();
        long now = System.currentTimeMillis();
        int total = new Random().nextInt(1000) + 10;
        for (int i = 0; i < total; i++) {
            list.add(new UserUid("mybatis-helper-" + now + i));
        }
        userUidService.batchInsert(list);

        Page<UserUid> page = userUidService.selectPage(1, 10, new SelectWrapper<UserUid>()
                .likeRight(UserUid::getName, "mybatis-helper-" + now)
        );
        assert page.getRecords().size() == 10 && page.getTotal() == total;

        List<UserUid> userList = userUidService.selectList(new SelectWrapper<UserUid>()
                .select(UserUid::getUid)
                .likeRight(UserUid::getName, "mybatis-helper-" + now)
        );
        assert userList.size() == total;


        List<Integer> userIdList = userList.stream().map(UserUid::getUid).distinct().collect(Collectors.toList());
        assert userIdList.size() == total;

        userUidService.deleteByIdList(userIdList);
    }

    /**
     * sql注入攻击测试
     */
    @Test
    public void sqlInject() {
        UserUid userUid = new UserUid();
        userUid.setName("mybatis-helper");
        userUidService.insert(userUid);
        assert userUid.getUid() != null;

        String injectSql = "mybatis-helper or 1=1";

        List<UserUid> users = userUidService.selectList(new SelectWrapper<UserUid>()
                .eq(UserUid::getName, injectSql)
                .or().like(UserUid::getName, injectSql)
                .or().likeLeft(UserUid::getName, injectSql)
                .or().likeRight(UserUid::getName, injectSql)
        );

        assert users.isEmpty();

        boolean deleteSuccess = userUidService.deleteById(userUid.getUid());
        assert deleteSuccess;
    }
}
