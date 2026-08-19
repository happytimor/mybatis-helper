package io.github.happytimor.mybatis.helper.core.service;

import io.github.happytimor.mybatis.helper.core.mapper.UniqueIndexEnhanceMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author chenpeng
 */
public class UniqueIndexEnhanceService<M extends UniqueIndexEnhanceMapper<T>, T> extends BaseService<M, T> {
    private static final int BATCH_SIZE = 5000;

    @Autowired(required = false)
    private M uniqueIndexEnhanceMapper;

    /**
     * 有唯一索引的前提下插入或更新数据
     * 主要依靠 duplicate key update 来实现
     *
     * @param entity 对象
     * @return 操作是否成功
     */
    public boolean insertOrUpdateWithUniqueIndex(T entity) {
        if (entity == null) {
            return false;
        }
        return this.uniqueIndexEnhanceMapper.insertOrUpdateWithUniqueIndex(entity);
    }

    /**
     * 批量插入或根据唯一索引更新数据
     *
     * @param list 对象列表
     */
    public void batchInsertOrUpdateWithUniqueIndex(List<T> list) {
        this.batchInsertOrUpdateWithUniqueIndex(list, BATCH_SIZE);
    }

    /**
     * 分批插入或根据唯一索引更新数据
     *
     * @param list      对象列表
     * @param batchSize 分批大小
     */
    public void batchInsertOrUpdateWithUniqueIndex(List<T> list, int batchSize) {
        executeBatch(list, batchSize, this.uniqueIndexEnhanceMapper::batchInsertOrUpdateWithUniqueIndex);
    }

    /**
     * insert ignore into
     *
     * @param entity the entity to  insert
     * @return true if insert success
     */
    public boolean insertIgnoreInto(T entity) {
        if (entity == null) {
            return false;
        }
        return this.uniqueIndexEnhanceMapper.insertIgnoreInto(entity);
    }

    /**
     * batch insert ignore into
     *
     * @param list the entities to insert
     */
    public void batchInsertIgnoreInto(List<T> list) {
        this.batchInsertIgnoreInto(list, BATCH_SIZE);
    }

    /**
     * batch insert ignore into
     *
     * @param list      the entities to insert
     * @param batchSize batch size
     */
    public void batchInsertIgnoreInto(List<T> list, int batchSize) {
        executeBatch(list, batchSize, this.uniqueIndexEnhanceMapper::batchInsertIgnoreInto);
    }

    /**
     * replace info
     *
     * @param entity the entity to  insert
     * @return true if insert success
     */
    public boolean replaceInto(T entity) {
        if (entity == null) {
            return false;
        }
        return this.uniqueIndexEnhanceMapper.replaceInto(entity);
    }

    /**
     * batch replace into
     *
     * @param list the entities to insert
     */
    public void batchReplaceInto(List<T> list) {
        this.batchReplaceInto(list, BATCH_SIZE);
    }

    /**
     * batch replace into
     *
     * @param list      the entities to insert
     * @param batchSize batch size
     */
    public void batchReplaceInto(List<T> list, int batchSize) {
        executeBatch(list, batchSize, this.uniqueIndexEnhanceMapper::batchReplaceInto);
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
