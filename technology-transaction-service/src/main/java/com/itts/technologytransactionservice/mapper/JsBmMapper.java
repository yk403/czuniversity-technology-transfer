package com.itts.technologytransactionservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.technologytransactionservice.model.TJsBm;
import com.itts.technologytransactionservice.model.TJsBmDto;
import com.itts.technologytransactionservice.model.TJsLb;
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
public interface JsBmMapper extends BaseMapper<TJsBm> {
	
	List<TJsBmDto> list(@Param("map") Map map);

}
