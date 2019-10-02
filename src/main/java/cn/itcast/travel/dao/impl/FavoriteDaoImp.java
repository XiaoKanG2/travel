package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

/**
 * @author XiaokanG
 * @version 1.0.1
 * @function 操作收藏表的dao实现类
 * @data 2019/4/12 17:10
 */
public class FavoriteDaoImp implements FavoriteDao {

    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 查找是否有uid 和rid 的数据 判断是否有收藏
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public Favorite isFavorite(int rid, int uid) {
        String sql = "select * from tab_favorite where uid = ? and rid = ?";
        Favorite favorite = null;
        try {
            favorite = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), uid, rid);
        }catch (Exception e){
            //没有找到，不会赋值
        }
        return favorite;
    }

    /**
     * 通过rid 查找收藏数
     * @param rid
     * @return
     */
    @Override
    public int findCountByrid(int rid) {
        String sql = "select count(*) from tab_favorite where rid = ?";
        return jdbcTemplate.queryForObject(sql,Integer.class,rid);
    }

    /**
     * 添加一个收藏
     * @param uid
     * @param rid
     * @return
     */
    @Override
    public Boolean add(int uid, int rid) {
        Boolean flag = false;
        String sql = "insert into tab_favorite(uid,rid,date) values(?,?,?)";
        try {
            jdbcTemplate.update(sql, uid, rid, new Date());
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 通过uid查找数量
     * @param uid
     * @return
     */
    @Override
    public int findCountByUid(int uid) {
        String sql = "select count(*) from tab_favorite where uid = ?";
        return jdbcTemplate.queryForObject(sql,Integer.class,uid);

    }

    /**
     * 通过uid查找一页的数据
     * @param uid
     * @param start
     * @param pageSize
     * @return
     */
    //select tab_route.rid,rname,price,routeIntroduce,rflag,rdate,isThemeTour,count,cid,rimage,sid,sourceId from tab_route,tab_favorite WHERE tab_route.rid = tab_favorite.rid and tab_favorite.uid=13;
    @Override
    public List<Route> findPageByUid(int uid, int start, int pageSize) {
        String sql = "select tab_route.rid,rname,price,routeIntroduce,rflag,rdate,isThemeTour,count,cid,rimage,sid,sourceId from tab_route,tab_favorite WHERE tab_route.rid = tab_favorite.rid and tab_favorite.uid=? limit ?,?";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<Route>(Route.class),uid,start,pageSize);

    }
}
