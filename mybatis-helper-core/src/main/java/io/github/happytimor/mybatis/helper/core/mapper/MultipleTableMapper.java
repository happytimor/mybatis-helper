package io.github.happytimor.mybatis.helper.core.mapper;

import io.github.happytimor.mybatis.helper.core.common.Params;
import io.github.happytimor.mybatis.helper.core.wrapper.AbstractWrapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 分表mapper, 适用于多表，有主键的普通数据库表映射
 *
 * @author chenpeng
 */
public interface MultipleTableMapper<T> {

    /**
     * 数据插入
     *
     * @param tableNum 表号
     * @param entity   对象
     */
    void insert(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.ENTITY) T entity);

    /**
     * 批量插入
     *
     * @param tableNum 表号
     * @param list     对象列表
     */
    void batchInsert(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.LIST) Collection<T> list);

    /**
     * 根据主键删除
     *
     * @param tableNum 表号
     * @param id       主键id
     * @return 删除结果
     */
    boolean deleteById(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.ID) Number id);

    /**
     * 根据主键删除
     *
     * @param tableNum 表号
     * @param idList   主键列表
     * @return 删除结果
     */
    int deleteByIdList(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.ID_LIST) Collection<? extends Number> idList);

    /**
     * 根据主键更新
     *
     * @param tableNum 表号
     * @param entity   对象
     * @return 更新结果
     */
    boolean updateById(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.ENTITY) T entity);

    /**
     * 根据主键批量更新
     *
     * @param tableNum 表号
     * @param list     对象列表
     * @return 更新结果
     */
    boolean batchUpdateById(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.LIST) Collection<T> list);

    /**
     * 根据主键查询
     *
     * @param tableNum 表号
     * @param id       主键id
     * @return 返回对象
     */
    T selectById(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.ID) Number id);

    /**
     * 根据主键列表查询
     *
     * @param tableNum 表号
     * @param idList   主键列表
     * @return 返回对象列表
     */
    List<T> selectByIdList(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.ID_LIST) Collection<? extends Number> idList);

    /**
     * 列表查询
     *
     * @param tableNum      表号
     * @param selectWrapper 条件组合
     * @return 返回结果
     */
    List<T> selectList(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.WRAPPER) AbstractWrapper<T> selectWrapper);

    /**
     * 查询某个对象
     *
     * @param selectWrapper 条件组合
     * @return 返回对象
     */
    Map<String, Object> selectMap(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.WRAPPER) AbstractWrapper<T> selectWrapper);

    /**
     * 查询某个对象列表
     *
     * @param selectWrapper 条件组合
     * @return 对象列表
     */
    List<Map<String, Object>> selectMapList(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.WRAPPER) AbstractWrapper<T> selectWrapper);

    /**
     * 查询总数
     *
     * @param tableNum      表号
     * @param selectWrapper 查询条件
     * @return 数据总数
     */
    long selectCount(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.WRAPPER) AbstractWrapper<T> selectWrapper);

    /**
     * 最多返回一条
     *
     * @param tableNum      表号
     * @param selectWrapper 条件组合
     * @return 返回结果
     */
    T selectOne(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.WRAPPER) AbstractWrapper<T> selectWrapper);

    /**
     * 数据更新
     *
     * @param tableNum      表号
     * @param updateWrapper 条件组合
     * @return 更新条数
     */
    int update(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.WRAPPER) AbstractWrapper<T> updateWrapper);

    /**
     * 数据删除
     *
     * @param tableNum      表号
     * @param deleteWrapper 条件组合
     * @return 删除条数
     */
    int delete(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.WRAPPER) AbstractWrapper<T> deleteWrapper);

    /**
     * 有唯一索引的前提下插入或更新数据
     * 主要依靠 duplicate key update 来实现
     *
     * @param tableNum 表号
     * @param entity   对象
     * @return 操作是否成功
     */
    boolean insertOrUpdateWithUniqueIndex(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.ENTITY) T entity);

    /**
     * 单值查询
     *
     * @param tableNum      表号
     * @param selectWrapper selectWrapper
     * @param <R>           返回类型
     * @return 返回数据
     */
    <R> R selectSingleValue(@Param(Params.TABLE_NUM) String tableNum, @Param(Params.WRAPPER) AbstractWrapper<T> selectWrapper);
}
