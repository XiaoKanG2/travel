package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * @author XiaokanG
 * @version 1.0.1
 * @function 处理用户的所有的请求的方法
 * @data 2019/4/10 15:54
 */
@SuppressWarnings("ALL")
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    /**
     * 注册的方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //创建返回的对象
        ResultInfo resultInfo = new ResultInfo();
        //json格式化的对象
        ObjectMapper om = new ObjectMapper();
        //给客户端传输的json字符串
        String toClient=null;

        //接受传递过来的验证码
        String check = request.getParameter("check");
        //获取session中的验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER"); //保证验证码的一次性

        //比较用户输入的验证码是否正确
        if (checkcode_server!=null&&checkcode_server.equalsIgnoreCase(check)){
            //验证码正确
            //判断用户是否已经存在
            Map<String, String[]> parameterMap = request.getParameterMap();
            User user = new User();
            try {
                BeanUtils.populate(user,parameterMap);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            UserService userService = new UserServiceImp();
            Boolean b = userService.regist(user);
            if (!b){
                //注册失败 ，返回错误的信息
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("注册失败，用户已经存在！");
                //转为json
                toClient = om.writeValueAsString(resultInfo);
            }else {
                //注册成功，返回信息
                resultInfo.setFlag(true);
                //转为json
                toClient = om.writeValueAsString(resultInfo);
            }
        }else {
            //验证码错误
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("注册失败，验证码错误！");
            //转为json
            toClient = om.writeValueAsString(resultInfo);

        }
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(toClient);
    }

    /**
     * 登录的方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo resultInfo = new ResultInfo(); //响应的对象
        ObjectMapper om = new ObjectMapper(); // 将对象转为json

        //判断用户输入的验证嘛是否正确
        String userCheck = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if (checkcode_server!=null&&checkcode_server.equalsIgnoreCase(userCheck)){
            //验证码正确，获取用户提交的表单数据
            Map<String, String[]> parameterMap = request.getParameterMap();
            //封装为User对象
            User user = new User();
            try {
                BeanUtils.populate(user,parameterMap);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            //调用service
            UserService userService = new UserServiceImp();
            User u  = userService.login(user);
            //登陆成功
            if (u!=null&&"Y".equals(u.getStatus())){
                resultInfo.setFlag(true);
                session.setAttribute("user",u);
            }
            //登陆失败
            else if (u!=null&&"N".equals(u.getStatus())){
                //没有激活
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("用户没有激活，请去邮箱激活！");
            }
            else if (u==null){
                //用户名或密码错误
                resultInfo.setErrorMsg("用户名或密码错误！");
                resultInfo.setFlag(false);
            }
        }else {
            //验证码错误
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误，请刷新验证码重试！");
        }

        String data = om.writeValueAsString(resultInfo);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(data);
    }

    /**
     * 查找是否登录的方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取session
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        writeValue(user,response);
    }

    /**
     * 退出登录的方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取session
        HttpSession session = request.getSession();
        //销毁session
        session.invalidate();
        //转跳到登录页面
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

    /**
     * 激活的方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取用户传过来的激活码
            String code = request.getParameter("code");
            //激活码是否存在
            if (code != null) {
                    //激活码是否有效 能否找到对应的用户
                    UserService userService = new UserServiceImp();
                    Boolean flag = userService.active(code);
                    String msg; //设置回写的信息
                    if (flag) {
                            //激活成功
                            msg = "激活成功,<a href='login.html'>点击登陆</a>";
                        } else {
                            //激活失败
                            msg = "激活失败，请重试！";
                        }
                    response.setContentType("text/html;charset=utf-8");
                    response.getWriter().write(msg);
                }

        }

    /**
     * 某一个用户的收藏的方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void myFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //要求每页固定对的个数是12个
        //从session中获取用户的信息
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int uid = 0;
        if (user!=null){
            uid = user.getUid();
        }
        //获取当前的页数
        String currentPage = request.getParameter("currentPage");
        //每页的数据量
        String pageSize = request.getParameter("pageSize");
        //调用userServ方法 返回数据
        UserService userService = new UserServiceImp();
        PageBean<Route> routePage = userService.myFavorite(uid,currentPage,pageSize);
        //将数据写回客户端
        writeValue(routePage,response);
    }
}

