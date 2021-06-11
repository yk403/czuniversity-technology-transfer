package com.itts.technologytransactionservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.technologytransactionservice.model.TJsBm;
import com.itts.technologytransactionservice.model.TJsCg;
import com.itts.technologytransactionservice.model.TJsCjRc;
import com.itts.technologytransactionservice.model.TJsCjRcDto;
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
public interface JsCjRcMapper extends BaseMapper<TJsCjRc> {
	
	List<TJsCjRc> list(@Param("map") Map map);
	List<TJsCjRc> listMax(@Param("map") Map map);
	//List<TJsCjRcDto> listRcHd(@Param("map") Map map);
	List<TJsCjRcDto> listRcJy(@Param("map") Map map);
	/**
	 * 叫价专用(成果)
	 * @param tJsCg
	 */
	void saveTJsCjRcCg(TJsCjRc tJsCjRc);
	/**
	 * 叫价专用(需求)
	 * @param tJsCg
	 */
	void saveTJsCjRcXq(TJsCjRc tJsCjRc);
}
