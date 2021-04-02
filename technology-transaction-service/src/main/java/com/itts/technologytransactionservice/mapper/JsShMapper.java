package com.itts.technologytransactionservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itts.technologytransactionservice.model.TJsSh;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-22 11:13:04
 */
@Repository
public interface JsShMapper extends BaseMapper<TJsSh> {
	
	List<TJsSh> list(IPage<TJsSh> page, @Param("map") Map map);

	int save(@Param("id") Long id);

	@Select("select * from t_js_sh where cg_id = #{cgId}")
	TJsSh selectByCgId(@Param("cgId") Integer cgId);

	List<TJsSh> selectBycgxqIds(@Param("cgxqIds") Integer[] cgxqIds);

	void updateJsSh(@Param("cgId") Integer cgId, @Param("xqId") Integer xqId);

	@Select("select * from t_js_sh where xq_id = #{xqId}")
	TJsSh selectByXqId(Integer xqId);

	List<TJsSh> selectBycgIds(@Param("cgIds")Integer[] cgIds);

	List<TJsSh> selectByxqIds(@Param("xqIds")Integer[] xqIds);

	List<TJsSh> selectByCgIds(@Param("cgIds")List<Integer> cgIds);

	List<TJsSh> selectByXqIds(@Param("xqIds")List<Integer> xqIds);
}
