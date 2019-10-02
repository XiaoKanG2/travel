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
 * @function 用户注册的servlet
 * @data 2019/4/10 9:38
 */
@WebServlet("/registUserServlet")
public class RegistUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
