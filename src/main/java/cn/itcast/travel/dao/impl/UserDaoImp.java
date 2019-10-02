package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author XiaokanG
 * @version 1.0.1
 * @function 用户dao的实现类
 * @data 2019/4/10 10:37
 */
public class UserDaoImp implements UserDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findUserByUsername(String username) {
        User user = null;
        //1.sql语句
        String sql = "select * from tab_user where username = ?";
        try {
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (Exception e) {
        }
        return user;
    }

    @Override
    public void save(User user) {
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";

        jdbcTemplate.update(sql,user.getUsername(),
                                user.getPassword(),
                                user.getName(),
                                user.getBirthday(),
                                user.getSex(),
                                user.getTelephone(),
                                user.getEmail(),
                                user.getStatus(),
                                user.getCode());
    }

    @Override
    public User findUserByCode(String code) {
        User user = null;
        String sql = "select * from tab_user where code = ?";
        try {
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),code);
        }catch (Exception e){
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public void updataUser(User user) {
        String sql = "update tab_user set status = 'Y' where uid = ?";
        jdbcTemplate.update(sql,user.getUid());
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        User user = null;
        String sql = "select * from tab_user where username = ? and password = ?";
        try {
            user = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),username,password);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
}
