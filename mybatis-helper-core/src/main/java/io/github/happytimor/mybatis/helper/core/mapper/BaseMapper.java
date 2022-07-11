package io.github.happytimor.mybatis.helper.core.mapper;

import io.github.happytimor.mybatis.helper.core.common.Params;
import io.github.happytimor.mybatis.helper.core.wrapper.AbstractWrapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * basic mapper for single ordinary table which contains auto-increment primary key
 *
 * @author chenpeng
 */
public interface BaseMapper<T> {

    /**
     * insert one row
     *
     * @param entity insert object
     */
    void insert(@Param(Params.ENTITY) T entity);

    /**
     * batch insert rows
     *
     * @param list insert object list
     */
    void batchInsert(@Param(Params.LIST) Collection<T> list);

    /**
     * delete one row by primary key
     *
     * @param id primary key
     * @return true if delete success
     */
    boolean deleteById(@Param(Params.ID) Number id);

    /**
     * delete rows by primary key list
     *
     * @param idList primary id list
     * @return delete count
     */
    int deleteByIdList(@Param(Params.ID_LIST) Collection<? extends Number> idList);

    /**
     * update row by primary key
     *
     * @param entity object list
     * @return true if update success
     */
    boolean updateById(@Param(Params.ENTITY) T entity);

    /**
     * batch update rows by primary key
     *
     * @param list update object list
     * @return true if update success
     */
    boolean batchUpdateById(@Param(Params.LIST) Collection<T> list);

    /**
     * query one row by primary key
     *
     * @param id primary key
     * @return row object
     */
    T selectById(@Param(Params.ID) Number id);

    /**
     * query rows by primary key list
     *
     * @param idList primary id list
     * @return row list
     */
    List<T> selectByIdList(@Param(Params.ID_LIST) Collection<? extends Number> idList);

    /**
     * query rows by customized condition
     *
     * @param selectWrapper select condition
     * @return rows object
     */
    List<T> selectList(@Param(Params.WRAPPER) AbstractWrapper<T> selectWrapper);

    /**
     * query map object
     *
     * @param selectWrapper select condition
     * @return map object
     */
    Map<String, Object> selectMap(@Param(Params.WRAPPER) AbstractWrapper<T> selectWrapper);

    /**
     * query map object list
     *
     * @param selectWrapper condition wrapper
     * @return map list
     */
    List<Map<String, Object>> selectMapList(@Param(Params.WRAPPER) AbstractWrapper<T> selectWrapper);

    /**
     * query total count
     *
     * @param selectWrapper select condition
     * @return total rows
     */
    long selectCount(@Param(Params.WRAPPER) AbstractWrapper<T> selectWrapper);

    /**
     * query one row
     *
     * @param selectWrapper select condition
     * @return row object
     */
    T selectOne(@Param(Params.WRAPPER) AbstractWrapper<T> selectWrapper);

    /**
     * query single value
     *
     * @param selectWrapper select condition
     * @param <R>           type of return object
     * @return single value
     */
    <R> R selectSingleValue(@Param(Params.WRAPPER) AbstractWrapper<T> selectWrapper);

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
