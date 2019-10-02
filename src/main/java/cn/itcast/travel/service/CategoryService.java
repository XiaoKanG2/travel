package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * @author XiaokanG
 * @version 1.0.1
 * @function 分类的服务接口
 * @data 2019/4/10 19:12
 */
public interface CategoryService {

    /**
     * 查询所有的分类
     * @return
     */
    List<Category> findAll();
}
