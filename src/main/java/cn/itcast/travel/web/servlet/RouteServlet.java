package cn.itcast.travel.web.servlet;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImp;
import cn.itcast.travel.service.impl.RouteServiceImp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author XiaokanG
 * @version 1.0.1
 * @function 处理路线数据
 * @data 2019/4/11 12:40
 */
@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    RouteService routeService = new RouteServiceImp();
    FavoriteService favoriteService = new FavoriteServiceImp();

    /**
     * 查找一页的数据的方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取得到分类
        String cidStr = request.getParameter("cid");
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String rname = request.getParameter("rname");
        rname = new String(rname.getBytes("iso-8859-1"),"utf-8");


        int cid = 0;
        if (cidStr!=null&&cidStr.length()>0&&!"null".equals(cidStr)){
            cid = Integer.parseInt(cidStr);
        }
        int currentPage;
        if (currentPageStr!=null&&currentPageStr.length()>0){
            currentPage = Integer.parseInt(currentPageStr);
        }else {
            //当没有指定当前页数时，默认值为1 即表示第一页
            currentPage = 1;
        }
        int pageSize;
        if (pageSizeStr!=null&&pageSizeStr.length()>0){
             pageSize = Integer.parseInt(pageSizeStr);
        }else {
            //当没有指定每页的个数时 默认设置为5
            pageSize = 5;
        }
        //查询分类 得到一个pageBean对象
        RouteService routeService = new RouteServiceImp();
        PageBean<Route> routePageBean = routeService.findPage(cid,currentPage,pageSize,rname);
        //转为json返回给浏览器
        writeValue(routePageBean,response);
    }


    /**
     * 一条路线的详细信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void oneDetailPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取查询路信息的id
        String rid = request.getParameter("rid");
        //查询数据
        Route route = routeService.oneDetailPage(Integer.parseInt(rid));
        //返回数据
        writeValue(route,response);

    }

    /**
     * 判断当前登录的用户是否收藏了该路线
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //得到传入rid
        String rid = request.getParameter("rid");
        //从session中获取登录的用户id
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int uid;
        if (user==null){
            //用户没有登录,将其id设置为0
            uid = 0;
        }else {
            uid = user.getUid();
        }
        Boolean flag = favoriteService.isFavorite(rid,uid);
        writeValue(flag,response);
    }

    /**
     * 添加收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String rid = request.getParameter("rid");
        Boolean flag = favoriteService.add(user.getUid(),rid);
        writeValue(flag,response);
    }

}
