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
	 * 分页条件查询成果(前台)
	 * @param params
	 * @return
	 */
	PageInfo findJsCgFront(Map<String, Object> params);

	/**
	 * 分页条件查询成果(个人详情)
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

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	boolean removeByIdsCg(List<String> ids);

	/**
	 * 已发布的成果申请拍卖挂牌(受理协办)
	 * @param tJsCg
	 * @return
	 */
	boolean assistanceUpdateTJsCg(TJsCg tJsCg);

	/**
	 * 个人发布审核成果申请(0待提交;1待审核;2通过;3整改;4拒绝)
	 * @param params
	 * @return
	 */
	boolean auditCg(Map<String, Object> params, Integer fbshzt);

	boolean passUpdateById(Integer id);
	boolean issueBatch(List<Integer> ids);




	boolean assistancePassUpdateById(Integer id);

	boolean assistanceDisPassById(Map<String, Object> params);

	boolean assistanceIssueBatch(List<Integer> ids);


}
