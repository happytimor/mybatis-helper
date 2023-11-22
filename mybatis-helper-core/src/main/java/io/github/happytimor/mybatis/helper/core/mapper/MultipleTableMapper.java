package io.github.happytimor.mybatis.helper.core.mapper;

import io.github.happytimor.mybatis.helper.core.common.Params;
import io.github.happytimor.mybatis.helper.core.wrapper.AbstractWrapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * multiple mapper(like baseMapper but for multiple tables)
 *
 * @author chenpeng
 */
public interface MultipleTableMapper<T> {

    /**
     * insert one row
     *
     * @param tableNum table index
     * @param entity   insert object
     */
    void insert(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.ENTITY) T entity);

    /**
     * batch insert rows
     *
     * @param tableNum table index
     * @param list     insert object list
     */
    void batchInsert(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.LIST) Collection<T> list);

    /**
     * delete one row by primary key
     *
     * @param tableNum table index
     * @param id       primary key
     * @return true if operation success
     */
    boolean deleteById(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.ID) Number id);

    /**
     * delete rows by primary key list
     *
     * @param tableNum table index
     * @param idList   primary id list
     * @return delete count
     */
    int deleteByIdList(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.ID_LIST) Collection<? extends Number> idList);

    /**
     * update row by primary key
     *
     * @param tableNum table index
     * @param entity   update object
     * @return true if operation success
     */
    boolean updateById(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.ENTITY) T entity);

    /**
     * batch update rows by primary key
     *
     * @param tableNum table index
     * @param list     update object list
     * @return true if operation success
     */
    boolean batchUpdateById(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.LIST) Collection<T> list);

    /**
     * query one row by primary key
     *
     * @param tableNum table index
     * @param id       primary key
     * @return select object
     */
    T selectById(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.ID) Number id);

    /**
     * query rows by primary key list
     *
     * @param tableNum table index
     * @param idList   primary id list
     * @return return object list
     */
    List<T> selectByIdList(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.ID_LIST) Collection<? extends Number> idList);

    /**
     * query rows by customized condition
     *
     * @param tableNum      table index
     * @param selectWrapper select condition
     * @return object list
     */
    List<T> selectList(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.WRAPPER) AbstractWrapper<T> selectWrapper);

    /**
     * query map object
     *
     * @param selectWrapper select condition
     * @param tableNum      table index
     * @return map object
     */
    Map<String, Object> selectMap(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.WRAPPER) AbstractWrapper<T> selectWrapper);

    /**
     * query map object list
     *
     * @param selectWrapper condition wrapper
     * @param tableNum      table index
     * @return map list
     */
    List<Map<String, Object>> selectMapList(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.WRAPPER) AbstractWrapper<T> selectWrapper);

    /**
     * query total count
     *
     * @param tableNum      table index
     * @param selectWrapper select condition
     * @return total count
     */
    long selectCount(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.WRAPPER) AbstractWrapper<T> selectWrapper);

    /**
     * query one row
     *
     * @param tableNum      table index
     * @param selectWrapper select condition
     * @return row object
     */
    T selectOne(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.WRAPPER) AbstractWrapper<T> selectWrapper);

    /**
     * update row
     *
     * @param tableNum      table index
     * @param updateWrapper update condition
     * @return update count
     */
    int update(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.WRAPPER) AbstractWrapper<T> updateWrapper);

    /**
     * delete rows with customized condition
     *
     * @param tableNum      table index
     * @param deleteWrapper delete condition
     * @return delete count
     */
    int delete(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.WRAPPER) AbstractWrapper<T> deleteWrapper);

    /**
     * query single value
     *
     * @param tableNum      table index
     * @param selectWrapper selectWrapper
     * @param <R>           object value
     * @return query object
     */
    <R> R selectSingleValue(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.WRAPPER) AbstractWrapper<T> selectWrapper);
}
