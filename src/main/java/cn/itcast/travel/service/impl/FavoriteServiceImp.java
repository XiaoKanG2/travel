package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImp;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;

/**
 * @author XiaokanG
 * @version 1.0.1
 * @function 收藏服务的接口
 * @data 2019/4/12 17:09
 */
public class FavoriteServiceImp implements FavoriteService {

    FavoriteDao favoriteDao = new FavoriteDaoImp();

    @Override
    public Boolean isFavorite(String rid, int uid) {
        Favorite favorite = favoriteDao.isFavorite(Integer.parseInt(rid),uid);
        return favorite!=null;
    }

    /**
     * 添加一个收藏
     * @param uid
     * @param rid
     * @return
     */
    @Override
    public Boolean add(int uid, String rid) {
        return favoriteDao.add(uid,Integer.parseInt(rid));

    }
}
