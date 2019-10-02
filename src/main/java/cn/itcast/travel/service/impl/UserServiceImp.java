package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImp;
import cn.itcast.travel.dao.impl.UserDaoImp;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

import java.util.List;
import java.util.Map;

/**
 * @author XiaokanG
 * @version 1.0.1
 * @function 用户的服务实现类
 * @data 2019/4/10 10:36
 */
public class UserServiceImp implements UserService {

    private UserDao userDao = new UserDaoImp();
    private FavoriteDao favoriteDao = new FavoriteDaoImp();
    /**
     * 用户注册
     * @param user
     * @return
     */
    @Override
    public Boolean regist(User user) {
        String username = user.getUsername();
        User u = userDao.findUserByUsername(username);
        //没有找到则需要保存
        if (u==null){
            //保存前要加入激活码和激活状态

            //1.给用户设置激活状态
            user.setStatus("N");
            //给用户设置激活码
            user.setCode(UuidUtil.getUuid());

            userDao.save(user);

            //给用户发送邮件
            String content = "<a href='http://127.0.0.1:8080/travel/user/active?code="+user.getCode()+"'>点击激活旅游网</a>";
            MailUtils.sendMail(user.getEmail(),content,"旅游网激活邮件");
            return true;
        }
        return false;
    }

    /**
     * 用户激活
     * @param code
     * @return
     */
    @Override
    public Boolean active(String code) {
        //通过该code是否可以找到用户
        User user = userDao.findUserByCode(code);
        if (user!=null) {
            //查找到用户，修改他的激活状态
            userDao.updataUser(user);
            return true;
        }
        return false;
    }

    @Override
    public User login(User user) {
        //通过用户的用户名和密码来查找
        User u = userDao.findUserByUsernameAndPassword(user.getUsername(),user.getPassword());
        return u;
    }

    /**
     * 获取当前登录用户的收藏
     * @param uid
     * @param currentPageStr
     * @param pageSizeStr
     * @return
     */
    @Override
    public PageBean<Route> myFavorite(int uid, String currentPageStr, String pageSizeStr) {

        PageBean<Route> route = new PageBean<>();

        //设置总条数
        int totalCount = favoriteDao.findCountByUid(uid);
        route.setTotalCount(totalCount);

        //设置每页的个数
        int pageSize;
        if ("all".equals(pageSizeStr)){
            pageSize = totalCount;
        }else {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        route.setPageSize(pageSize);

        //设置当前的页数
        int currentPage;
        if (currentPageStr!=null&&!"null".equals(currentPageStr)){
            currentPage = Integer.parseInt(currentPageStr);
        }else {
            currentPage = 1;
        }
        route.setCurrentPage(currentPage);

        //设置总页数
        int totalPage = totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize)+1;
        route.setTotalPage(totalPage);

        //设置数据
        int start = (currentPage-1)*pageSize;
        List<Route> routeList = favoriteDao.findPageByUid(uid,start,pageSize);
        route.setList(routeList);

        return route;
    }
}
