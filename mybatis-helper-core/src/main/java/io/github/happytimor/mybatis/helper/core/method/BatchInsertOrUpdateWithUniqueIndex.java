package io.github.happytimor.mybatis.helper.core.method;

import io.github.happytimor.mybatis.helper.core.common.SqlMethod;

/**
 * Batch insert rows and update them when a unique index conflicts.
 *
 * @author chenpeng
 */
public class BatchInsertOrUpdateWithUniqueIndex extends AbstractBatchUniqueIndexMethod {
    public BatchInsertOrUpdateWithUniqueIndex() {
        super(SqlMethod.BATCH_INSERT_OR_UPDATE_WITH_UNIQUE_INDEX, true, true);
    }
}
