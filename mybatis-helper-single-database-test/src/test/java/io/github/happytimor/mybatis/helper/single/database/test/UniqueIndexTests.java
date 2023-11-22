package io.github.happytimor.mybatis.helper.single.database.test;

import io.github.happytimor.mybatis.helper.core.wrapper.DeleteWrapper;
import io.github.happytimor.mybatis.helper.core.wrapper.SelectWrapper;
import io.github.happytimor.mybatis.helper.single.database.test.domain.UserUniqueIndex;
import io.github.happytimor.mybatis.helper.single.database.test.service.GenerateService;
import io.github.happytimor.mybatis.helper.single.database.test.service.UserUniqueIndexService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

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
     * replace into测试(插入后不返回主键)
     */
    @Test
    public void replaceInto() {
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
        assert u1.getId().equals(u2.getId()) && u2.getId().equals(u3.getId());
        List<UserUniqueIndex> list = this.userUniqueIndexService.selectList(new SelectWrapper<UserUniqueIndex>()
                .eq(UserUniqueIndex::getCardNo, "1001")
        );
        assert list.size() == 1 && list.get(0).getName().equals("wangwu");
        this.userUniqueIndexService.delete(new DeleteWrapper<UserUniqueIndex>()
                .eq(UserUniqueIndex::getCardNo, "1001")
        );
    }
}
