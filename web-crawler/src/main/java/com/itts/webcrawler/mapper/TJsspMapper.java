package com.itts.webcrawler.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.webcrawler.model.TJssp;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/*
 *@Auther: yukai
 *@Date: 2021/07/12/16:37
 *@Desription:
 */
public interface TJsspMapper extends BaseMapper<TJssp> {
    /**
     * 分页条件查询需求
     *
     * @param map
     * @return
     */
    List<TJssp> findTJssp(@Param("map") Map map);
}
