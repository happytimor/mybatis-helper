package io.github.happytimor.mybatis.helper.core.mapper;

import io.github.happytimor.mybatis.helper.core.common.Params;
import org.apache.ibatis.annotations.Param;

/**
 * enchanced mapper for single table which contains unique key
 *
 * @author chenpeng
 */
public interface UniqueIndexEnhanceMapper<T> extends BaseMapper<T> {

    /**
     * insert one row if not exists
     *
     * @param entity insert object
     */
    boolean insertIgnoreInto(@Param(Params.ENTITY) T entity);

    /**
     * insert override
     *
     * @param entity insert object
     */
    boolean replaceInto(@Param(Params.ENTITY) T entity);

    /**
     * insert or update one row
     * depend on duplicate key update
     *
     * @param entity object
     * @return true if operate success
     */
    boolean insertOrUpdateWithUniqueIndex(@Param(Params.ENTITY) T entity);


}
