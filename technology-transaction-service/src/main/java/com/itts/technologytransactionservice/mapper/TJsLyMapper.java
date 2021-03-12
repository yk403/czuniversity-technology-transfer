package com.itts.technologytransactionservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itts.technologytransactionservice.model.TJsLy;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-22 11:11:25
 */
@Repository
public interface TJsLyMapper extends BaseMapper<TJsLy> {
	
	List<TJsLy> list(IPage<TJsLy> page, @Param("map") Map map);

}
