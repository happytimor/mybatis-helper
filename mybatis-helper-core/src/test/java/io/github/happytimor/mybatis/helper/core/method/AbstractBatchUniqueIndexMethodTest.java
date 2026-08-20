package io.github.happytimor.mybatis.helper.core.method;

import io.github.happytimor.mybatis.helper.core.metadata.Result;
import io.github.happytimor.mybatis.helper.core.metadata.TableInfo;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class AbstractBatchUniqueIndexMethodTest {

    @Test
    public void shouldGenerateNoOpUpdateForPrimaryKeyOnlyEntity() throws Exception {
        TableInfo tableInfo = new TableInfo();
        tableInfo.setKeyColumn("id");
        tableInfo.setResultList(Collections.singletonList(new Result("id", "id", false)));
        Method method = AbstractBatchUniqueIndexMethod.class
                .getDeclaredMethod("generateUpdateScript", TableInfo.class);
        method.setAccessible(true);

        String sql = (String) method.invoke(new BatchInsertOrUpdateWithUniqueIndex(), tableInfo);

        assertEquals("`id`=VALUES(`id`)", sql);
    }
}
