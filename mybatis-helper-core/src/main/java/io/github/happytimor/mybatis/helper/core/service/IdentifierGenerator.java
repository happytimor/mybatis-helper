package io.github.happytimor.mybatis.helper.core.service;

import java.util.List;

/**
 * @author chenpeng
 */
public interface IdentifierGenerator {

    /**
     * id生成
     *
     * @param identity id生成标识符
     * @return 下个id
     */
    Number nextId(String identity);

    /**
     * id生成
     *
     * @param identity  id生成标识符
     * @param batchSize 生成数量
     * @return 可用id列表
     */
    List<Number> nextIdBatch(String identity, int batchSize);
}
