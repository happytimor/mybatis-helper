package io.github.happytimor.mybatis.helper.core.service;

import io.github.happytimor.mybatis.helper.core.mapper.UniqueIndexEnhanceForMultipleTableMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author chenpeng
 */
public class UniqueIndexEnhanceForMultipleTableService<M extends UniqueIndexEnhanceForMultipleTableMapper<T>, T> extends MultipleTableService<M, T> {
    private static final int BATCH_SIZE = 5000;

    @Autowired(required = false)
    private M uniqueIndexEnhanceForMultipleTableMapper;

    /**
     * 有唯一索引的前提下插入或更新数据
     * 主要依靠 duplicate key update 来实现
     *
     * @param entity 对象
     * @return 操作是否成功
     */
    public boolean insertOrUpdateWithUniqueIndex(String tableNum, T entity) {
        if (entity == null) {
            return false;
        }
        return this.uniqueIndexEnhanceForMultipleTableMapper.insertOrUpdateWithUniqueIndex(tableNum, entity);
    }

    public void batchInsertOrUpdateWithUniqueIndex(String tableNum, List<T> list) {
        this.batchInsertOrUpdateWithUniqueIndex(tableNum, list, BATCH_SIZE);
    }

    public void batchInsertOrUpdateWithUniqueIndex(String tableNum, List<T> list, int batchSize) {
        executeBatch(list, batchSize,
                batch -> this.uniqueIndexEnhanceForMultipleTableMapper.batchInsertOrUpdateWithUniqueIndex(tableNum, batch));
    }

    /**
     * insert ignore into
     *
     * @param entity the entity to  insert
     * @return true if insert success
     */
    public boolean insertIgnoreInto(String tableNum, T entity) {
        if (entity == null) {
            return false;
        }
        return this.uniqueIndexEnhanceForMultipleTableMapper.insertIgnoreInto(tableNum, entity);
    }

    public void batchInsertIgnoreInto(String tableNum, List<T> list) {
        this.batchInsertIgnoreInto(tableNum, list, BATCH_SIZE);
    }

    public void batchInsertIgnoreInto(String tableNum, List<T> list, int batchSize) {
        executeBatch(list, batchSize,
                batch -> this.uniqueIndexEnhanceForMultipleTableMapper.batchInsertIgnoreInto(tableNum, batch));
    }

    /**
     * replace info
     *
     * @param entity the entity to  insert
     * @return true if insert success
     */
    public boolean replaceInto(String tableNum, T entity) {
        if (entity == null) {
            return false;
        }
        return this.uniqueIndexEnhanceForMultipleTableMapper.replaceInto(tableNum, entity);
    }

    public void batchReplaceInto(String tableNum, List<T> list) {
        this.batchReplaceInto(tableNum, list, BATCH_SIZE);
    }

    public void batchReplaceInto(String tableNum, List<T> list, int batchSize) {
        executeBatch(list, batchSize,
                batch -> this.uniqueIndexEnhanceForMultipleTableMapper.batchReplaceInto(tableNum, batch));
    }

    private void executeBatch(List<T> list, int batchSize, Consumer<List<T>> operation) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (batchSize <= 0) {
            throw new IllegalArgumentException("batchSize must be greater than 0");
        }
        if (list.size() <= batchSize) {
            operation.accept(list);
            return;
        }
        List<T> batch = new ArrayList<>(batchSize);
        for (T entity : list) {
            batch.add(entity);
            if (batch.size() == batchSize) {
                operation.accept(batch);
                batch = new ArrayList<>(batchSize);
            }
        }
        if (!batch.isEmpty()) {
            operation.accept(batch);
        }
    }
}
