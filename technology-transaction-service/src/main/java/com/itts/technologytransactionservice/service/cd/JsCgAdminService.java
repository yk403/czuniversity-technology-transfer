package com.itts.technologytransactionservice.service.cd;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.model.TJsCg;

import java.util.List;
import java.util.Map;


/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术成果接口
 */
public interface JsCgAdminService extends IService<TJsCg> {



	TJsCg selectByName(String name);
	PageInfo FindtJsCgByTJsLbTJsLy(Query query);

	boolean saveCg(TJsCg tJsCg) throws Exception;
	boolean removeByIdCg(Integer id);
	boolean disPassById(Map<String, Object> params);
	boolean passUpdateById(Integer id);
	boolean issueBatch(List<Integer> ids);

    boolean updateTJsCg(TJsCg tJsCg);

    boolean assistanceUpdateTJsCg(TJsCg tJsCg);

	boolean assistancePassUpdateById(Integer id);

	boolean assistanceDisPassById(Map<String, Object> params);

	boolean assistanceIssueBatch(List<Integer> ids);

	boolean removeByIdsCg(List<String> ids);
}
