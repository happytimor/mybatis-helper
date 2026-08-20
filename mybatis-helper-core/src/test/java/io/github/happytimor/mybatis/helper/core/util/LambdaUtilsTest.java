package io.github.happytimor.mybatis.helper.core.util;

import io.github.happytimor.mybatis.helper.core.annotation.TableColumn;
import io.github.happytimor.mybatis.helper.core.metadata.Result;
import io.github.happytimor.mybatis.helper.core.metadata.TableInfo;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class LambdaUtilsTest {

    @Test
    public void shouldUseMappedColumnNameForCamelCasePrimaryKey() {
        TableInfo tableInfo = LambdaUtils.parseTableInfo(CamelPrimaryKeyEntity.class);

        assertEquals("user_id", tableInfo.getKeyColumn());
        assertEquals("userId", tableInfo.getKeyProperty());
        List<String> properties = tableInfo.getResultList().stream()
                .map(Result::getProperty)
                .collect(Collectors.toList());
        assertFalse(properties.contains("sharedValue"));
    }

    @Test
    public void shouldKeepPropertyAndColumnNamesSeparateForOverriddenPrimaryKey() {
        TableInfo tableInfo = LambdaUtils.parseTableInfo(OverriddenPrimaryKeyEntity.class);

        assertEquals("user_id", tableInfo.getKeyColumn());
        assertEquals("id", tableInfo.getKeyProperty());
    }

    private static class CamelPrimaryKeyEntity {
        private static String sharedValue;

        @TableColumn(primaryKey = true)
        private Long userId;
        private String name;
    }

    private static class OverriddenPrimaryKeyEntity {
        @TableColumn(value = "user_id", primaryKey = true)
        private Long id;
    }
}
