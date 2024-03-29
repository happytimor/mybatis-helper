package io.github.happytimor.mybatis.helper.core.metadata;

import java.util.List;

/**
 * paging object
 *
 * @author chenpeng
 */
public class Page<T> {
    /**
     * page no
     */
    private int pageNo;
    /**
     * page size
     */
    private int pageSize;
    /**
     * start row
     */
    private int startRow;
    /**
     * total count
     */
    private long total;
    /**
     * record list
     */
    private List<T> records;

    public Page() {
    }

    public Page(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.startRow = (pageNo - 1) * pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}
