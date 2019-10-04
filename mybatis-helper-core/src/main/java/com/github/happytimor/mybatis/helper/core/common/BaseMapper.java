package com.github.happytimor.mybatis.helper.core.common;

import com.github.happytimor.mybatis.helper.core.wrapper.AbstractWrapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 基础mapper, 适用于单表，有主键的普通数据库表映射
 *
 * @author chenpeng
 * @date 2019-08-21
 */
public interface BaseMapper<T> {

    /**
     * 数据插入
     *
     * @param entity 对象
     */
    void insert(T entity);

    /**
     * 批量插入
     *
     * @param list 对象列表
     */
    void batchInsert(Collection<T> list);

    /**
     * 根据主键删除
     *
     * @param id 主键id
     * @return 删除结果
     */
    boolean deleteById(Number id);

    /**
     * 根据主键删除
     *
     * @param idList 主键列表
     * @return 删除结果
     */
    int deleteByIdList(@Param("idList") Collection<? extends Number> idList);

    /**
     * 根据主键更新
     *
     * @param entity 对象
     * @return 更新结果
     */
    boolean updateById(T entity);

    /**
     * 根据主键批量更新
     *
     * @param list 对象列表
     * @return 更新结果
     */
    boolean batchUpdateById(Collection<T> list);

    /**
     * 根据主键查询
     *
     * @param id 主键id
     * @return 返回对象
     */
    T selectById(Number id);

    /**
     * 根据主键列表查询
     *
     * @param idList 主键列表
     * @return 返回对象列表
     */
    List<T> selectByIdList(@Param("idList") Collection<? extends Number> idList);

    /**
     * 列表查询
     *
     * @param selectWrapper 条件组合
     * @return 返回结果
     */
    List<T> selectList(AbstractWrapper<T> selectWrapper);

    /**
     * 查询总数
     *
     * @param selectWrapper 查询条件
     * @return 数据总数
     */
    long selectCount(AbstractWrapper<T> selectWrapper);

    /**
     * 最多返回一条
     *
     * @param selectWrapper 条件组合
     * @return 返回结果
     */
    T selectOne(AbstractWrapper<T> selectWrapper);

    /**
     * 数据更新
     *
     * @param updateWrapper 条件组合
     * @return 更新条数
     */
    int update(AbstractWrapper<T> updateWrapper);

    /**
     * 数据删除
     *
     * @param deleteWrapper 条件组合
     * @return 删除条数
     */
    int delete(AbstractWrapper<T> deleteWrapper);

    /**
     * 有唯一索引的前提下插入或更新数据
     * 主要依靠 duplicate key update 来实现
     *
     * @param entity 对象
     * @return 操作是否成功
     */
    boolean insertOrUpdateWithUniqueIndex(T entity);
}
