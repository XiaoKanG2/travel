package cn.itcast.travel.web.servlet.old;

import cn.itcast.travel.domain.ResultInfo;
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
import java.util.Map;

/**
 * @author XiaokanG
 * @version 1.0.1
 * @function 用户登陆
 * @data 2019/4/10 14:14
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo resultInfo = new ResultInfo(); //响应的对象
        ObjectMapper om = new ObjectMapper(); // 将对象转为json

        //判断用户输入的验证嘛是否正确
        String userCheck = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");

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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
