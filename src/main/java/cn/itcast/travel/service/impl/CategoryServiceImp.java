package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImp;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author XiaokanG
 * @version 1.0.1
 * @function 分类的服务的实现类
 * @data 2019/4/10 19:13
 */
public class CategoryServiceImp implements CategoryService {

    private CategoryDao categoryDao = new CategoryDaoImp();

    /**
     * 查询所有的分类
     * 优化：利用redis作为缓存，先在缓存中查找，没有再到数据库中查找，并将找到的值保存到
     * redis数据库中作为缓存
     * 减少数据库的查询次数
     * 当redis数据库没有开启时 不会报错
     * @return
     */
    @Override
    public List<Category> findAll() {
        Set<Tuple> categorys=null;
        Jedis jedis =null;
        try {
            jedis = JedisUtil.getJedis();
            categorys = jedis.zrangeWithScores("category", 0, -1);
        }catch (Exception e){
            System.out.println("redis数据库没有开启");
        }
        //查询缓存
        List<Category> categoryListst = null;
        if (categorys==null||categorys.size()==0){
            //缓存中没有
            //查询数据库
            categoryListst = categoryDao.findAll();
            //保存到redis缓存中
            if (jedis!=null){
                for (int i = 0; i < categoryListst.size(); i++) {
                    jedis.zadd("category",categoryListst.get(i).getCid(),categoryListst.get(i).getCname());
                }
            }
        }else {
            //缓存中有,需要转变格式
            categoryListst = new ArrayList<Category>();
            for (Tuple tuple : categorys) {
                Category cy = new Category();
                cy.setCname(tuple.getElement());
                cy.setCid((int) tuple.getScore());
                categoryListst.add(cy);
            }
        }
         return categoryListst;

    }
}
