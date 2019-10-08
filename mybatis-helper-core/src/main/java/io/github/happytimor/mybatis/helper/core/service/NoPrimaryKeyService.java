package io.github.happytimor.mybatis.helper.core.service;

import io.github.happytimor.mybatis.helper.core.mapper.NoPrimaryKeyMapper;
import io.github.happytimor.mybatis.helper.core.metadata.Page;
import io.github.happytimor.mybatis.helper.core.wrapper.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 单表无主键service, 适用于单表，无主键的数据库表映射
 *
 * @author chenpeng
 */
public class NoPrimaryKeyService<M extends NoPrimaryKeyMapper<T>, T> {
    @Autowired(required = false)
    private M noPrimaryKeyMapper;

    private static final int BATCH_SIZE = 5000;

    /**
     * 数据插入
     *
     * @param entity 对象
     */
    public void insert(T entity) {
        this.noPrimaryKeyMapper.insert(entity);
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
            this.noPrimaryKeyMapper.batchInsert(list);
            return;
        }

        List<T> temp = new ArrayList<>();
        for (T t : list) {
            temp.add(t);
            if (temp.size() == batchSize) {
                this.noPrimaryKeyMapper.batchInsert(temp);
                temp.clear();
            }
        }

        if (!temp.isEmpty()) {
            this.noPrimaryKeyMapper.batchInsert(temp);
        }
    }

    /**
     * 列表查询
     *
     * @param selectWrapper 条件组合
     * @return 返回结果
     */
    public List<T> selectList(AbstractWrapper<T> selectWrapper) {
        if (selectWrapper == null) {
            selectWrapper = new SelectWrapper<>();
        }
        return this.noPrimaryKeyMapper.selectList(selectWrapper);
    }

    /**
     * 分页查询
     *
     * @param page          分页对象
     * @param selectWrapper 请求
     * @return 分页结果
     */
    public Page<T> selectPage(Page<T> page, AbstractWrapper<T> selectWrapper) {
        if (selectWrapper == null) {
            selectWrapper = new SelectWrapper<>();
        }
        long total = this.selectCount(selectWrapper);
        page.setTotal(total);
        if (total <= 0) {
            page.setRecords(new ArrayList<>());
            return page;
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
        if (selectWrapper == null) {
            selectWrapper = new SelectWrapper<>();
        }
        return this.noPrimaryKeyMapper.selectCount(selectWrapper);
    }

    /**
     * 最多返回一条
     *
     * @param selectWrapper 条件组合
     * @return 返回结果
     */
    public T selectOne(AbstractWrapper<T> selectWrapper) {
        if (selectWrapper == null) {
            selectWrapper = new SelectWrapper<>();
        }
        return this.noPrimaryKeyMapper.selectOne(selectWrapper);
    }

    /**
     * 数据更新
     *
     * @param updateWrapper 条件组合
     * @return 更新条数
     */
    public int update(AbstractWrapper<T> updateWrapper) {
        if (updateWrapper == null) {
            throw new RuntimeException("updateWrapper can not be null");
        }
        return this.noPrimaryKeyMapper.update(updateWrapper);
    }

    /**
     * 数据删除
     *
     * @param deleteWrapper 条件组合
     * @return 删除条数
     */
    public int delete(AbstractWrapper<T> deleteWrapper) {
        if (deleteWrapper == null) {
            throw new RuntimeException("deleteWrapper can not be null");
        }
        return this.noPrimaryKeyMapper.delete(deleteWrapper);
    }

    /**
     * 有唯一索引的前提下插入或更新数据
     * 主要依靠 duplicate key update 来实现
     *
     * @param entity 对象
     * @return 操作是否成功
     */
    public boolean insertOrUpdateWithUniqueIndex(T entity) {
        return this.noPrimaryKeyMapper.insertOrUpdateWithUniqueIndex(entity);
    }
}
