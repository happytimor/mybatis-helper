package io.github.happytimor.mybatis.helper.core.service;

import io.github.happytimor.mybatis.helper.core.mapper.MultipleTableMapper;
import io.github.happytimor.mybatis.helper.core.metadata.Page;
import io.github.happytimor.mybatis.helper.core.wrapper.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 分表service, 适用于多表，有主键的普通数据库表映射
 *
 * @author chenpeng
 */
public class MultipleTableService<M extends MultipleTableMapper<T>, T> {
    @Autowired(required = false)
    private M multipleTableMapper;

    private static final int BATCH_SIZE = 5000;

    /**
     * 数据插入
     *
     * @param tableNum 表号
     * @param entity   对象
     */
    public void insert(String tableNum, T entity) {
        this.multipleTableMapper.insert(tableNum, entity);
    }

    /**
     * 批量插入
     *
     * @param tableNum 表号
     * @param list     对象列表
     */
    public void batchInsert(String tableNum, List<T> list) {
        this.batchInsert(tableNum, list, BATCH_SIZE);
    }

    /**
     * 批量插入
     *
     * @param tableNum  表号
     * @param list      对象列表
     * @param batchSize 分页大小
     */
    public void batchInsert(String tableNum, List<T> list, int batchSize) {
        if (list == null || list.size() == 0) {
            return;
        }
        if (list.size() <= batchSize) {
            this.multipleTableMapper.batchInsert(tableNum, list);
            return;
        }

        List<T> temp = new ArrayList<>();
        for (T t : list) {
            temp.add(t);
            if (temp.size() == batchSize) {
                this.multipleTableMapper.batchInsert(tableNum, temp);
                temp.clear();
            }
        }

        if (!temp.isEmpty()) {
            this.multipleTableMapper.batchInsert(tableNum, temp);
        }
    }

    /**
     * 根据主键删除
     *
     * @param tableNum 表号
     * @param id       主键id
     * @return 删除结果
     */
    public boolean deleteById(String tableNum, Number id) {
        return this.multipleTableMapper.deleteById(tableNum, id);
    }

    /**
     * 根据主键删除
     *
     * @param tableNum 表号
     * @param idList   主键列表
     * @return 删除结果
     */
    public int deleteByIdList(String tableNum, Collection<? extends Number> idList) {
        return this.multipleTableMapper.deleteByIdList(tableNum, idList);
    }

    /**
     * 根据主键更新
     *
     * @param tableNum 表号
     * @param entity   对象
     * @return 更新结果
     */
    public boolean updateById(String tableNum, T entity) {
        return this.multipleTableMapper.updateById(tableNum, entity);
    }

    /**
     * 根据主键批量更新
     *
     * @param tableNum 表号
     * @param list     对象列表
     * @return 更新结果
     */
    public boolean batchUpdateById(String tableNum, Collection<T> list) {
        return this.multipleTableMapper.batchUpdateById(tableNum, list);
    }

    /**
     * 根据主键查询
     *
     * @param tableNum 表号
     * @param id       主键id
     * @return 返回对象
     */
    public T selectById(String tableNum, Number id) {
        return this.multipleTableMapper.selectById(tableNum, id);
    }

    /**
     * 根据主键列表查询
     *
     * @param tableNum 表号
     * @param idList   主键列表
     * @return 返回对象列表
     */
    public List<T> selectByIdList(String tableNum, Collection<? extends Number> idList) {
        return this.multipleTableMapper.selectByIdList(tableNum, idList);
    }

    /**
     * 列表查询
     *
     * @param tableNum      表号
     * @param selectWrapper 条件组合
     * @return 返回结果
     */
    public List<T> selectList(String tableNum, AbstractWrapper<T> selectWrapper) {
        if (selectWrapper == null) {
            selectWrapper = new SelectWrapper<>();
        }
        return this.multipleTableMapper.selectList(tableNum, selectWrapper);
    }

    /**
     * map查询
     *
     * @param selectWrapper 条件组合
     * @return 返回map
     */
    public Map<String, Object> selectMap(String tableNum, AbstractWrapper<T> selectWrapper) {
        if (selectWrapper == null) {
            selectWrapper = new SelectWrapper<>();
        }
        return this.multipleTableMapper.selectMap(tableNum, selectWrapper);
    }

    /**
     * map查询
     *
     * @param selectWrapper 条件组合
     * @return 返回map
     */
    public List<Map<String, Object>> selectMapList(String batchNum, AbstractWrapper<T> selectWrapper) {
        if (selectWrapper == null) {
            selectWrapper = new SelectWrapper<>();
        }
        return this.multipleTableMapper.selectMapList(batchNum, selectWrapper);
    }

    /**
     * 分页查询
     *
     * @param tableNum      表号
     * @param pageNo        页码
     * @param pageSize      页面大小
     * @param selectWrapper 请求
     * @return 分页结果
     */
    public Page<T> selectPage(String tableNum, int pageNo, int pageSize, AbstractWrapper<T> selectWrapper) {
        long total = this.selectCount(tableNum, selectWrapper);
        Page<T> page = new Page<>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotal(total);
        if (total <= 0) {
            page.setRecords(new ArrayList<>());
            return page;
        }
        if (selectWrapper == null) {
            selectWrapper = new SelectWrapper<>();
        }
        if (selectWrapper instanceof OrderWrapper) {
            ((OrderWrapper) selectWrapper).limit(page.getStartRow(), pageSize);
        }
        List<T> reocrds = this.selectList(tableNum, selectWrapper);
        page.setRecords(reocrds);
        return page;
    }

    /**
     * 查询总数
     *
     * @param tableNum      表号
     * @param selectWrapper 查询条件
     * @return 数据总数
     */
    public long selectCount(String tableNum, AbstractWrapper<T> selectWrapper) {
        if (selectWrapper == null) {
            selectWrapper = new SelectWrapper<>();
        }
        return this.multipleTableMapper.selectCount(tableNum, selectWrapper);
    }

    /**
     * 最多返回一条
     *
     * @param tableNum      表号
     * @param selectWrapper 条件组合
     * @return 返回结果
     */
    public T selectOne(String tableNum, AbstractWrapper<T> selectWrapper) {
        if (selectWrapper == null) {
            selectWrapper = new SelectWrapper<>();
        }
        return this.multipleTableMapper.selectOne(tableNum, selectWrapper);
    }

    /**
     * 数据更新
     *
     * @param tableNum      表号
     * @param updateWrapper 条件组合
     * @return 更新条数
     */
    public int update(String tableNum, AbstractWrapper<T> updateWrapper) {
        if (updateWrapper == null) {
            throw new RuntimeException("updateWrapper can not be null");
        }
        return this.multipleTableMapper.update(tableNum, updateWrapper);
    }

    /**
     * 数据删除
     *
     * @param tableNum      表号
     * @param deleteWrapper 条件组合
     * @return 删除条数
     */
    public int delete(String tableNum, AbstractWrapper<T> deleteWrapper) {
        if (deleteWrapper == null) {
            throw new RuntimeException("deleteWrapper can not be null");
        }
        return this.multipleTableMapper.delete(tableNum, deleteWrapper);
    }

    /**
     * 有唯一索引的前提下插入或更新数据
     * 主要依靠 duplicate key update 来实现
     *
     * @param tableNum 表号
     * @param entity   对象
     * @return 操作是否成功
     */
    public boolean insertOrUpdateWithUniqueIndex(String tableNum, T entity) {
        return this.multipleTableMapper.insertOrUpdateWithUniqueIndex(tableNum, entity);
    }
}
