package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.Seller;

import java.util.List;

/**
 * @author XiaokanG
 * @version 1.0.1
 * @function 对于分类的数据操作的接口
 * @data 2019/4/10 19:07
 */
public interface CategoryDao {
    /**
     * 查询所有的分类
     * @return
     */
    List<Category> findAll();

    /**
     * 通过id查找一个分类
     * @param cid
     * @return
     */
    Category findByid(int cid);
}
