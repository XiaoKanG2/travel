package cn.itcast.travel.test;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImp;
import cn.itcast.travel.domain.User;
import org.junit.Test;

/**
 * @author XiaokanG
 * @version 1.0.1
 * @function
 * @data 2019/4/10 11:30
 */
public class UserDaoTest {
    UserDao userDao = new UserDaoImp();

    @Test
    public void  testFindByUsername(){
        User userByUsername = userDao.findUserByUsername("xiaokang");
        System.out.println(userByUsername);
    }
}
