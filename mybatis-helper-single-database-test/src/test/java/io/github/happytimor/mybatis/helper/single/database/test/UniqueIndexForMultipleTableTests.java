package io.github.happytimor.mybatis.helper.single.database.test;

import io.github.happytimor.mybatis.helper.core.metadata.Page;
import io.github.happytimor.mybatis.helper.core.wrapper.DeleteWrapper;
import io.github.happytimor.mybatis.helper.core.wrapper.SelectWrapper;
import io.github.happytimor.mybatis.helper.core.wrapper.UpdateWrapper;
import io.github.happytimor.mybatis.helper.single.database.test.domain.User;
import io.github.happytimor.mybatis.helper.single.database.test.domain.UserUniqueIndex;
import io.github.happytimor.mybatis.helper.single.database.test.service.UserMultipleTableUniqueIndexService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
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
public class UniqueIndexForMultipleTableTests {
    private final static Logger logger = LoggerFactory.getLogger(UniqueIndexForMultipleTableTests.class);
    @Resource
    private UserMultipleTableUniqueIndexService userMultipleTableUniqueIndexService;


    private final String tableNum = "01";

    String name = "mybatis-helper-";

    /**
     * 单条插入、根据主键查找、根据主键删除联合测试
     */
    @Test
    public void insert() {
        UserUniqueIndex user = new UserUniqueIndex();
        user.setName(name);
        this.userMultipleTableUniqueIndexService.insert(tableNum, user);
        assert user.getId() != null;

        UserUniqueIndex dbUser = this.userMultipleTableUniqueIndexService.selectById(tableNum, user.getId());
        assert dbUser.getName().equals(user.getName());

        boolean deleteSuccess = this.userMultipleTableUniqueIndexService.deleteById(tableNum, user.getId());
        assert deleteSuccess;

        dbUser = this.userMultipleTableUniqueIndexService.selectById(tableNum, user.getId());
        assert dbUser == null;
    }

    /**
     * 根据id更新测试
     */
    @Test
    public void updateById() {
        String cardNo = "123456";
        UserUniqueIndex user = new UserUniqueIndex();
        user.setName(name);
        this.userMultipleTableUniqueIndexService.insert(tableNum, user);
        assert user.getId() != null;

        String newName = name + System.currentTimeMillis();
        user.setName(newName);
        user.setCardNo(cardNo);
        boolean updateSuccess = this.userMultipleTableUniqueIndexService.updateById(tableNum, user);
        assert updateSuccess;

        UserUniqueIndex dbUser = this.userMultipleTableUniqueIndexService.selectById(tableNum, user.getId());
        assert newName.equals(dbUser.getName()) && cardNo.equals(dbUser.getCardNo());
        boolean deleteSuccess = this.userMultipleTableUniqueIndexService.deleteById(tableNum, dbUser.getId());
        assert deleteSuccess;
    }

    /**
     * 自定义条件查询测试
     */
    @Test
    public void selectList() {
        String name = "mybatis-helper-" + System.currentTimeMillis();
        UserUniqueIndex user = new UserUniqueIndex();
        user.setName(name);
        this.userMultipleTableUniqueIndexService.insert(tableNum, user);
        assert user.getId() != null;

        //测试各种条件能否生效
        List<UserUniqueIndex> userList = this.userMultipleTableUniqueIndexService.selectList(tableNum, new SelectWrapper<UserUniqueIndex>()
                .eq(UserUniqueIndex::getName, name + System.currentTimeMillis())
                .gt(UserUniqueIndex::getCardNo, 12)
                .lt(UserUniqueIndex::getCardNo, 13)
                .isNotNull(UserUniqueIndex::getCardNo)
                .isNull(UserUniqueIndex::getCardNo)
                .between(UserUniqueIndex::getCardNo, 12, 15)
                .notBetween(UserUniqueIndex::getCardNo, 14, 19)
                .like(UserUniqueIndex::getName, "mybatis")
                .notLike(UserUniqueIndex::getName, "mybatis")
                .likeLeft(UserUniqueIndex::getName, "mybatis")
                .likeRight(UserUniqueIndex::getName, "mybatis")
        );
        assert userList.isEmpty();


        userList = this.userMultipleTableUniqueIndexService.selectList(tableNum, new SelectWrapper<UserUniqueIndex>()
                .select(UserUniqueIndex::getName)
                .eq(UserUniqueIndex::getName, name)
        );

        assert userList.size() == 1 && userList.get(0).getName().equals(name);


        //selectOne测试
        UserUniqueIndex dbUser = this.userMultipleTableUniqueIndexService.selectOne(tableNum, new SelectWrapper<UserUniqueIndex>()
                .select(UserUniqueIndex::getId, UserUniqueIndex::getName)
                .eq(UserUniqueIndex::getName, name)
        );
        assert dbUser.getName().equals(name);

        boolean deleteSuccess = this.userMultipleTableUniqueIndexService.deleteById(tableNum, dbUser.getId());
        assert deleteSuccess;
    }

    /**
     * 自定义更新测试
     */
    public void update() {
        UserUniqueIndex user = new UserUniqueIndex();
        user.setName(name);
        this.userMultipleTableUniqueIndexService.insert(tableNum, user);
        assert user.getId() != null;

        String newName = name + System.currentTimeMillis();


        //自定义条件更新测试(填充很多的查询条件,主要用于检测条件是否能正常组成sql)
        int updateCount = this.userMultipleTableUniqueIndexService.update(tableNum, new UpdateWrapper<UserUniqueIndex>()
                .set(UserUniqueIndex::getCardNo, "123456")
                .eq(UserUniqueIndex::getName, newName + System.currentTimeMillis())
                .gt(UserUniqueIndex::getCardNo, "123456")
                .lt(UserUniqueIndex::getCardNo, "123456")
                .isNotNull(UserUniqueIndex::getCardNo)
                .isNull(UserUniqueIndex::getCardNo)
                .between(UserUniqueIndex::getCardNo, 12, 15)
                .notBetween(UserUniqueIndex::getCardNo, 14, 19)
                .like(UserUniqueIndex::getName, "mybatis")
                .notLike(UserUniqueIndex::getName, "mybatis")
                .likeLeft(UserUniqueIndex::getName, "mybatis")
                .likeRight(UserUniqueIndex::getName, "mybatis")
        );
        assert updateCount == 0;

        updateCount = this.userMultipleTableUniqueIndexService.update(tableNum, new UpdateWrapper<UserUniqueIndex>()
                .set(UserUniqueIndex::getCardNo, "11111")
                .eq(User::getName, newName)
        );

        UserUniqueIndex dbUser = this.userMultipleTableUniqueIndexService.selectById(tableNum, user.getId());
        boolean deleteSuccess = this.userMultipleTableUniqueIndexService.deleteById(tableNum, dbUser.getId());
        assert deleteSuccess;
    }

    /**
     * 批量插入、根据主键批量删除、根据主键批量更新联合测试
     */
    @Test
    public void batchOperation() {
        List<UserUniqueIndex> list = new ArrayList<>();
        long now = System.currentTimeMillis();
        int total = new Random().nextInt(1000) + 10;

        for (int i = 0; i < total; i++) {
            list.add(new UserUniqueIndex(String.valueOf(i), name + now + i));
        }
        this.userMultipleTableUniqueIndexService.batchInsert(tableNum, list);
        List<UserUniqueIndex> userList = this.userMultipleTableUniqueIndexService.selectList(tableNum, new SelectWrapper<UserUniqueIndex>().select(UserUniqueIndex::getId).likeLeft(UserUniqueIndex::getName, "mybatis-helper-" + now));
        assert userList.size() == total;

        //批量查找
        List<Integer> userIdList = userList.stream().map(UserUniqueIndex::getId).distinct().collect(Collectors.toList());
        userList = this.userMultipleTableUniqueIndexService.selectByIdList(tableNum, userIdList);
        assert userList.size() == total;


        //根据id批量更新
        for (UserUniqueIndex user : userList) {
            user.setName(name);
        }
        boolean updateSuucess = this.userMultipleTableUniqueIndexService.batchUpdateById(tableNum, userList);
        assert updateSuucess;
        userList = this.userMultipleTableUniqueIndexService.selectByIdList(tableNum, userIdList);
        for (UserUniqueIndex user : userList) {
            assert name.equals(user.getName());
        }


        //根据id批量删除
        int deleteCount = this.userMultipleTableUniqueIndexService.deleteByIdList(tableNum, userIdList);
        assert deleteCount == total;

        //校验批量删除是否成功
        userList = this.userMultipleTableUniqueIndexService.selectByIdList(tableNum, userIdList);
        assert userList.isEmpty();

    }

    /**
     * 自定义条件删除测试
     */
    @Test
    public void delete() {
        String name = "mybatis-helper-" + System.currentTimeMillis();
        UserUniqueIndex user = new UserUniqueIndex();
        user.setName(name);
        user.setCardNo("123456");
        this.userMultipleTableUniqueIndexService.insert(tableNum, user);
        assert user.getId() != null;

        int deleteCount = this.userMultipleTableUniqueIndexService.delete(tableNum, new DeleteWrapper<UserUniqueIndex>()
                .eq(UserUniqueIndex::getName, name)
                .likeLeft(UserUniqueIndex::getName, name)
                .eq(UserUniqueIndex::getCardNo, "123456")
        );
        assert deleteCount == 1;

        user = this.userMultipleTableUniqueIndexService.selectById(tableNum, user.getId());
        assert user == null;
    }

    /**
     * 查询总数测试
     */
    @Test
    public void count() {
        List<UserUniqueIndex> list = new ArrayList<>();
        long now = System.currentTimeMillis();
        int total = new Random().nextInt(1000) + 10;
        for (int i = 0; i < total; i++) {
            list.add(new UserUniqueIndex(String.valueOf(i), name + now + i));
        }
        this.userMultipleTableUniqueIndexService.batchInsert(tableNum, list);
        List<UserUniqueIndex> userList = this.userMultipleTableUniqueIndexService.selectList(tableNum, new SelectWrapper<UserUniqueIndex>()
                .select(UserUniqueIndex::getId)
                .likeLeft(UserUniqueIndex::getName, name + now)
        );
        assert userList.size() == total;

        //总数查询测试
        long count = this.userMultipleTableUniqueIndexService.selectCount(tableNum, new SelectWrapper<UserUniqueIndex>().likeLeft(UserUniqueIndex::getName, "mybatis-helper-" + now));
        assert count == total;


        List<Integer> userIdList = userList.stream().map(UserUniqueIndex::getId).distinct().collect(Collectors.toList());
        assert userIdList.size() == total;

        //根据id批量删除
        int deleteCount = this.userMultipleTableUniqueIndexService.deleteByIdList(tableNum, userIdList);
        assert deleteCount == total;
    }

    /**
     * 分页测试
     */
    @Test
    public void selectPage() {
        List<UserUniqueIndex> list = new ArrayList<>();
        long now = System.currentTimeMillis();
        int total = new Random().nextInt(1000) + 10;
        for (int i = 0; i < total; i++) {
            list.add(new UserUniqueIndex(String.valueOf(i), name + now + i));
        }
        this.userMultipleTableUniqueIndexService.batchInsert(tableNum, list);

        Page<UserUniqueIndex> page = this.userMultipleTableUniqueIndexService.selectPage(tableNum, 1, 10, new SelectWrapper<UserUniqueIndex>()
                .likeLeft(User::getName, name + now)
        );
        assert page.getRecords().size() == 10 && page.getTotal() == total;

        List<UserUniqueIndex> userList = this.userMultipleTableUniqueIndexService.selectList(tableNum, new SelectWrapper<UserUniqueIndex>()
                .select(UserUniqueIndex::getId)
                .likeLeft(UserUniqueIndex::getName, name + now)
        );
        assert userList.size() == total;


        List<Integer> userIdList = userList.stream().map(UserUniqueIndex::getId).distinct().collect(Collectors.toList());
        assert userIdList.size() == total;

        this.userMultipleTableUniqueIndexService.deleteByIdList(tableNum, userIdList);
    }

    /**
     * sql注入攻击测试
     */
    @Test
    public void sqlInject() {
        UserUniqueIndex user = new UserUniqueIndex();
        user.setName(name);
        this.userMultipleTableUniqueIndexService.insert(tableNum, user);
        assert user.getId() != null;

        String injectSql = "mybatis-helper or 1=1";

        List<UserUniqueIndex> users = this.userMultipleTableUniqueIndexService.selectList(tableNum, new SelectWrapper<UserUniqueIndex>()
                .eq(UserUniqueIndex::getName, injectSql)
                .or().like(UserUniqueIndex::getName, injectSql)
                .or().likeLeft(UserUniqueIndex::getName, injectSql)
                .or().likeRight(UserUniqueIndex::getName, injectSql)
        );

        assert users.isEmpty();

        boolean deleteSuccess = this.userMultipleTableUniqueIndexService.deleteById(tableNum, user.getId());
        assert deleteSuccess;
    }

    /**
     * insert ignore into 语法测试
     */
    @Test
    public void insertIgnoreInto() {
        this.userMultipleTableUniqueIndexService.delete(tableNum, new DeleteWrapper<UserUniqueIndex>()
                .eq(UserUniqueIndex::getCardNo, "1001")
        );
        boolean flag1 = this.userMultipleTableUniqueIndexService.insertIgnoreInto(tableNum, new UserUniqueIndex("1001", "zhangsan"));
        boolean flag2 = this.userMultipleTableUniqueIndexService.insertIgnoreInto(tableNum, new UserUniqueIndex("1001", "lisi"));
        boolean flag3 = this.userMultipleTableUniqueIndexService.insertIgnoreInto(tableNum, new UserUniqueIndex("1001", "wangwu"));
        assert flag1 && !flag2 && !flag3;

        List<UserUniqueIndex> list = this.userMultipleTableUniqueIndexService.selectList(tableNum, new SelectWrapper<UserUniqueIndex>()
                .eq(UserUniqueIndex::getCardNo, "1001")
        );
        assert list.size() == 1 && list.get(0).getName().equals("zhangsan");
        this.userMultipleTableUniqueIndexService.delete(tableNum, new DeleteWrapper<UserUniqueIndex>()
                .eq(UserUniqueIndex::getCardNo, "1001")
        );
    }

    /**
     * replace into测试(插入后不返回主键)
     */
    @Test
    public void replaceInto() {
        this.userMultipleTableUniqueIndexService.delete(tableNum, new DeleteWrapper<UserUniqueIndex>()
                .eq(UserUniqueIndex::getCardNo, "1001")
        );
        UserUniqueIndex u1 = new UserUniqueIndex("1001", "zhangsan");
        UserUniqueIndex u2 = new UserUniqueIndex("1001", "lisi");
        UserUniqueIndex u3 = new UserUniqueIndex("1001", "wangwu");
        boolean flag1 = this.userMultipleTableUniqueIndexService.replaceInto(tableNum, u1);
        boolean flag2 = this.userMultipleTableUniqueIndexService.replaceInto(tableNum, u2);
        boolean flag3 = this.userMultipleTableUniqueIndexService.replaceInto(tableNum, u3);
        assert flag1 && flag2 && flag3;
        assert u1.getId() == null && u2.getId() == null && u3.getId() == null;
        List<UserUniqueIndex> list = this.userMultipleTableUniqueIndexService.selectList(tableNum, new SelectWrapper<UserUniqueIndex>()
                .eq(UserUniqueIndex::getCardNo, "1001")
        );
        assert list.size() == 1 && list.get(0).getName().equals("wangwu");
        this.userMultipleTableUniqueIndexService.delete(tableNum, new DeleteWrapper<UserUniqueIndex>()
                .eq(UserUniqueIndex::getCardNo, "1001")
        );
    }

    /**
     * 唯一索引冲突更新测试
     */
    @Test
    public void insertOrUpdateWithUniqueIndex() {
        this.userMultipleTableUniqueIndexService.delete(tableNum, new DeleteWrapper<UserUniqueIndex>()
                .eq(UserUniqueIndex::getCardNo, "1001")
        );
        UserUniqueIndex u1 = new UserUniqueIndex("1001", "zhangsan");
        UserUniqueIndex u2 = new UserUniqueIndex("1001", "lisi");
        UserUniqueIndex u3 = new UserUniqueIndex("1001", "wangwu");
        boolean flag1 = this.userMultipleTableUniqueIndexService.insertOrUpdateWithUniqueIndex(tableNum, u1);
        boolean flag2 = this.userMultipleTableUniqueIndexService.insertOrUpdateWithUniqueIndex(tableNum, u2);
        boolean flag3 = this.userMultipleTableUniqueIndexService.insertOrUpdateWithUniqueIndex(tableNum, u3);
        assert flag1 && flag2 && flag3;
        assert u1.getId().equals(u2.getId()) && u2.getId().equals(u3.getId());
        List<UserUniqueIndex> list = this.userMultipleTableUniqueIndexService.selectList(tableNum, new SelectWrapper<UserUniqueIndex>()
                .eq(UserUniqueIndex::getCardNo, "1001")
        );
        assert list.size() == 1 && list.get(0).getName().equals("wangwu");
        this.userMultipleTableUniqueIndexService.delete(tableNum, new DeleteWrapper<UserUniqueIndex>()
                .eq(UserUniqueIndex::getCardNo, "1001")
        );
    }
}
