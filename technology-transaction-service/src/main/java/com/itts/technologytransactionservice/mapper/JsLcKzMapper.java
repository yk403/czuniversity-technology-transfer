package com.itts.technologytransactionservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.technologytransactionservice.model.TJsCjRc;
import com.itts.technologytransactionservice.model.TJsLcKz;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-22 11:14:18
 */
@Repository
public interface JsLcKzMapper extends BaseMapper<TJsLcKz> {
	
	List<TJsLcKz> list(@Param("map") Map map);
	List<TJsLcKz> deleteLcKzByCgIds(@Param("list") List<Integer> tJsLcKzList);
	List<TJsLcKz> deleteLcKzByXqIds(@Param("list") List<Integer> tJsLcKzList);
	/*
	叫价专用(成果)
	 */
	void updateTJsLcKzCg(TJsLcKz tJsLcKz);
	/*
叫价专用(需求)
 */
	void updateTJsLcKzXq(TJsLcKz tJsLcKz);
}
