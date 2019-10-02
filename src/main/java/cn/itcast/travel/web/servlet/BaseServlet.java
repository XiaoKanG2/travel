package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author XiaokanG
 * @version 1.0.1
 * @function 基础的servlet 完成对不同请求的方法分发
 * @data 2019/4/10 15:52
 */
public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       //获取方法的请求路径
        String uri = req.getRequestURI();//travel/user/add
        //获取方法的名称
        String methodName = uri.substring(uri.lastIndexOf('/') + 1);

        //this 谁调用 代表谁
        try {
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }


    }

    /**
     * 序列化 把对象转为json并转入输出流输出
     * @param o
     * @param response
     * @throws IOException
     */
    public void writeValue(Object o,HttpServletResponse response) throws IOException {
        ObjectMapper om = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        om.writeValue(response.getOutputStream(),o);
    }


    /**
     * 把对象转为json字符串
     * @param o
     * @return
     * @throws IOException
     */
    public String writeValueAsString(Object o) throws IOException {
        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(o);
    }
}
