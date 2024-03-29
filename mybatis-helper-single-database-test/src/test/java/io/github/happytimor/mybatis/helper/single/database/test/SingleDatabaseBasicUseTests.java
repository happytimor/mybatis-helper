package io.github.happytimor.mybatis.helper.single.database.test;

import io.github.happytimor.mybatis.helper.core.wrapper.DeleteWrapper;
import io.github.happytimor.mybatis.helper.core.wrapper.SelectWrapper;
import io.github.happytimor.mybatis.helper.core.wrapper.UpdateWrapper;
import io.github.happytimor.mybatis.helper.single.database.test.domain.TeacherInfo;
import io.github.happytimor.mybatis.helper.single.database.test.domain.User;
import io.github.happytimor.mybatis.helper.single.database.test.mapper.UserMapper;
import io.github.happytimor.mybatis.helper.single.database.test.service.GenerateService;
import io.github.happytimor.mybatis.helper.single.database.test.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 测试环境: 单表,有主键且主键就是id
 *
 * @author chenpeng
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SingleDatabaseBasicUseTests {
    private final static Logger logger = LoggerFactory.getLogger(SingleDatabaseBasicUseTests.class);
    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private GenerateService generateService;

    @Test
    public void insertOrg() {
        User user = new User();
        user.setName("zhangsan");
        userMapper.insertOrg(user);
        System.out.println(user.getId());
    }

    @Test
    public void testBatchInsert() {
        List<User> userList = Arrays.asList(new User("zhangsan"), new User("lisi"));
        userMapper.batchInsertDemo("", userList);
        System.out.println(userList.size());
    }

    @Test
    public void testNested() {
        List<TeacherInfo> teacherInfos = this.userMapper.selectTeacherInfo();
        for (TeacherInfo teacherInfo : teacherInfos) {
            System.out.println(teacherInfo.toString());
        }
        System.out.println("老师数量: " + teacherInfos.size());
//        System.out.println("老师 " + teacherInfos.get(0).getId() + " 有学生: " + teacherInfos.get(0).hasStudent());
//        System.out.println("老师 " + teacherInfos.get(0).getId() + " 的学生数量: "
//                + teacherInfos.get(0).getStudentList().size());
    }

    /**
     * 插入孔对象
     */
    @Test
    public void insertNullObject() {
        User user = new User();
        try {
            this.userService.insert(user);
            assert user.getId() > 0;
        } finally {
            if (user.getId() != null) {
                this.userService.deleteById(user.getId());
            }
        }
    }

    @Test
    public void test1() {
        userService.selectList(new SelectWrapper<User>()
                        .select(User::getId)
                        .like(User::getName, "zhangsan")
                        .notLike(User::getName, "zhangsan")
                        .likeLeft(User::getName, "zhangsan_left")
                        .likeRight(User::getName, "zhangsan_right")

                        .between(User::getAge, 1, 3)
                        .notBetween(User::getAge, 1, 4)
                        .gt(User::getAge, 1)
                        .ge(User::getAge, 2)
                        .eq(User::getAge, 3)
                        .ne(User::getAge, 4)
                        .le(User::getAge, 5)
                        .lt(User::getAge, 6)

                        .gt(User::getAge, User::getId)
                        .ge(User::getAge, User::getId)
                        .eq(User::getAge, User::getId)
                        .lt(User::getAge, User::getId)
                        .le(User::getAge, User::getId)
                        .ne(User::getAge, User::getId)

//                .gtNested(User::getAge, t -> t.applySelectWrapper(User.class).select(User::getAge).gt(User::getFlag, 1))
        );
    }

    /**
     * localDateTime测试
     */
    @Test
    public void localDateTime() {
        this.generateService.generateBatch((flag, userList) -> {
            LocalDateTime end = LocalDateTime.now().minusDays(new Random().nextInt(15));
            LocalDateTime start = end.minusDays(new Random().nextInt(15));
            long hitCount = userList.stream().filter(user ->
                    user.getLastLoginTime().isAfter(start) && user.getLastLoginTime().isBefore(end)
            ).count();

            long dbCount = this.userService.selectCount(new SelectWrapper<User>()
                    .eq(User::getFlag, flag)
                    .gt(User::getLastLoginTime, start)
                    .lt(User::getLastLoginTime, end));
            assert hitCount == dbCount;
        });
    }

    /**
     * 自定义条件查询测试
     */
    @Test
    public void selectList() {
        String name = "select-list-" + System.currentTimeMillis();
        User user = new User();
        user.setName(name);
        userService.insert(user);
        assert user.getId() != null;

        //测试各种条件是否会报错
        List<User> userList = userService.selectList(new SelectWrapper<User>()
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


        userList = userService.selectList(new SelectWrapper<User>()
                .select(User::getName)
                .eq(User::getName, name)
        );

        assert userList.size() == 1 && userList.get(0).getName().equals(name);


        //selectOne测试
        User dbUser = userService.selectOne(new SelectWrapper<User>()
                .select(User::getId, User::getName)
                .eq(User::getName, name)
        );
        assert dbUser.getName().equals(name);

        boolean deleteSuccess = userService.deleteById(dbUser.getId());
        assert deleteSuccess;
    }

    /**
     * 自定义更新测试
     */
    public void update() {
        String name = "update-" + System.currentTimeMillis();
        User user = new User();
        user.setName(name);
        userService.insert(user);
        assert user.getId() != null;

        String newName = name + "-new";


        //自定义条件更新测试(填充很多的查询条件,主要用于检测条件是否能正常组成sql)
        int updateCount = userService.update(new UpdateWrapper<User>().set(User::getAge, 12).set(User::getMarried, true).set(User::getLastLoginTime, "2019-09-09")
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

        Date date = new Date();
        updateCount = userService.update(new UpdateWrapper<User>().set(User::getAge, 12).set(User::getMarried, true).set(User::getLastLoginTime, date)
                .eq(User::getName, newName)
        );

        User dbUser = userService.selectById(user.getId());
        assert updateCount == 1 && dbUser.getAge() == 12 && date.equals(dbUser.getLastLoginTime());
        boolean deleteSuccess = userService.deleteById(dbUser.getId());
        assert deleteSuccess;
    }

    /**
     * 自定义条件删除测试
     */
    @Test
    public void delete() {
        String name = "delete-";
        User user = new User();
        user.setName(name + System.currentTimeMillis());
        user.setAge(11);
        user.setMarried(true);
        user.setLastLoginTime(LocalDateTime.now());
        userService.insert(user);
        assert user.getId() != null;

        int deleteCount = userService.delete(new DeleteWrapper<User>()
                .eq(User::getName, user.getName())
                .likeLeft(User::getName, name)
                .eq(User::getAge, 11)
        );
        assert deleteCount == 1;

        user = userService.selectById(user.getId());
        assert user == null;
    }


    /**
     * sql注入攻击测试
     */
    @Test
    public void sqlInject() {
        String name = "sql-inject";
        User user = new User();
        user.setName(name);
        userService.insert(user);
        assert user.getId() != null;

        String injectSql = name + " or 1=1";

        List<User> users = userService.selectList(new SelectWrapper<User>()
                .eq(User::getName, injectSql)
                .or().like(User::getName, injectSql)
                .or().likeLeft(User::getName, injectSql)
                .or().likeRight(User::getName, injectSql)
        );

        assert users.isEmpty();

        boolean deleteSuccess = userService.deleteById(user.getId());
        assert deleteSuccess;
    }

}
