package io.github.happytimor.mybatis.helper.core.service;

import io.github.happytimor.mybatis.helper.core.mapper.UniqueIndexEnhanceMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author chenpeng
 */
public class UniqueIndexEnhanceService<M extends UniqueIndexEnhanceMapper<T>, T> extends BaseService<M, T> {
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
}
