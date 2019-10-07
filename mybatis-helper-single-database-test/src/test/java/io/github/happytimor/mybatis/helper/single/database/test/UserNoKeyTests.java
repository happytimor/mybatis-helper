package io.github.happytimor.mybatis.helper.single.database.test;

import io.github.happytimor.mybatis.helper.core.metadata.Page;
import io.github.happytimor.mybatis.helper.core.wrapper.DeleteWrapper;
import io.github.happytimor.mybatis.helper.core.wrapper.SelectWrapper;
import io.github.happytimor.mybatis.helper.core.wrapper.UpdateWrapper;
import io.github.happytimor.mybatis.helper.single.database.test.domain.UserNoKey;
import io.github.happytimor.mybatis.helper.single.database.test.service.UserNoKeyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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
public class UserNoKeyTests {
    @Resource
    private UserNoKeyService userNoKeyService;

    /**
     * 单条插入、根据主键查找、根据主键删除联合测试
     */
    @Test
    public void insert() {
        String name = "mybatis-helper-" + System.currentTimeMillis();
        UserNoKey userNoKey = new UserNoKey();
        userNoKey.setName(name);
        userNoKeyService.insert(userNoKey);

        UserNoKey dbUser = userNoKeyService.selectOne(new SelectWrapper<UserNoKey>().eq(UserNoKey::getName, userNoKey.getName()));
        assert dbUser.getName().equals(userNoKey.getName());

        int deleteCount = userNoKeyService.delete(new DeleteWrapper<UserNoKey>().eq(UserNoKey::getName, userNoKey.getName()));
        assert deleteCount == 1;

        dbUser = userNoKeyService.selectOne(new SelectWrapper<UserNoKey>().eq(UserNoKey::getName, userNoKey.getName()));
        assert dbUser == null;
    }


    /**
     * 自定义条件查询测试
     */
    @Test
    public void selectList() {
        String name = "mybatis-helper-" + System.currentTimeMillis();
        UserNoKey userNoKey = new UserNoKey();
        userNoKey.setName(name);
        userNoKeyService.insert(userNoKey);

        //测试各种条件能否生效
        List<UserNoKey> userList = userNoKeyService.selectList(new SelectWrapper<UserNoKey>()
                .eq(UserNoKey::getName, name + System.currentTimeMillis())
                .gt(UserNoKey::getAge, 12)
                .lt(UserNoKey::getAge, 13)
                .isNotNull(UserNoKey::getMarried)
                .isNull(UserNoKey::getAge)
                .between(UserNoKey::getAge, 12, 15)
                .notBetween(UserNoKey::getAge, 14, 19)
                .like(UserNoKey::getName, "mybatis")
                .notLike(UserNoKey::getName, "mybatis")
                .likeLeft(UserNoKey::getName, "mybatis")
                .likeRight(UserNoKey::getName, "mybatis")
        );
        assert userList.isEmpty();


        userList = userNoKeyService.selectList(new SelectWrapper<UserNoKey>()
                .select(UserNoKey::getName)
                .eq(UserNoKey::getName, name)
        );

        assert userList.size() == 1 && userList.get(0).getName().equals(name);


        //selectOne测试
        UserNoKey dbUser = userNoKeyService.selectOne(new SelectWrapper<UserNoKey>()
                .select(UserNoKey::getName)
                .eq(UserNoKey::getName, name)
        );
        assert dbUser.getName().equals(name);

        int deleteCount = userNoKeyService.delete(new DeleteWrapper<UserNoKey>().eq(UserNoKey::getName, userNoKey.getName()));
        assert deleteCount == 1;
    }

    /**
     * 自定义更新测试
     */
    public void update() {
        String name = "mybatis-helper-" + System.currentTimeMillis();
        UserNoKey userNoKey = new UserNoKey();
        userNoKey.setName(name);
        userNoKeyService.insert(userNoKey);

        String newName = "mybatis-helper-" + System.currentTimeMillis();


        //自定义条件更新测试(填充很多的查询条件,主要用于检测条件是否能正常组成sql)
        int updateCount = userNoKeyService.update(new UpdateWrapper<UserNoKey>().set(UserNoKey::getAge, 12).set(UserNoKey::getMarried, true).set(UserNoKey::getBirthday, "2019-09-09")
                .eq(UserNoKey::getName, newName + System.currentTimeMillis())
                .gt(UserNoKey::getAge, 12)
                .lt(UserNoKey::getAge, 13)
                .isNotNull(UserNoKey::getMarried)
                .isNull(UserNoKey::getAge)
                .between(UserNoKey::getAge, 12, 15)
                .notBetween(UserNoKey::getAge, 14, 19)
                .like(UserNoKey::getName, "mybatis")
                .notLike(UserNoKey::getName, "mybatis")
                .likeLeft(UserNoKey::getName, "mybatis")
                .likeRight(UserNoKey::getName, "mybatis")
        );
        assert updateCount == 0;

        updateCount = userNoKeyService.update(new UpdateWrapper<UserNoKey>().set(UserNoKey::getAge, 12).set(UserNoKey::getMarried, true).set(UserNoKey::getBirthday, "2019-09-09")
                .eq(UserNoKey::getName, newName)
        );

        UserNoKey dbUser = userNoKeyService.selectOne(new SelectWrapper<UserNoKey>()
                .select(UserNoKey::getName)
                .eq(UserNoKey::getName, newName));
        assert updateCount == 1 && dbUser.getAge() == 12 && "2019-09-09".equals(dbUser.getBirthday());
        int deleteCount = userNoKeyService.delete(new DeleteWrapper<UserNoKey>().eq(UserNoKey::getName, newName));
        assert deleteCount == 1;
    }


    /**
     * 批量插入、根据主键批量删除、根据主键批量更新联合测试
     */
    @Test
    public void batchOperation() {
        List<UserNoKey> list = new ArrayList<>();
        String name = "mybatis-helper-" + System.currentTimeMillis();
        int total = new Random().nextInt(1000) + 10;

        for (int i = 0; i < total; i++) {
            list.add(new UserNoKey(name + i));
        }
        userNoKeyService.batchInsert(list);
        List<UserNoKey> userList = userNoKeyService.selectList(new SelectWrapper<UserNoKey>().likeRight(UserNoKey::getName, name));
        assert userList.size() == total;

        int deleteCount = userNoKeyService.delete(new DeleteWrapper<UserNoKey>().likeRight(UserNoKey::getName, name));
        assert deleteCount == total;
    }

    /**
     * 自定义条件删除测试
     */
    @Test
    public void delete() {
        String name = "mybatis-helper-" + System.currentTimeMillis();
        UserNoKey userNoKey = new UserNoKey();
        userNoKey.setName(name);
        userNoKey.setAge(11);
        userNoKey.setMarried(true);
        userNoKey.setBirthday(new Date());
        userNoKeyService.insert(userNoKey);

        int deleteCount = userNoKeyService.delete(new DeleteWrapper<UserNoKey>()
                .eq(UserNoKey::getName, name)
                .likeRight(UserNoKey::getName, "mybatis-helper")
                .eq(UserNoKey::getAge, 11)
        );
        assert deleteCount == 1;

        UserNoKey dbUser = userNoKeyService.selectOne(new SelectWrapper<UserNoKey>()
                .select(UserNoKey::getName)
                .eq(UserNoKey::getName, name)
        );
        assert dbUser == null;
    }

    /**
     * 查询总数测试
     */
    @Test
    public void count() {
        List<UserNoKey> list = new ArrayList<>();
        String name = "mybatis-helper-" + System.currentTimeMillis();
        int total = new Random().nextInt(1000) + 10;
        for (int i = 0; i < total; i++) {
            list.add(new UserNoKey(name + i));
        }
        userNoKeyService.batchInsert(list);
        List<UserNoKey> userList = userNoKeyService.selectList(new SelectWrapper<UserNoKey>()
                .likeRight(UserNoKey::getName, name)
        );
        assert userList.size() == total;

        //总数查询测试
        long count = userNoKeyService.selectCount(new SelectWrapper<UserNoKey>().likeRight(UserNoKey::getName, name));
        assert count == total;

        //根据id批量删除
        int deleteCount = userNoKeyService.delete(new DeleteWrapper<UserNoKey>().likeRight(UserNoKey::getName, name));
        assert deleteCount == total;
    }

    /**
     * 分页测试
     */
    @Test
    public void selectPage() {
        List<UserNoKey> list = new ArrayList<>();
        String name = "mybatis-helper-" + System.currentTimeMillis();
        int total = new Random().nextInt(1000) + 10;
        for (int i = 0; i < total; i++) {
            list.add(new UserNoKey(name + i));
        }
        userNoKeyService.batchInsert(list);

        Page<UserNoKey> page = userNoKeyService.selectPage(new Page<>(1, 10), new SelectWrapper<UserNoKey>()
                .likeRight(UserNoKey::getName, name)
        );
        assert page.getRecords().size() == 10 && page.getTotal() == total;

        List<UserNoKey> userList = userNoKeyService.selectList(new SelectWrapper<UserNoKey>()
                .likeRight(UserNoKey::getName, name)
        );
        assert userList.size() == total;


        int deleteCount = userNoKeyService.delete(new DeleteWrapper<UserNoKey>().likeRight(UserNoKey::getName, name));
        assert deleteCount == total;
    }

    /**
     * sql注入攻击测试
     */
    @Test
    public void sqlInject() {
        String name = "mybatis-helper-" + System.currentTimeMillis();
        UserNoKey userNoKey = new UserNoKey();
        userNoKey.setName(name);
        userNoKeyService.insert(userNoKey);

        String injectSql = name + " or 1=1";

        List<UserNoKey> users = userNoKeyService.selectList(new SelectWrapper<UserNoKey>()
                .eq(UserNoKey::getName, injectSql)
                .or().like(UserNoKey::getName, injectSql)
                .or().likeLeft(UserNoKey::getName, injectSql)
                .or().likeRight(UserNoKey::getName, injectSql)
        );

        assert users.isEmpty();

        int deleteCount = userNoKeyService.delete(new DeleteWrapper<UserNoKey>().eq(UserNoKey::getName, name));
        assert deleteCount == 1;

        UserNoKey dbUser = userNoKeyService.selectOne(new SelectWrapper<UserNoKey>()
                .select(UserNoKey::getName)
                .eq(UserNoKey::getName, userNoKey.getName())
        );
        assert dbUser == null;
    }
}
