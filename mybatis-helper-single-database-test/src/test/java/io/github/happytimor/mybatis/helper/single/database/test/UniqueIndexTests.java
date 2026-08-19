package io.github.happytimor.mybatis.helper.single.database.test;

import io.github.happytimor.mybatis.helper.core.wrapper.DeleteWrapper;
import io.github.happytimor.mybatis.helper.core.wrapper.SelectWrapper;
import io.github.happytimor.mybatis.helper.single.database.test.domain.UserUniqueIndex;
import io.github.happytimor.mybatis.helper.single.database.test.service.GenerateService;
import io.github.happytimor.mybatis.helper.single.database.test.service.UserUniqueIndexService;
import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 方法测试
 *
 * @author chenpeng
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UniqueIndexTests {
    @Resource
    private GenerateService generateService;
    @Resource
    private UserUniqueIndexService userUniqueIndexService;

    @Resource
    private DataSource dataSource;

    /**
     * 单条插入测试
     */
    @Test
    public void insert() {
        UserUniqueIndex userUniqueIndex = new UserUniqueIndex("1001", "zhangsan");
        assert userUniqueIndex.getId() == null;
        this.userUniqueIndexService.insert(userUniqueIndex);
        assert userUniqueIndex.getId() != null;

        UserUniqueIndex dbExistsUser = this.userUniqueIndexService.selectById(userUniqueIndex.getId());
        assert dbExistsUser.getName().equals(userUniqueIndex.getName()) && dbExistsUser.getCardNo().equals(userUniqueIndex.getCardNo());

        this.userUniqueIndexService.deleteById(dbExistsUser.getId());
        UserUniqueIndex dbUnExistsUser = this.userUniqueIndexService.selectById(dbExistsUser.getId());
        assert dbUnExistsUser == null;
    }

    /**
     * insert ignore into 语法测试
     */
    @Test
    public void insertIgnoreInto() {
        this.userUniqueIndexService.delete(new DeleteWrapper<UserUniqueIndex>()
                .eq(UserUniqueIndex::getCardNo, "1001")
        );
        boolean flag1 = this.userUniqueIndexService.insertIgnoreInto(new UserUniqueIndex("1001", "zhangsan"));
        boolean flag2 = this.userUniqueIndexService.insertIgnoreInto(new UserUniqueIndex("1001", "lisi"));
        boolean flag3 = this.userUniqueIndexService.insertIgnoreInto(new UserUniqueIndex("1001", "wangwu"));
        assert flag1 && !flag2 && !flag3;

        List<UserUniqueIndex> list = this.userUniqueIndexService.selectList(new SelectWrapper<UserUniqueIndex>()
                .eq(UserUniqueIndex::getCardNo, "1001")
        );
        assert list.size() == 1 && list.get(0).getName().equals("zhangsan");
        this.userUniqueIndexService.delete(new DeleteWrapper<UserUniqueIndex>()
                .eq(UserUniqueIndex::getCardNo, "1001")
        );
    }

    /**
     * batch insert ignore into 语法测试
     */
    @Test
    public void batchInsertIgnoreInto() {
        this.userUniqueIndexService.delete(new DeleteWrapper<UserUniqueIndex>()
                .in(UserUniqueIndex::getCardNo, Arrays.asList("batch-ignore-1", "batch-ignore-2"))
        );
        this.userUniqueIndexService.batchInsertIgnoreInto(Arrays.asList(
                new UserUniqueIndex("batch-ignore-1", "zhangsan"),
                new UserUniqueIndex("batch-ignore-1", "lisi"),
                new UserUniqueIndex("batch-ignore-2", "wangwu")
        ), 2);

        List<UserUniqueIndex> list = this.userUniqueIndexService.selectList(new SelectWrapper<UserUniqueIndex>()
                .in(UserUniqueIndex::getCardNo, Arrays.asList("batch-ignore-1", "batch-ignore-2"))
                .orderByAsc(UserUniqueIndex::getCardNo)
        );
        assert list.size() == 2;
        assert list.get(0).getName().equals("zhangsan");
        assert list.get(1).getName().equals("wangwu");
        this.userUniqueIndexService.delete(new DeleteWrapper<UserUniqueIndex>()
                .in(UserUniqueIndex::getCardNo, Arrays.asList("batch-ignore-1", "batch-ignore-2"))
        );
    }

    /**
     * replace into测试(插入后不返回主键)
     */
    @Test
    public void replaceInto() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            String product = connection.getMetaData().getDatabaseProductName();
            Assume.assumeFalse("H2 REPLACE INTO does not match MySQL delete-then-insert semantics",
                    product != null && product.toLowerCase().contains("h2"));
        }
        this.userUniqueIndexService.delete(new DeleteWrapper<UserUniqueIndex>()
                .eq(UserUniqueIndex::getCardNo, "1001")
        );
        UserUniqueIndex u1 = new UserUniqueIndex("1001", "zhangsan");
        UserUniqueIndex u2 = new UserUniqueIndex("1001", "lisi");
        UserUniqueIndex u3 = new UserUniqueIndex("1001", "wangwu");
        boolean flag1 = this.userUniqueIndexService.replaceInto(u1);
        boolean flag2 = this.userUniqueIndexService.replaceInto(u2);
        boolean flag3 = this.userUniqueIndexService.replaceInto(u3);
        assert flag1 && flag2 && flag3;
        assert u1.getId() == null && u2.getId() == null && u3.getId() == null;
        List<UserUniqueIndex> list = this.userUniqueIndexService.selectList(new SelectWrapper<UserUniqueIndex>()
                .eq(UserUniqueIndex::getCardNo, "1001")
        );
        assert list.size() == 1 && list.get(0).getName().equals("wangwu");
        this.userUniqueIndexService.delete(new DeleteWrapper<UserUniqueIndex>()
                .eq(UserUniqueIndex::getCardNo, "1001")
        );
    }

    /**
     * 唯一索引冲突更新测试
     */
    @Test
    public void insertOrUpdateWithUniqueIndex() {
        this.userUniqueIndexService.delete(new DeleteWrapper<UserUniqueIndex>()
                .eq(UserUniqueIndex::getCardNo, "1001")
        );
        UserUniqueIndex u1 = new UserUniqueIndex("1001", "zhangsan");
        UserUniqueIndex u2 = new UserUniqueIndex("1001", "lisi");
        UserUniqueIndex u3 = new UserUniqueIndex("1001", "wangwu");
        boolean flag1 = this.userUniqueIndexService.insertOrUpdateWithUniqueIndex(u1);
        boolean flag2 = this.userUniqueIndexService.insertOrUpdateWithUniqueIndex(u2);
        boolean flag3 = this.userUniqueIndexService.insertOrUpdateWithUniqueIndex(u3);
        assert flag1 && flag2 && flag3;
        List<UserUniqueIndex> list = this.userUniqueIndexService.selectList(new SelectWrapper<UserUniqueIndex>()
                .eq(UserUniqueIndex::getCardNo, "1001")
        );
        assert list.size() == 1 && list.get(0).getName().equals("wangwu");
        assert u1.getId() != null && Objects.equals(u1.getId(), list.get(0).getId());
        this.userUniqueIndexService.delete(new DeleteWrapper<UserUniqueIndex>()
                .eq(UserUniqueIndex::getCardNo, "1001")
        );
    }

    /**
     * 批量唯一索引冲突更新测试
     */
    @Test
    public void batchInsertOrUpdateWithUniqueIndex() {
        this.userUniqueIndexService.delete(new DeleteWrapper<UserUniqueIndex>()
                .in(UserUniqueIndex::getCardNo, Arrays.asList("batch-upsert-1", "batch-upsert-2"))
        );
        this.userUniqueIndexService.insert(new UserUniqueIndex("batch-upsert-1", "old-name"));
        this.userUniqueIndexService.batchInsertOrUpdateWithUniqueIndex(Arrays.asList(
                new UserUniqueIndex("batch-upsert-1", "new-name"),
                new UserUniqueIndex("batch-upsert-2", "second-user")
        ));

        List<UserUniqueIndex> list = this.userUniqueIndexService.selectList(new SelectWrapper<UserUniqueIndex>()
                .in(UserUniqueIndex::getCardNo, Arrays.asList("batch-upsert-1", "batch-upsert-2"))
                .orderByAsc(UserUniqueIndex::getCardNo)
        );
        assert list.size() == 2;
        assert list.get(0).getName().equals("new-name");
        assert list.get(1).getName().equals("second-user");
        this.userUniqueIndexService.delete(new DeleteWrapper<UserUniqueIndex>()
                .in(UserUniqueIndex::getCardNo, Arrays.asList("batch-upsert-1", "batch-upsert-2"))
        );
    }
}
