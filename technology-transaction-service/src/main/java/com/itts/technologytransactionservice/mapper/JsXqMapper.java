package com.itts.technologytransactionservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.model.TJsCg;
import com.itts.technologytransactionservice.model.TJsFb;
import com.itts.technologytransactionservice.model.TJsXq;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-22 09:19:20
 */
@Repository
public interface JsXqMapper extends BaseMapper<TJsXq> {

    List<TJsXq> findJsXq(@Param("map") Map map);
    List<TJsXq> findHdJsXq(@Param("map") Map map);

    @Select("select * from t_js_xq where id = #{id} and is_delete = 0")
    TJsXq getById(Integer id);

    @Select("select * from t_js_xq where fjjg_id = #{fjjgId} and xqmc like #{name} and is_delete = 0")
    TJsXq selectByName(@Param("name") String name,@Param("fjjgId") Long fjjgId);

    List<TJsFb> PageByTJsFb(@Param("map") Map map);

    /**
     * 更新技术需求
     *
     * @param tJsXq
     */
    boolean updateTJsXq(TJsXq tJsXq);
    List<TJsXq> list(@Param("map") Map map);

    /**
     * 分页条件查询需求
     *
     * @param map
     * @return
     */
    List<TJsXq> findJsXqFront(@Param("map") Map map);
    /**
     * 分页条件查询需求
     *
     * @param map
     * @return
     */
    List<TJsXq> findGdJsXq(@Param("map") Map map);

    List<TJsXq> findJsXqByIds(@Param("ids") List<Integer> ids);
}
