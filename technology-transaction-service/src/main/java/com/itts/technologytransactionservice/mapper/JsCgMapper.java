package com.itts.technologytransactionservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.model.TJsCg;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-22 09:16:14
 */
@Repository
public interface JsCgMapper extends BaseMapper<TJsCg> {
	
	List<TJsCg> list(IPage<TJsCg> page, @Param("map") Map map);

	List<TJsCg> FindtJsCgByTJsLbTJsLy(@Param("map") Map map);

	void updateTJsCg(TJsCg tJsCg);

	List<TJsCg> findJsCg(@Param("map") Map map);

	@Select("select * from t_js_cg where id = #{id} and is_delete = 0")
	TJsCg findById(Integer id);

	@Select("select * from t_js_cg where cgmc like #{name} and is_delete = 0")
	TJsCg selectByName(String name);
}
