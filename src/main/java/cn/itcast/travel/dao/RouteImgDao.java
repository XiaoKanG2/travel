package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

/**
 * @author XiaokanG
 * @version 1.0.1
 * @function 操作一条路线中的图片表的接口
 * @data 2019/4/12 14:34
 */
public interface RouteImgDao {
    /**
     * 根据rid查找图片的列表
     * @param rid
     * @return
     */
    List<RouteImg> findByRid(int rid);
}
