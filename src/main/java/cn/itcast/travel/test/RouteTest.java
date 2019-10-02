package cn.itcast.travel.test;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImp;
import org.junit.Test;

/**
 * @author XiaokanG
 * @version 1.0.1
 * @function 路线dao的测试方法
 * @data 2019/4/11 18:52
 */
public class RouteTest {

    RouteDao routeDao = new RouteDaoImp();

    @Test
    public void testFindTotalCount(){
       int cid = 5;
       String rname = "西安";
       System.out.println(routeDao.findTotalCount(cid, rname));
    }

    @Test
    public void testFindOnePage(){
        System.out.println(routeDao.findOnePage(5, 0, 5, "西安"));
    }
}
