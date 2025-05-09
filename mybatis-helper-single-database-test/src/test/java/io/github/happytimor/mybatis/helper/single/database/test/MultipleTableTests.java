package io.github.happytimor.mybatis.helper.single.database.test;

import io.github.happytimor.mybatis.helper.core.metadata.Page;
import io.github.happytimor.mybatis.helper.core.wrapper.DeleteWrapper;
import io.github.happytimor.mybatis.helper.core.wrapper.SelectWrapper;
import io.github.happytimor.mybatis.helper.core.wrapper.UpdateWrapper;
import io.github.happytimor.mybatis.helper.single.database.test.domain.User;
import io.github.happytimor.mybatis.helper.single.database.test.service.MultipleUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 测试环境: 多表,有主键且主键就是id
 *
 * @author chenpeng
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MultipleTableTests {
    private final static Logger logger = LoggerFactory.getLogger(MultipleTableTests.class);
    @Resource
    private MultipleUserService multipleUserService;


    private final String tableNum = "01";

    String name = "mybatis-helper-";

    /**
     * 单条插入、根据主键查找、根据主键删除联合测试
     */
    @Test
    public void insert() {
        User user = new User();
        user.setName(name);
        multipleUserService.insert(tableNum, user);
        assert user.getId() != null;

        User dbUser = multipleUserService.selectById(tableNum, user.getId());
        assert dbUser.getName().equals(user.getName());

        boolean deleteSuccess = multipleUserService.deleteById(tableNum, user.getId());
        assert deleteSuccess;

        dbUser = multipleUserService.selectById(tableNum, user.getId());
        assert dbUser == null;
    }

    /**
     * 根据id更新测试
     */
    @Test
    public void updateById() {
        User user = new User();
        user.setName(name);
        multipleUserService.insert(tableNum, user);
        assert user.getId() != null;

        String newName = name + System.currentTimeMillis();
        user.setName(newName);
        user.setAge(22);
        user.setMarried(true);
        boolean updateSuccess = multipleUserService.updateById(tableNum, user);
        assert updateSuccess;

        User dbUser = multipleUserService.selectById(tableNum, user.getId());
        assert newName.equals(dbUser.getName()) && dbUser.getAge() == 22 && dbUser.getMarried();
        boolean deleteSuccess = multipleUserService.deleteById(tableNum, dbUser.getId());
        assert deleteSuccess;
    }

    /**
     * 自定义条件查询测试
     */
    @Test
    public void selectList() {
        String name = "mybatis-helper-" + System.currentTimeMillis();
        User user = new User();
        user.setName(name);
        multipleUserService.insert(tableNum, user);
        assert user.getId() != null;

        //测试各种条件能否生效
        List<User> userList = multipleUserService.selectList(tableNum, new SelectWrapper<User>()
                .eq(User::getName, name + System.currentTimeMillis())
                .gt(User::getAge, 12)
                .lt(User::getAge, 13)
                .isNotNull(User::getMarried)
                .isNull(User::getAge)
                .between(User::getAge, 12, 15)
                .notBetween(User::getAge, 14, 19)
                .like(User::getName, "mybatis")
                .notLike(User::getName, "mybatis")
                .likeLeft(User::getName, "mybatis")
                .likeRight(User::getName, "mybatis")
        );
        assert userList.isEmpty();


        userList = multipleUserService.selectList(tableNum, new SelectWrapper<User>()
                .select(User::getName)
                .eq(User::getName, name)
        );

        assert userList.size() == 1 && userList.get(0).getName().equals(name);


        //selectOne测试
        User dbUser = multipleUserService.selectOne(tableNum, new SelectWrapper<User>()
                .select(User::getId, User::getName)
                .eq(User::getName, name)
        );
        assert dbUser.getName().equals(name);

        boolean deleteSuccess = multipleUserService.deleteById(tableNum, dbUser.getId());
        assert deleteSuccess;
    }

    /**
     * 自定义更新测试
     */
    public void update() {
        User user = new User();
        user.setName(name);
        multipleUserService.insert(tableNum, user);
        assert user.getId() != null;

        String newName = name + System.currentTimeMillis();


        //自定义条件更新测试(填充很多的查询条件,主要用于检测条件是否能正常组成sql)
        int updateCount = multipleUserService.update(tableNum, new UpdateWrapper<User>().set(User::getAge, 12).set(User::getMarried, true).set(User::getLastLoginTime, "2019-09-09")
                .eq(User::getName, newName + System.currentTimeMillis())
                .gt(User::getAge, 12)
                .lt(User::getAge, 13)
                .isNotNull(User::getMarried)
                .isNull(User::getAge)
                .between(User::getAge, 12, 15)
                .notBetween(User::getAge, 14, 19)
                .like(User::getName, "mybatis")
                .notLike(User::getName, "mybatis")
                .likeLeft(User::getName, "mybatis")
                .likeRight(User::getName, "mybatis")
        );
        assert updateCount == 0;

        updateCount = this.multipleUserService.update(tableNum, new UpdateWrapper<User>()
                .set(User::getAge, 12)
                .set(User::getMarried, true)
                .set(User::getLastLoginTime, "2019-09-09")
                .eq(User::getName, newName)
        );
        assert updateCount > 0;

        User dbUser = this.multipleUserService.selectById(tableNum, user.getId());
        boolean deleteSuccess = this.multipleUserService.deleteById(tableNum, dbUser.getId());
        assert deleteSuccess;
    }

    /**
     * 批量插入、根据主键批量删除、根据主键批量更新联合测试
     */
    @Test
    public void batchOperation() {
        List<User> list = new ArrayList<>();
        long now = System.currentTimeMillis();
        int total = new Random().nextInt(1000) + 10;

        for (int i = 0; i < total; i++) {
            list.add(new User(name + now + i));
        }
        this.multipleUserService.batchInsert(tableNum, list);
        List<User> userList = this.multipleUserService.selectList(tableNum, new SelectWrapper<User>().select(User::getId).likeLeft(User::getName, "mybatis-helper-" + now));
        assert userList.size() == total;

        //批量查找
        List<Integer> userIdList = userList.stream().map(User::getId).distinct().collect(Collectors.toList());
        userList = this.multipleUserService.selectByIdList(tableNum, userIdList);
        assert userList.size() == total;


        //根据id批量更新
        for (User user : userList) {
            user.setName(name);
        }
        boolean updateSuucess = this.multipleUserService.batchUpdateById(tableNum, userList);
        assert updateSuucess;
        userList = this.multipleUserService.selectByIdList(tableNum, userIdList);
        for (User user : userList) {
            assert name.equals(user.getName());
        }


        //根据id批量删除
        int deleteCount = this.multipleUserService.deleteByIdList(tableNum, userIdList);
        assert deleteCount == total;

        //校验批量删除是否成功
        userList = this.multipleUserService.selectByIdList(tableNum, userIdList);
        assert userList.isEmpty();

    }

    /**
     * 自定义条件删除测试
     */
    @Test
    public void delete() {
        String name = "mybatis-helper-" + System.currentTimeMillis();
        User user = new User();
        user.setName(name);
        user.setAge(11);
        user.setMarried(true);
        user.setLastLoginTime(LocalDateTime.now());
        this.multipleUserService.insert(tableNum, user);
        assert user.getId() != null;

        int deleteCount = this.multipleUserService.delete(tableNum, new DeleteWrapper<User>()
                .eq(User::getName, name)
                .likeLeft(User::getName, name)
                .eq(User::getAge, 11)
        );
        assert deleteCount == 1;

        user = this.multipleUserService.selectById(tableNum, user.getId());
        assert user == null;
    }

    /**
     * 查询总数测试
     */
    @Test
    public void count() {
        List<User> list = new ArrayList<>();
        long now = System.currentTimeMillis();
        int total = new Random().nextInt(1000) + 10;
        for (int i = 0; i < total; i++) {
            list.add(new User(name + now + i));
        }
        this.multipleUserService.batchInsert(tableNum, list);
        List<User> userList = this.multipleUserService.selectList(tableNum, new SelectWrapper<User>()
                .select(User::getId)
                .likeLeft(User::getName, name + now)
        );
        assert userList.size() == total;

        //总数查询测试
        long count = this.multipleUserService.selectCount(tableNum, new SelectWrapper<User>().likeLeft(User::getName, "mybatis-helper-" + now));
        assert count == total;


        List<Integer> userIdList = userList.stream().map(User::getId).distinct().collect(Collectors.toList());
        assert userIdList.size() == total;

        //根据id批量删除
        int deleteCount = this.multipleUserService.deleteByIdList(tableNum, userIdList);
        assert deleteCount == total;
    }

    /**
     * 分页测试
     */
    @Test
    public void selectPage() {
        List<User> list = new ArrayList<>();
        long now = System.currentTimeMillis();
        int total = new Random().nextInt(1000) + 10;
        for (int i = 0; i < total; i++) {
            list.add(new User(name + now + i));
        }
        this.multipleUserService.batchInsert(tableNum, list);

        Page<User> page = this.multipleUserService.selectPage(tableNum, 1, 10, new SelectWrapper<User>()
                .likeLeft(User::getName, name + now)
        );
        assert page.getRecords().size() == 10 && page.getTotal() == total;

        List<User> userList = this.multipleUserService.selectList(tableNum, new SelectWrapper<User>()
                .select(User::getId)
                .likeLeft(User::getName, name + now)
        );
        assert userList.size() == total;


        List<Integer> userIdList = userList.stream().map(User::getId).distinct().collect(Collectors.toList());
        assert userIdList.size() == total;

        this.multipleUserService.deleteByIdList(tableNum, userIdList);
    }

    /**
     * sql注入攻击测试
     */
    @Test
    public void sqlInject() {
        User user = new User();
        user.setName(name);
        this.multipleUserService.insert(tableNum, user);
        assert user.getId() != null;

        String injectSql = "mybatis-helper or 1=1";

        List<User> users = this.multipleUserService.selectList(tableNum, new SelectWrapper<User>()
                .eq(User::getName, injectSql)
                .or().like(User::getName, injectSql)
                .or().likeLeft(User::getName, injectSql)
                .or().likeRight(User::getName, injectSql)
        );

        assert users.isEmpty();

        boolean deleteSuccess = this.multipleUserService.deleteById(tableNum, user.getId());
        assert deleteSuccess;
    }
}
