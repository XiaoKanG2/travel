package cn.itcast.travel.domain;

import java.util.List;

/**
 * @author XiaokanG
 * @version 1.0.1
 * @function 返回分类的一页对象
 * @data 2019/4/11 12:34
 */
public class PageBean<T> {

    private int totalPage;//该类的页数
    private int totalCount;//该类的总数
    private int currentPage;//当前的页数
    private List<T> list;//返回的数据
    private int pageSize;//每条的页数

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
