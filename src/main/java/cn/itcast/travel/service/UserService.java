package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;

import java.util.Map;

/**
 * @author XiaokanG
 * @version 1.0.1
 * @function 用户的服务接口
 * @data 2019/4/10 10:36
 */
public interface UserService {
    /**
     * 用户注册
     * @param user
     * @return
     */
    Boolean regist(User user);

    /**
     * 用户激活
     * @param code
     * @return
     */
    Boolean active(String code);

    /**
     * 用户登录
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 查找当前用户的收藏
     * @param uid
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageBean<Route> myFavorite(int uid, String currentPage, String pageSize);
}
