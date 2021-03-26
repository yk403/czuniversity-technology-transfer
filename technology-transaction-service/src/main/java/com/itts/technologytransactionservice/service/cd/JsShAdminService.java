package com.itts.technologytransactionservice.service.cd;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.model.TJsSh;

import java.util.List;


/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术审核接口
 */
public interface JsShAdminService extends IService<TJsSh> {

	IPage page(Query query);

	TJsSh selectBycgxqId(Integer cgxqId,Integer lx);

	List<TJsSh> selectBycgxqIds(List<Integer> cgxqIds);

	/*int updateById(Long id);*/
}
