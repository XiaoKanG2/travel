package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XiaokanG
 * @version 1.0.1
 * @function 路线数据库操作的有个实现类
 * @data 2019/4/11 12:46
 */
public class RouteDaoImp  implements RouteDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * 查找该分类的总数 带关键字查找
     * @param cid
     * @return
     */
    @Override
    public int findTotalCount(int cid,String rname) {

        //select count(*) from tab_route where cid = 5 and rname like '%西安%';

        List list = new ArrayList(); //用来接收参数的值
        String sql = "select count(*) from tab_route where 1 = 1 ";
        StringBuffer sb = new StringBuffer(sql);
        if (cid!=0){
            sb.append(" and cid = ?");
            list.add(cid);
        }
        if (rname!=null){
            sb.append(" and rname like ? ");
            list.add("%"+rname+"%");
        }
        Integer totalCount = jdbcTemplate.queryForObject(sb.toString(), Integer.class,list.toArray());
        return totalCount;
    }

    /**
     * 查找指定的一页的数据
     * @param cid
     * @param start
     * @param pageSize
     * @return
     */
    @Override
    public List<Route> findOnePage(int cid, int start, int pageSize,String rname) {
        //select * from tab_route where 1 =1 and cid =5 and rname like '%西安%' limit 0,5;
        String sql = "select * from tab_route where 1 = 1";
        StringBuffer sb = new StringBuffer(sql);
        List list = new ArrayList(); //参数列表
        if (cid!=0){
            sb.append(" and cid = ? ");
            list.add(cid);
        }

        if (rname!=null){
            sb.append(" and rname like ? ");
            list.add("%"+ rname +"%");
        }
        sb.append(" limit ?,?");
        list.add(start);
        list.add(pageSize);
        List<Route> routeList = jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<Route>(Route.class),list.toArray());
        return routeList;
    }

    /**
     * 通过rid查找一条路线的信息
     * @param rid
     * @return
     */
    @Override
    public Route findOneroute(int rid) {
        String sql = "select * from tab_route where rid = ?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);

    }
}
