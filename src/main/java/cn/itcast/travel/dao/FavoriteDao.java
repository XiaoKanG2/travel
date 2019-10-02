package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;

import java.util.List;

/**
 * @author XiaokanG
 * @version 1.0.1
 * @function 操作收藏表的dao接口
 * @data 2019/4/12 17:09
 */
public interface FavoriteDao {


    /**
     * 查找是否有uid 和rid 的数据 判断是否有收藏
     * @param parseInt
     * @param uid
     * @return
     */
    Favorite isFavorite(int parseInt, int uid);

    /**
     * 通过rid 查找收藏的次数
     * @param rid
     * @return
     */
    int findCountByrid(int rid);

    /**
     * 添加一个收藏
     * @param uid
     * @param parseInt
     * @return
     */
    Boolean add(int uid, int parseInt);

    /**
     * 通过uid查找数量
     * @param uid
     * @return
     */
    int findCountByUid(int uid);

    /**
     * 通过uid查找一页的数据
     * @param uid
     * @param start
     * @param pageSize
     * @return
     */
    List<Route> findPageByUid(int uid, int start, int pageSize);
}
