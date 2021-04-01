package com.itts.technologytransactionservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.model.TJsCg;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-22 09:16:14
 */
public interface JsCgService extends IService<TJsCg> {

	TJsCg selectByName(String name);

	PageInfo FindtJsCgByTJsLbTJsLy(Query query);

	boolean saveCg(TJsCg tJsCg);
	boolean removeByIdCg(Integer id);
	boolean passUpdateById(Integer id);
	boolean issueBatch(List<Integer> ids);

    boolean updateTJsCg(TJsCg tJsCg);

    boolean assistanceUpdateTJsCg(TJsCg tJsCg);

	boolean assistancePassUpdateById(Integer id);

	boolean assistanceDisPassById(Map<String, Object> params);

	boolean assistanceIssueBatch(List<Integer> ids);

	boolean removeByIdsCg(List<String> ids);

}
