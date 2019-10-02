package cn.itcast.travel.test;

import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.impl.RouteImgDaoImp;
import org.junit.Test;

/**
 * @author XiaokanG
 * @version 1.0.1
 * @function 操作routeImg的测试类
 * @data 2019/4/12 14:47
 */
public class RouteImgTest {
    RouteImgDao routeImgDao = new RouteImgDaoImp();

    @Test
    public void testFindByRid(){
        System.out.println(routeImgDao.findByRid(1));
    }
}
