package cn.itcast.travel.service;

/**
 * @author XiaokanG
 * @version 1.0.1
 * @function 收藏表的服务接口
 * @data 2019/4/12 17:08
 */
public interface FavoriteService {
    /**
     * 查询该条路线 rid 是否被当前用户 uid 收藏
     * @param rid
     * @param uid
     * @return
     */
    Boolean isFavorite(String rid, int uid);

    /**
     * 添加一个收藏
     * @param uid
     * @param rid
     * @return
     */
    Boolean add(int uid, String rid);
}
