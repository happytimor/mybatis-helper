package io.github.happytimor.mybatis.helper.core.service;

import io.github.happytimor.mybatis.helper.core.mapper.UniqueIndexEnhanceForMultipleTableMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author chenpeng
 */
public class UniqueIndexEnhanceForMultipleTableService<M extends UniqueIndexEnhanceForMultipleTableMapper<T>, T> extends MultipleTableService<M, T> {
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
}
