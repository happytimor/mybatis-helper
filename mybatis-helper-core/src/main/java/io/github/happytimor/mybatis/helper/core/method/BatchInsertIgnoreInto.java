package io.github.happytimor.mybatis.helper.core.method;

import io.github.happytimor.mybatis.helper.core.common.SqlMethod;

/**
 * Batch insert rows and ignore unique index conflicts.
 *
 * @author chenpeng
 */
public class BatchInsertIgnoreInto extends AbstractBatchUniqueIndexMethod {
    public BatchInsertIgnoreInto() {
        super(SqlMethod.BATCH_INSERT_IGNORE_INTO, true, false);
    }
}
