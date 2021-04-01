package com.itts.technologytransactionservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.technologytransactionservice.model.TJsCg;
import java.util.List;
import java.util.Map;


/**
 * @Author: Austin
 * @Data: 2021/4/1
 * @Description: 技术成果管理接口
 */
public interface JsCgService extends IService<TJsCg> {



	/**
	 * 分页条件查询(前台)
	 * @param params
	 * @return
	 */
	PageInfo findJsCgFront(Map<String, Object> params);

	/**
	 * 分页条件查询(个人详情)
	 * @param params
	 * @return
	 */
	PageInfo<TJsCg> findJsCgUser(Map<String, Object> params);

	/**
	 * 根据成果名称查询
	 * @param name
	 * @return
	 */
	TJsCg selectByName(String name);

	/**
	 * 新增成果信息
	 * @param tJsCg
	 * @return
	 */
	boolean saveCg(TJsCg tJsCg);

	/**
	 * 修改成果信息
	 * @param tJsCg
	 * @return
	 */
	boolean updateTJsCg(TJsCg tJsCg);

	/**
	 * 删除成果信息
	 * @param id
	 * @return
	 */
	boolean removeByIdCg(Integer id);

	boolean passUpdateById(Integer id);
	boolean issueBatch(List<Integer> ids);



    boolean assistanceUpdateTJsCg(TJsCg tJsCg);

	boolean assistancePassUpdateById(Integer id);

	boolean assistanceDisPassById(Map<String, Object> params);

	boolean assistanceIssueBatch(List<Integer> ids);

	boolean removeByIdsCg(List<String> ids);


}
