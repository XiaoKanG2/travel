package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

/**
 * @author XiaokanG
 * @version 1.0.1
 * @function 操作商家数据库的接口
 * @data 2019/4/12 14:32
 */
public interface SellerDao {

    /**
     * 通过商家的id查找一个
     * @param sid
     * @return
     */
    Seller findByid(int sid);
}
