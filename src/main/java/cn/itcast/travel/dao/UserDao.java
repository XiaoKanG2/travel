package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

/**
 * @author XiaokanG
 * @version 1.0.1
 * @function 用户dao的接口
 * @data 2019/4/10 10:37
 */
public interface UserDao {
    /**
     * 通过用户名查找用户
     * @param username
     * @return
     */
    User findUserByUsername(String username);

    /**
     * 保存一个用户
     * @param user
     */
    void save(User user);

    /**
     * 通过激活码查找用户
     * @param code
     * @return
     */
    User findUserByCode(String code);

    /**
     * 更改用户信息
     * @param user
     */
    void updataUser(User user);

    /**
     * 通过用户名和密码查找用户
     * @return
     */
    User findUserByUsernameAndPassword(String username,String password);
}
