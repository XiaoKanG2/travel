package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author XiaokanG
 * @version 1.0.1
 * @function
 * @data 2019/4/10 19:09
 */
public class CategoryDaoImp implements CategoryDao {

    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 查询所有的分类
     *  为了和缓存中的顺序保持一致 通过cid排序显示
     * @return
     */
    @Override
    public List<Category> findAll() {
        String sql = "select * from tab_category order by cid";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
    }

    /**
     * 通过id 查找一个分类的信息
     * @param cid
     * @return
     */
    @Override
    public Category findByid(int cid) {
        String sql = "select * from tab_category where cid = ?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Category>(Category.class),cid);

    }
}
