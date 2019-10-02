package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

/**
 * @author XiaokanG
 * @version 1.0.1
 * @function 路线服务的接口
 * @data 2019/4/11 12:43
 */
public interface RouteService {
    /**
     * 查找一页的数据
     * @param cid
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageBean<Route> findPage(int cid,int currentPage,int pageSize,String rname);

    /**
     * 查询一条路线的详细信息
     * @param rid
     * @return
     */
    Route oneDetailPage(int rid);


}
