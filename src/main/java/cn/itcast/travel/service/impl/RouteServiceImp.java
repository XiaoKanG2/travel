package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.*;
import cn.itcast.travel.dao.impl.*;
import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.RouteService;

import java.util.List;

/**
 * @author XiaokanG
 * @version 1.0.1
 * @function 路线服务接口的实现类
 * @data 2019/4/11 12:44
 */
public class RouteServiceImp implements RouteService {

    private RouteDao routeDao = new RouteDaoImp();
    private SellerDao sellerDao = new SellerDaoImp();
    private RouteImgDao routeImgDao = new RouteImgDaoImp();
    private CategoryDao categoryDao = new CategoryDaoImp();
    private FavoriteDao favoriteDao = new FavoriteDaoImp();

    /**
     * 查找一页的数据
     * @param cid
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageBean<Route> findPage(int cid, int currentPage, int pageSize,String rname) {
        PageBean<Route> routes = new PageBean<Route>();
        //设置当前的页数
        routes.setCurrentPage(currentPage);
        //设置每页的个数
        routes.setPageSize(pageSize);

        //设置该类列表的所有的个数
        int totalCount = routeDao.findTotalCount(cid,rname);
        routes.setTotalCount(totalCount);

        //设置总有多少页
        int totalPage = totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize) + 1;
        routes.setTotalPage(totalPage);

        //设置数据
        int start = (currentPage-1)*pageSize;//开始的页码
        List<Route> list = routeDao.findOnePage(cid,start,pageSize,rname);
        routes.setList(list);
        return routes;
    }

    /**
     * 查询一条路线的详细信息
     * @param rid
     * @return
     */
    @Override
    public Route oneDetailPage(int rid) {

        //查找到了基本信息
        Route route = routeDao.findOneroute(rid);

        //查找商家信息
        int sid = route.getSid();
        Seller seller = sellerDao.findByid(sid);
        route.setSeller(seller);

        //查找图片信息
        List<RouteImg> routeImgList = routeImgDao.findByRid(rid);
        route.setRouteImgList(routeImgList);

        //查找分类信息
        Category category = categoryDao.findByid(route.getCid());
        route.setCategory(category);

        //查找收藏的次数
        int count = favoriteDao.findCountByrid(rid);
        route.setCount(count);
        return route;
    }


}
