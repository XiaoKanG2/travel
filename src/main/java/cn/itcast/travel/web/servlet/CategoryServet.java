package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author XiaokanG
 * @version 1.0.1
 * @function
 * @data 2019/4/10 19:06
 */
@WebServlet("/category/*")
public class CategoryServet extends BaseServlet {

    private CategoryService categoryService = new CategoryServiceImp();
    /**
     * 查询所有的分类
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categorys = categoryService.findAll();
        writeValue(categorys,response);
    }
}