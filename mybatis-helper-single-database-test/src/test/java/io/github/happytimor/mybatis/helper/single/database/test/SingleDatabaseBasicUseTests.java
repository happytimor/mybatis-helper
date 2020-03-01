package io.github.happytimor.mybatis.helper.single.database.test;

import io.github.happytimor.mybatis.helper.core.function.SqlFunction;
import io.github.happytimor.mybatis.helper.core.metadata.Page;
import io.github.happytimor.mybatis.helper.core.wrapper.DeleteWrapper;
import io.github.happytimor.mybatis.helper.core.wrapper.SelectWrapper;
import io.github.happytimor.mybatis.helper.core.wrapper.UpdateWrapper;
import io.github.happytimor.mybatis.helper.single.database.test.domain.AgeInfo;
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
import java.util.*;
import java.util.stream.Collectors;

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

    /**
     * 单条插入、根据主键查找、根据主键删除联合测试
     */
    @Test
    public void insert() {
        //插入
        String name = "insert-test-" + System.currentTimeMillis();
        User user = new User();
        user.setName(name);
        user.setUserGrade(new Random().nextInt(100));
        userService.insert(user);

        //是否返回主键
        assert user.getId() != null;

        //插入结果判断
        User dbUser = userService.selectById(user.getId());
        assert dbUser.getName().equals(user.getName());

        //删除
        boolean deleteSuccess = userService.deleteById(user.getId());
        assert deleteSuccess;

        //判断是否删除成功
        dbUser = userService.selectById(user.getId());
        assert dbUser == null;
    }

    /**
     * 根据id更新测试
     */
    @Test
    public void updateById() {
        //插入新数据
        String name = "update-by-id-test-" + System.currentTimeMillis();
        User user = new User();
        user.setName(name);
        userService.insert(user);
        assert user.getId() != null;

        //数据更新
        String newName = name + "-new";
        user.setName(newName);
        user.setAge(22);
        user.setMarried(true);
        boolean updateSuccess = userService.updateById(user);
        assert updateSuccess;

        //判断更新结果
        User dbUser = userService.selectById(user.getId());
        assert newName.equals(dbUser.getName()) && dbUser.getAge() == 22 && dbUser.getMarried();

        //数据删除
        boolean deleteSuccess = userService.deleteById(dbUser.getId());
        assert deleteSuccess;
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

            long dbCount = this.userService.selectCount(new SelectWrapper<User>().gt(User::getLastLoginTime, start).lt(User::getLastLoginTime, end));
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
     * 唯一索引冲突更新测试
     */
    @Test
    public void insertOrUpdateWithUniqueIndex() {
        String name = "insert-or-update-with-uni-" + System.currentTimeMillis();
        User user = new User();
        user.setName(name);
        userService.insert(user);
        assert user.getId() != null;


        //唯一索引冲突更新测试(id冲突)
        String newName = name + "-new";
        user.setName(newName);
        boolean updateSuccess = userService.insertOrUpdateWithUniqueIndex(user);
        assert updateSuccess;
        User dbUser = userService.selectById(user.getId());
        assert newName.equals(dbUser.getName());

        boolean deleteSuccess = userService.deleteById(dbUser.getId());
        assert deleteSuccess;
    }

    /**
     * 批量插入、根据主键批量删除、根据主键批量更新联合测试
     */
    @Test
    public void batchOperation() {
        List<User> list = new ArrayList<>();
        String name = "batch-operation-" + System.currentTimeMillis();
        int total = new Random().nextInt(1000) + 10;

        for (int i = 0; i < total; i++) {
            list.add(new User(name + i));
        }
        userService.batchInsert(list);
        List<User> userList = userService.selectList(new SelectWrapper<User>().select(User::getId).likeLeft(User::getName, name));
        assert userList.size() == total;

        //批量查找
        List<Integer> userIdList = userList.stream().map(User::getId).distinct().collect(Collectors.toList());
        userList = userService.selectByIdList(userIdList);
        assert userList.size() == total;


        String newName = name + "-new";
        //根据id批量更新
        for (User user : userList) {
            user.setName(newName);
        }
        boolean updateSuucess = userService.batchUpdateById(userList);
        assert updateSuucess;
        userList = userService.selectByIdList(userIdList);
        for (User user : userList) {
            assert newName.equals(user.getName());
        }


        //根据id批量删除
        int deleteCount = userService.deleteByIdList(userIdList);
        assert deleteCount == total;

        //校验批量删除是否成功
        userList = userService.selectByIdList(userIdList);
        assert userList.size() == 0;

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
     * 查询总数测试
     */
    @Test
    public void count() {
        List<User> list = new ArrayList<>();
        String name = "count-" + System.currentTimeMillis();
        int total = new Random().nextInt(1000) + 10;
        for (int i = 0; i < total; i++) {
            list.add(new User(name + i));
        }
        userService.batchInsert(list);
        List<User> userList = userService.selectList(new SelectWrapper<User>()
                .select(User::getId)
                .likeLeft(User::getName, name)
        );
        assert userList.size() == total;

        //总数查询测试
        long count = userService.selectCount(new SelectWrapper<User>().likeLeft(User::getName, name));
        assert count == total;


        List<Integer> userIdList = userList.stream().map(User::getId).distinct().collect(Collectors.toList());
        assert userIdList.size() == total;

        //根据id批量删除
        int deleteCount = userService.deleteByIdList(userIdList);
        assert deleteCount == total;
    }

    /**
     * 分页测试
     */
    @Test
    public void selectPage() {
        List<User> list = new ArrayList<>();
        String name = "select-page-" + System.currentTimeMillis();
        //随机插入若干条数据
        int total = new Random().nextInt(1000) + 10;
        for (int i = 0; i < total; i++) {
            list.add(new User(name + i));
        }
        userService.batchInsert(list);

        //分页查询
        Page<User> page = userService.selectPage(1, 10, new SelectWrapper<User>()
                .likeLeft(User::getName, name)
        );
        assert page.getRecords().size() == 10 && page.getTotal() == total;

        List<User> userList = userService.selectList(new SelectWrapper<User>()
                .select(User::getId)
                .likeLeft(User::getName, name)
        );
        assert userList.size() == total;


        List<Integer> userIdList = userList.stream().map(User::getId).distinct().collect(Collectors.toList());
        assert userIdList.size() == total;
        //数据清理
        userService.deleteByIdList(userIdList);
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

    /**
     * 单值查询
     */
    @Test
    public void selectSingleValue() {
        final int count = 1000;
        this.generateService.generateBatch(count, (flag, userList) -> {
            int maxAge = -1;
            int sumAge = 0;
            for (User user : userList) {
                if (maxAge < user.getAge()) {
                    maxAge = user.getAge();
                }
                sumAge += user.getAge();
            }

            Number dbCount = this.userService.selectSingleValue(new SelectWrapper<User>().select(SqlFunction.count(User::getId)).eq(User::getFlag, flag));
            assert dbCount.intValue() == count;

            Number dbMaxAge = this.userService.selectSingleValue(new SelectWrapper<User>().select(SqlFunction.max(User::getAge)).eq(User::getFlag, flag));
            assert dbMaxAge.intValue() == maxAge;

            Number dbSumAge = this.userService.selectSingleValue(new SelectWrapper<User>().select(SqlFunction.sum(User::getAge)).eq(User::getFlag, flag));
            assert dbSumAge.intValue() == sumAge;

        });
    }

    /**
     * 对象转换测试
     */
    @Test
    public void selectObjectList() {
        generateService.generateBatch(1000, (flag, userList) -> {
            //对生成的数据按照年龄进行分组, 为后续比对做准备
            Map<Integer, List<User>> ageMap = userList.stream().collect(Collectors.groupingBy(User::getAge));
            List<AgeInfo> ageInfoList = userService.selectObjectList(AgeInfo.class, new SelectWrapper<User>().select(User::getAge, SqlFunction.count(User::getAge, "count"))
                    .eq(User::getFlag, flag)
                    .groupBy(User::getAge)
            );

            //数据校验
            for (AgeInfo ageInfo : ageInfoList) {
                assert ageMap.get(ageInfo.getAge()).size() == ageInfo.getCount();
            }
        });


    }

}
