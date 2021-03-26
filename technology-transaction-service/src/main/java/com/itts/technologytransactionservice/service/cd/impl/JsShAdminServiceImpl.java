package com.itts.technologytransactionservice.service.cd.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.JsShMapper;
import com.itts.technologytransactionservice.model.TJsSh;
import com.itts.technologytransactionservice.service.cd.JsShAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术审核业务逻辑
 */

@Service
@Primary
public class JsShAdminServiceImpl extends ServiceImpl<JsShMapper, TJsSh> implements JsShAdminService {
	@Autowired
	private JsShMapper jsShMapper;
	@Override
	public IPage page(Query query) {
		Page<TJsSh> p = new Page<>(query.getPageNum(), query.getPageSize());
		List<TJsSh> list = jsShMapper.list(p,query);
		p.setRecords(list);
		return p;
	}

	@Override
	public TJsSh selectBycgxqId(Integer cgxqId,Integer lx) {
		return jsShMapper.selectBycgxqId(cgxqId,lx);
	}

	public List<TJsSh> selectBycgxqIds(List<Integer> cgxqIds) {
		Integer[] objects = cgxqIds.toArray(new Integer[cgxqIds.size()]);
		return jsShMapper.selectBycgxqIds(objects);
	}

}
