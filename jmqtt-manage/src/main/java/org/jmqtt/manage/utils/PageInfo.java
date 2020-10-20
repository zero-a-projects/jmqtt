package org.jmqtt.manage.utils;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class PageInfo<T> implements Serializable {
    /**
     * 总数
     */
    private int total = 0;

    /**
     * 总页数
     */
    private int totalPage = 0;

    /**
     * 当前是第几页
     */
    private int curPageNo = 0;

    /**
     * 每页的大小
     */
    private int pageSize = 10;

    /**
     * 下一页
     */
    private int nextPage = 0;

    /**
     * 返回数据
     */
    private List<T> data = null;

    /**
     * 是否存在下一页
     */
    private boolean hasNextPage;

    public PageInfo() {
    }

    public PageInfo(Integer pageNum, Integer pageSize, List<T> list) {
        this.curPageNo = null == pageNum ? this.curPageNo : pageNum;
        this.pageSize = null == pageSize ? this.pageSize : pageSize;
        if (null != list && !list.isEmpty()) {
            this.total = list.size();
            this.hasNextPage = list.size() > pageSize * pageNum;
            this.totalPage = list.size() % pageSize == 0 ? list.size() / pageSize : (list.size() / pageSize) + 1;
            this.nextPage = pageNum < this.totalPage ? pageNum + 1 : 1;
            data = list.stream().skip((pageNum - 1) * pageSize)
                    .limit(pageSize).collect(Collectors.toList());
        }
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurPageNo() {
        return curPageNo;
    }

    public void setCurPageNo(int curPageNo) {
        this.curPageNo = curPageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }
}
