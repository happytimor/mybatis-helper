package io.github.happytimor.mybatis.helper.core.mapper;

import io.github.happytimor.mybatis.helper.core.common.Params;
import io.github.happytimor.mybatis.helper.core.wrapper.AbstractWrapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 无主键mapper, 适用于单表，无主键的数据库表映射
 *
 * @author chenpeng
 * @date 2019-08-21
 */
public interface NoPrimaryKeyMapper<T> {

    /**
     * 数据插入
     *
     * @param entity 对象
     */
    void insert(@Param(Params.ENTITY) T entity);

    /**
     * 批量插入
     *
     * @param list 对象列表
     */
    void batchInsert(@Param(Params.LIST) Collection<T> list);

    /**
     * 列表查询
     *
     * @param selectWrapper 条件组合
     * @return 返回结果
     */
    List<T> selectList(@Param(Params.WRAPPER) AbstractWrapper<T> selectWrapper);

    /**
     * 查询总数
     *
     * @param selectWrapper 查询条件
     * @return 数据总数
     */
    long selectCount(@Param(Params.WRAPPER) AbstractWrapper<T> selectWrapper);

    /**
     * 最多返回一条
     *
     * @param selectWrapper 条件组合
     * @return 返回结果
     */
    T selectOne(@Param(Params.WRAPPER) AbstractWrapper<T> selectWrapper);

    /**
     * 数据更新
     *
     * @param updateWrapper 条件组合
     * @return 更新条数
     */
    int update(@Param(Params.WRAPPER) AbstractWrapper<T> updateWrapper);

    /**
     * 数据删除
     *
     * @param deleteWrapper 条件组合
     * @return 删除条数
     */
    int delete(@Param(Params.WRAPPER) AbstractWrapper<T> deleteWrapper);

    /**
     * 有唯一索引的前提下插入或更新数据
     * 主要依靠 duplicate key update 来实现
     *
     * @param entity 对象
     * @return 操作是否成功
     */
    boolean insertOrUpdateWithUniqueIndex(@Param(Params.ENTITY) T entity);
}
