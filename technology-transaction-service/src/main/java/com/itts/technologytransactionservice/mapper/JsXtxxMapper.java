package com.itts.technologytransactionservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.technologytransactionservice.model.JsXtxx;
import com.itts.technologytransactionservice.model.LyBmDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/*
 *@Auther: yukai
 *@Date: 2021/07/06/9:19
 *@Desription:
 */
public interface JsXtxxMapper extends BaseMapper<JsXtxx> {
    /**
     * 分页条件查询需求
     *
     * @param map
     * @return
     */
    List<JsXtxx> findJsXtxx(@Param("map") Map map);
}
