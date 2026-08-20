package io.github.happytimor.mybatis.helper.generator.util;

import io.github.happytimor.mybatis.helper.generator.constant.GenConstants;
import io.github.happytimor.mybatis.helper.generator.domain.GenTableColumn;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GenUtilsTest {

    @Test
    public void shouldPreserveDecimalPrecision() {
        GenTableColumn column = column("DECIMAL", 10, 2);

        GenUtils.initColumnField(column);

        assertEquals(GenConstants.TYPE_BIGDECIMAL, column.getJavaType());
    }

    @Test
    public void shouldUseLongForBigint() {
        GenTableColumn column = column("BIGINT", 19, 0);

        GenUtils.initColumnField(column);

        assertEquals(GenConstants.TYPE_LONG, column.getJavaType());
    }

    @Test
    public void shouldUseBigDecimalForJdbcNumericType() {
        GenTableColumn column = column("NUMERIC", 20, 4);

        GenUtils.initColumnField(column);

        assertEquals(GenConstants.TYPE_BIGDECIMAL, column.getJavaType());
    }

    @Test
    public void shouldUseMetadataLengthForLongTextQueries() {
        GenTableColumn column = column("VARCHAR", 600, 0);

        GenUtils.initColumnField(column);

        assertEquals(GenConstants.QUERY_LIKE, column.getQueryType());
    }

    private static GenTableColumn column(String type, int size, int decimalDigits) {
        GenTableColumn column = new GenTableColumn();
        column.setColumnName("amount");
        column.setColumnType(type);
        column.setColumnSize(size);
        column.setDecimalDigits(decimalDigits);
        return column;
    }
}
