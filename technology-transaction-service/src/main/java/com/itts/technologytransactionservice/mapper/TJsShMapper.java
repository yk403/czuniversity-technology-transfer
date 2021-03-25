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
public interface TJsShMapper extends BaseMapper<TJsSh> {
	
	List<TJsSh> list(IPage<TJsSh> page, @Param("map") Map map);

	int save(@Param("id") Long id);

	@Select("select * from t_js_sh where cgxq_id = #{cgxqId}")
	TJsSh selectBycgxqId(@Param("cgxqId") Integer cgxqId);
	List<TJsSh> selectBycgxqIds(@Param("cgxqIds") Integer[] cgxqIds);

/*	@Update("update t_js_sh set fbshzt=2 where cgxq_id=#{id}")
	int updateById(Long id);*/
}
