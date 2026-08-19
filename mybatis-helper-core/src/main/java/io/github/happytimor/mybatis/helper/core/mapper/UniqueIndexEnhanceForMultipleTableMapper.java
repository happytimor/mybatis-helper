package io.github.happytimor.mybatis.helper.core.mapper;

import io.github.happytimor.mybatis.helper.core.common.Params;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * enchanced mapper for single table which contains unique key
 *
 * @author chenpeng
 */
public interface UniqueIndexEnhanceForMultipleTableMapper<T> extends MultipleTableMapper<T> {

    /**
     * insert one row if not exists
     *
     * @param entity insert object
     * @return true if success
     */
    boolean insertIgnoreInto(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.ENTITY) T entity);

    /**
     * batch insert rows and ignore unique index conflicts
     *
     * @param tableNum table suffix
     * @param list insert object list
     */
    void batchInsertIgnoreInto(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.LIST) Collection<T> list);

    /**
     * insert override
     *
     * @param entity insert object
     * @return true if success
     */
    boolean replaceInto(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.ENTITY) T entity);

    /**
     * batch replace rows
     *
     * @param tableNum table suffix
     * @param list insert object list
     */
    void batchReplaceInto(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.LIST) Collection<T> list);

    /**
     * insert or update one row
     * depend on duplicate key update
     *
     * @param entity object
     * @return true if operate success
     */
    boolean insertOrUpdateWithUniqueIndex(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.ENTITY) T entity);

    /**
     * batch insert or update rows depending on duplicate keys
     *
     * @param tableNum table suffix
     * @param list insert object list
     */
    void batchInsertOrUpdateWithUniqueIndex(@Param(Params.TABLE_NUM) String tableNum,
                                            @Param(Params.LIST) Collection<T> list);


}
