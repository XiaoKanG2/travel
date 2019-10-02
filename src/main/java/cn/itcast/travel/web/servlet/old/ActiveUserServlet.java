package cn.itcast.travel.web.servlet.old;

import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author XiaokanG
 * @version 1.0.1
 * @function 用户激活的servlet
 * @data 2019/4/10 13:20
 */
@WebServlet("/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取用户传过来的激活码
        String code = request.getParameter("code");
        //激活码是否存在
        if (code!=null){
            //激活码是否有效 能否找到对应的用户
            UserService userService = new UserServiceImp();
            Boolean flag = userService.active(code);
            String msg; //设置回写的信息
            if (flag){
                //激活成功
                 msg= "激活成功,<a href='login.html'>点击登陆</a>";
            }else {
                //激活失败
                msg = "激活失败，请重试！";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
