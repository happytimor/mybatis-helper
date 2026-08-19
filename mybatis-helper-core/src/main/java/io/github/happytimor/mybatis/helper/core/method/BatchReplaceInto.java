package io.github.happytimor.mybatis.helper.core.method;

import io.github.happytimor.mybatis.helper.core.common.SqlMethod;

/**
 * Batch replace rows without returning generated keys.
 *
 * @author chenpeng
 */
public class BatchReplaceInto extends AbstractBatchUniqueIndexMethod {
    public BatchReplaceInto() {
        super(SqlMethod.BATCH_REPLACE_INTO, false, false);
    }
}
