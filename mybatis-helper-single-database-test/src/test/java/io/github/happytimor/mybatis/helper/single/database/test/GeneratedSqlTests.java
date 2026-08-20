package io.github.happytimor.mybatis.helper.single.database.test;

import io.github.happytimor.mybatis.helper.core.common.Params;
import io.github.happytimor.mybatis.helper.core.wrapper.AbstractWrapper;
import io.github.happytimor.mybatis.helper.core.wrapper.SelectWrapper;
import io.github.happytimor.mybatis.helper.core.wrapper.UpdateWrapper;
import io.github.happytimor.mybatis.helper.single.database.test.domain.User;
import io.github.happytimor.mybatis.helper.single.database.test.mapper.UserMapper;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * 直接校验 MyBatis 最终生成的 SQL 以及有序绑定参数。
 *
 * @author chenpeng
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GeneratedSqlTests {

    private static final String SELECT_COUNT_SQL = "SELECT COUNT(*) FROM `user` ";

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    @Test
    public void compareOperators() {
        SelectWrapper<User> wrapper = new SelectWrapper<>();
        wrapper.gt(User::getAge, 18)
                .ge(User::getId, 1)
                .eq(User::getFlag, "compare")
                .le(User::getAge, 60)
                .lt(User::getId, 100)
                .ne(User::getName, "unknown");

        assertGeneratedSql("selectCount", wrapper,
                SELECT_COUNT_SQL + "WHERE `age` > ? AND `id` >= ? AND `flag` = ? "
                        + "AND `age` <= ? AND `id` < ? AND `name` <> ?",
                18, 1, "compare", 60, 100, "unknown");
    }

    @Test
    public void likeOperators() {
        SelectWrapper<User> wrapper = new SelectWrapper<>();
        wrapper.like(User::getName, "middle")
                .likeLeft(User::getName, "prefix")
                .likeRight(User::getName, "suffix")
                .notLike(User::getStrangeName, "excluded");

        assertGeneratedSql("selectCount", wrapper,
                SELECT_COUNT_SQL + "WHERE `name` LIKE ? AND `name` LIKE ? AND `name` LIKE ? "
                        + "AND `greater_then_60s_a_b_c_3_A_NN_axiba` NOT LIKE ?",
                "%middle%", "prefix%", "%suffix", "%excluded%");
    }

    @Test
    public void betweenOperators() {
        LocalDateTime startTime = LocalDateTime.of(2026, 8, 1, 10, 30);
        LocalDateTime endTime = startTime.plusDays(7);
        LocalDate startDate = LocalDate.of(2026, 8, 1);
        LocalDate endDate = startDate.plusDays(7);

        SelectWrapper<User> wrapper = new SelectWrapper<>();
        wrapper.between(User::getAge, 18, 60)
                .notBetween(User::getLastLoginTime, startTime, endTime)
                .between(User::getLastLoginTime, startDate, endDate);

        assertGeneratedSql("selectCount", wrapper,
                SELECT_COUNT_SQL + "WHERE `age` BETWEEN ? AND ? "
                        + "AND `last_login_time` NOT BETWEEN ? AND ? "
                        + "AND `last_login_time` BETWEEN ? AND ?",
                18, 60, startTime, endTime, startDate, endDate);
    }

    @Test
    public void nullAndEmptyOperators() {
        SelectWrapper<User> wrapper = new SelectWrapper<>();
        wrapper.isNull(User::getNullableAge)
                .isNotNull(User::getAge)
                .isEmpty(User::getName)
                .isNotEmpty(User::getStrangeName);

        assertGeneratedSql("selectCount", wrapper,
                SELECT_COUNT_SQL + "WHERE `nullable_age` IS NULL AND `age` IS NOT NULL "
                        + "AND `name` = '' AND `greater_then_60s_a_b_c_3_A_NN_axiba` != ''");
    }

    @Test
    public void collectionAndNestedQuery() {
        SelectWrapper<User> wrapper = new SelectWrapper<>();
        wrapper.in(User::getAge, Arrays.asList(18, 20, 22))
                .notIn(User::getId, Arrays.asList(10, 11))
                .eqNested(User::getAge, nested -> nested.applySelectWrapper(User.class)
                        .select(User::getAge)
                        .eq(User::getFlag, "nested")
                        .limit(1));

        assertGeneratedSql("selectCount", wrapper,
                SELECT_COUNT_SQL + "WHERE `age` IN (?,?,?) AND `id` NOT IN (?,?) "
                        + "AND `age` = (SELECT `age` FROM `user` WHERE `flag` = ? LIMIT 1)",
                18, 20, 22, 10, 11, "nested");
    }

    @Test
    public void orderAndLimit() {
        SelectWrapper<User> wrapper = new SelectWrapper<>();
        wrapper.eq(User::getFlag, "ordered");
        wrapper
                .orderByAsc(User::getAge)
                .orderByDesc(User::getId)
                .limit(10);

        assertGeneratedSql("selectList", wrapper,
                "SELECT * FROM `user` WHERE `flag` = ? ORDER BY `age` ASC, `id` DESC LIMIT 10",
                "ordered");
    }

    @Test
    public void updateSql() {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.set(User::getMarried, true)
                .set(User::getName, "updated")
                .plus(User::getAge, 1);
        wrapper.eq(User::getFlag, "update");

        assertGeneratedSql("update", wrapper,
                "UPDATE `user` SET `married` = ?, `name` = ?, `age` = `age` + ? WHERE `flag` = ?",
                true, "updated", 1, "update");
    }

    private void assertGeneratedSql(String mapperMethod, AbstractWrapper<User> wrapper,
                                    String expectedSql, Object... expectedParameters) {
        Configuration configuration = sqlSessionFactory.getConfiguration();
        MappedStatement statement = configuration.getMappedStatement(
                UserMapper.class.getName() + "." + mapperMethod);
        Map<String, Object> parameterObject = Collections.singletonMap(Params.WRAPPER, wrapper);
        BoundSql boundSql = statement.getBoundSql(parameterObject);

        assertEquals(normalizeSql(expectedSql), normalizeSql(boundSql.getSql()));
        assertEquals(Arrays.asList(expectedParameters), resolveParameters(configuration, boundSql, parameterObject));
    }

    private List<Object> resolveParameters(Configuration configuration, BoundSql boundSql,
                                           Map<String, Object> parameterObject) {
        return boundSql.getParameterMappings().stream()
                .map(ParameterMapping::getProperty)
                .map(property -> boundSql.hasAdditionalParameter(property)
                        ? boundSql.getAdditionalParameter(property)
                        : configuration.newMetaObject(parameterObject).getValue(property))
                .collect(Collectors.toList());
    }

    private String normalizeSql(String sql) {
        return sql.replaceAll("\\s+", " ").trim();
    }
}
