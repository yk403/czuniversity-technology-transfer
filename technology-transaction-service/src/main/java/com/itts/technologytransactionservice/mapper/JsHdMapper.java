package com.itts.technologytransactionservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itts.technologytransactionservice.model.TJsHd;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-22 11:17:27
 */
@Repository
public interface JsHdMapper extends BaseMapper<TJsHd> {
	
	List<TJsHd> list(@Param("map") Map map);
	List<TJsHd> listHdBm(@Param("map") Map map);
	List<TJsHd> listCount(@Param("map") Map map);

}
