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

	PageInfo findJsCg(Map<String, Object> params);

	boolean removeByCgId(Integer id);

	TJsCg selectByName(String name);

	boolean saveCg(TJsCg tJsCg);

	boolean issueBatch(List<Integer> ids);

    void updateTJsCg(TJsCg tJsCg);

	boolean assistanceIssueBatch(List<Integer> ids);

	boolean removeByIdsCg(List<String> ids);

	TJsCg findById(Integer id);
}
