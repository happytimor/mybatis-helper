package com.happytimor.mybatis.helper.core.service;

import com.happytimor.mybatis.helper.core.common.BaseMapper;
import com.happytimor.mybatis.helper.core.metadata.Page;
import com.happytimor.mybatis.helper.core.wrapper.AbstractWrapper;
import com.happytimor.mybatis.helper.core.wrapper.OrderWrapper;
import com.happytimor.mybatis.helper.core.wrapper.SelectWrapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author chenpeng
 * @date 2019-09-07
 */
public class BaseService<M extends BaseMapper<T>, T> {
    @Autowired(required = false)
    private M baseMapper;

    private static final int BATCH_SIZE = 5000;

    /**
     * 数据插入
     *
     * @param entity 对象
     */
    public void insert(T entity) {
        this.baseMapper.insert(entity);
    }

    /**
     * 批量插入
     *
     * @param list 对象列表
     */
    public void batchInsert(List<T> list) {
        this.batchInsert(list, BATCH_SIZE);
    }

    /**
     * 批量插入
     *
     * @param list      对象列表
     * @param batchSize 分页大小
     */
    public void batchInsert(List<T> list, int batchSize) {
        if (list == null || list.size() == 0) {
            return;
        }
        if (list.size() <= batchSize) {
            this.baseMapper.batchInsert(list);
            return;
        }

        List<T> temp = new ArrayList<>();
        for (T t : list) {
            temp.add(t);
            if (temp.size() == batchSize) {
                this.baseMapper.batchInsert(temp);
                temp.clear();
            }
        }

        if (!temp.isEmpty()) {
            this.baseMapper.batchInsert(temp);
        }
    }

    /**
     * 根据主键删除
     *
     * @param id 主键id
     * @return 删除结果
     */
    public boolean deleteById(Number id) {
        return this.baseMapper.deleteById(id);
    }

    /**
     * 根据主键删除
     *
     * @param idList 主键列表
     * @return 删除结果
     */
    public int deleteByIdList(@Param("idList") Collection<? extends Number> idList) {
        return this.baseMapper.deleteByIdList(idList);
    }

    /**
     * 根据主键更新
     *
     * @param entity 对象
     * @return 更新结果
     */
    public boolean updateById(T entity) {
        return this.baseMapper.updateById(entity);
    }

    /**
     * 根据主键批量更新
     *
     * @param list 对象列表
     * @return 更新结果
     */
    public boolean batchUpdateById(Collection<T> list) {
        return this.baseMapper.batchUpdateById(list);
    }

    /**
     * 根据主键查询
     *
     * @param id 主键id
     * @return 返回对象
     */
    public T selectById(Number id) {
        return this.baseMapper.selectById(id);
    }

    /**
     * 根据主键列表查询
     *
     * @param idList 主键列表
     * @return 返回对象列表
     */
    public List<T> selectByIdList(@Param("idList") Collection<? extends Number> idList) {
        return this.baseMapper.selectByIdList(idList);
    }

    /**
     * 列表查询
     *
     * @param selectWrapper 条件组合
     * @return 返回结果
     */
    public List<T> selectList(AbstractWrapper<T> selectWrapper) {
        return this.baseMapper.selectList(selectWrapper);
    }

    /**
     * 分页查询
     *
     * @param page          分页对象
     * @param selectWrapper 请求
     * @return 分页结果
     */
    public Page<T> selectPage(Page<T> page, AbstractWrapper<T> selectWrapper) {
        long total = this.selectCount(selectWrapper);
        page.setTotal(total);
        if (total <= 0) {
            page.setRecords(new ArrayList<>());
            return page;
        }
        if (selectWrapper == null) {
            selectWrapper = new SelectWrapper<>();
        }
        if (selectWrapper instanceof OrderWrapper) {
            ((OrderWrapper) selectWrapper).limit(page.getStartRow(), page.getPageSize());
        }
        List<T> reocrds = this.selectList(selectWrapper);
        page.setRecords(reocrds);
        return page;
    }

    /**
     * 查询总数
     *
     * @param selectWrapper 查询条件
     * @return 数据总数
     */
    public long selectCount(AbstractWrapper<T> selectWrapper) {
        return this.baseMapper.selectCount(selectWrapper);
    }

    /**
     * 最多返回一条
     *
     * @param selectWrapper 条件组合
     * @return 返回结果
     */
    public T selectOne(AbstractWrapper<T> selectWrapper) {
        return this.baseMapper.selectOne(selectWrapper);
    }

    /**
     * 数据更新
     *
     * @param updateWrapper 条件组合
     * @return 更新条数
     */
    public int update(AbstractWrapper<T> updateWrapper) {
        return this.baseMapper.update(updateWrapper);
    }

    /**
     * 数据删除
     *
     * @param deleteWrapper 条件组合
     * @return 删除条数
     */
    public int delete(AbstractWrapper<T> deleteWrapper) {
        return this.baseMapper.delete(deleteWrapper);
    }

    /**
     * 有唯一索引的前提下插入或更新数据
     * 主要依靠 duplicate key update 来实现
     *
     * @param entity 对象
     * @return 操作是否成功
     */
    public boolean insertOrUpdateWithUniqueIndex(T entity) {
        return this.baseMapper.insertOrUpdateWithUniqueIndex(entity);
    }
}
