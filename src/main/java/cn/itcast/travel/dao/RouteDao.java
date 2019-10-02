package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

/**
 * @author XiaokanG
 * @version 1.0.1
 * @function 路线的数据库相关的接口
 * @data 2019/4/11 12:45
 */
public interface RouteDao {
    /**
     * 查找该分类的总数
     * @param cid
     * @return
     */
    int findTotalCount(int cid, String rname);

    /**
     * 查找指定的一页的数据
     * @param cid
     * @param start
     * @param pageSize
     * @return
     */
    List<Route> findOnePage(int cid, int start, int pageSize, String rname);

    /**
     * 通过rid查找一条线路
     * @param rid
     * @return
     */
    Route findOneroute(int rid);
}
