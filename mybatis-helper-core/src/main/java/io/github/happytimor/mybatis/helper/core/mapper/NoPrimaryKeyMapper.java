package io.github.happytimor.mybatis.helper.core.mapper;

import io.github.happytimor.mybatis.helper.core.common.Params;
import io.github.happytimor.mybatis.helper.core.wrapper.AbstractWrapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * for single table which has no primary key
 *
 * @author chenpeng
 */
public interface NoPrimaryKeyMapper<T> {

    /**
     * insert one row
     *
     * @param entity insert object
     */
    void insert(@Param(Params.ENTITY) T entity);

    /**
     * batch insert rows
     *
     * @param list insert row list
     */
    void batchInsert(@Param(Params.LIST) Collection<T> list);

    /**
     * query rows by customized condition
     *
     * @param selectWrapper select condition
     * @return object list
     */
    List<T> selectList(@Param(Params.WRAPPER) AbstractWrapper<T> selectWrapper);

    /**
     * query total count
     *
     * @param selectWrapper query condition
     * @return total count
     */
    long selectCount(@Param(Params.WRAPPER) AbstractWrapper<T> selectWrapper);

    /**
     * query one row
     *
     * @param selectWrapper select condition
     * @return single object
     */
    T selectOne(@Param(Params.WRAPPER) AbstractWrapper<T> selectWrapper);

    /**
     * update row
     *
     * @param updateWrapper update condition
     * @return update count
     */
    int update(@Param(Params.WRAPPER) AbstractWrapper<T> updateWrapper);

    /**
     * delete rows with customized condition
     *
     * @param deleteWrapper delete condition
     * @return delete count
     */
    int delete(@Param(Params.WRAPPER) AbstractWrapper<T> deleteWrapper);

    /**
     * insert or update one row
     * depend on duplicate key update
     *
     * @param entity object
     * @return true if operate success
     */
    boolean insertOrUpdateWithUniqueIndex(@Param(Params.ENTITY) T entity);
}
