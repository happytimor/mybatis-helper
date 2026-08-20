package io.github.happytimor.mybatis.helper.core.handler;

import io.github.happytimor.mybatis.helper.core.common.Params;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.Configuration;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class IdentifierGeneratorHandlerTest {

    @Test
    public void shouldIgnoreEmptyBatch() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Params.LIST, Collections.emptyList());

        new IdentifierGeneratorHandler(new Configuration())
                .processParameter(parameters, SqlCommandType.INSERT);
    }
}
