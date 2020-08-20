package com.sd.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.pagehelper.PageInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.Assert;

public class Page<T> implements Serializable {
    private static final long serialVersionUID = 5859907455479273251L;
    public static final int DEFAULT_PAGE_SIZE = 10;
    private int pageSize;
    private int start;
    private List<T> data;
    private long resultCount;

    public static Page nullPage(){
        return new Page(1,0,null);
    }

    public Page(int start, long totalSize, List<T> data) {
        this.pageSize = 10;
        this.data = new ArrayList();
        Assert.isTrue(start >= 0, "Start must not be negative!");
        Assert.isTrue(totalSize >= 0L, "Total size must not be negative!");
        this.start = start;
        this.resultCount = totalSize;
        this.data = data;
        if (this.data == null) {
            this.data = new ArrayList();
        }

    }

    public int getStart() {
        return this.start;
    }

    public Page(int start, long totalSize, int pageSize, List<T> data) {
        this(start, totalSize, data);
        Assert.isTrue(pageSize > 0, "Page size must be greater than 0!");
        this.pageSize = pageSize;
    }

    public Page(PageInfo pageInfo) {
        this(pageInfo.getStartRow() > 0 ? pageInfo.getStartRow() - 1 : 0, pageInfo.getTotal(), pageInfo.getPageSize(), pageInfo.getList());
    }

    public long getResultCount() {
        return this.resultCount;
    }

    public long getTotal() {
        return this.getResultCount();
    }

    public long getPageCount() {
        return this.resultCount % (long)this.pageSize == 0L ? this.resultCount / (long)this.pageSize : this.resultCount / (long)this.pageSize + 1L;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public int getRowCount() {
        return this.getPageSize();
    }

    public List<T> getData() {
        return this.data;
    }

    public int getPageIndex() {
        return this.start / this.pageSize;
    }

    public int getCurrent() {
        return this.getPageIndex() + 1;
    }

    public boolean hasNextPage() {
        return (long)this.getPageIndex() < this.getPageCount() - 1L;
    }

    public boolean hasPreviousPage() {
        return this.getPageIndex() > 0;
    }

    public static int getStartOfPage(int pageIndex, int pageSize) {
        return pageIndex * pageSize;
    }

    public Page() {
        this.pageSize = 10;
        this.data = new ArrayList();
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = (long)resultCount;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Page)) {
            return false;
        } else {
            Page<?> other = (Page)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getPageSize() != other.getPageSize()) {
                return false;
            } else if (this.getStart() != other.getStart()) {
                return false;
            } else {
                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data == null) {
                        return this.getResultCount() == other.getResultCount();
                    }
                } else if (this$data.equals(other$data)) {
                    return this.getResultCount() == other.getResultCount();
                }

                return false;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Page;
    }


    public String toString() {
        return "Page(pageSize=" + this.getPageSize() + ", start=" + this.getStart() + ", data=" + this.getData() + ", resultCount=" + this.getResultCount() + ")";
    }
}
