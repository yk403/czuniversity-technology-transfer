package com.itts.technologytransactionservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itts.technologytransactionservice.model.TJsFb;
import com.itts.technologytransactionservice.model.TJsXq;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-22 09:19:20
 */
@Repository
public interface JsXqMapper extends BaseMapper<TJsXq> {
	
	/*List<TJsXq> list(IPage<TJsXq> page, @Param("map") Map map);*/

	@Select("select * from t_js_xq where xqmc = #{name} and is_delete = 0")
	TJsXq selectByName(String name);

	List<TJsXq> FindTJsXqByTJsLbTJsLy(@Param("map") Map map);

	List<TJsFb> PageByTJsFb(@Param("map") Map map);

	List<TJsXq> selectByStages(Integer companyId, String[] stages);

    void updateTJsXq(TJsXq tJsXq);

	TJsXq findById(Integer id);
}
