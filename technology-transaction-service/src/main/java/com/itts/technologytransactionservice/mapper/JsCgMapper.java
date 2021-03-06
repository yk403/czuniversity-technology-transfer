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
	
	List<TJsCg> list( @Param("map") Map map);

	/**
	 * 分页条件查询
	 * @param map
	 * @return
	 */
	List<TJsCg> findJsCgFront(@Param("map") Map map);

	/**
	 * 根据成果名称精准查询
	 * @param name
	 * @return
	 */
	@Select("select * from t_js_cg where fjjg_id = #{fjjgId} and cgmc like #{name} and is_delete = 0")
	TJsCg selectByName(@Param("name") String name,@Param("fjjgId") Long fjjgId);

	/**
	 * 更新成果信息
	 * @param tJsCg
	 */
	void updateTJsCg(TJsCg tJsCg);

	List<TJsCg> findJsCg(@Param("map") Map map);
	List<TJsCg> findGdJsCg(@Param("map") Map map);
	List<TJsCg> findHdJsCg(@Param("map") Map map);

	@Select("select * from t_js_cg where id = #{id} and is_delete = 0")
	TJsCg getById(Integer id);

    List<TJsCg> findByJsCgIds(@Param("ids") List<Integer> ids);
}
