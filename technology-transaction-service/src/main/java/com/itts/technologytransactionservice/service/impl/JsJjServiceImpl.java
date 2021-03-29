package com.itts.technologytransactionservice.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.JsJjMapper;
import com.itts.technologytransactionservice.model.TJsJj;
import com.itts.technologytransactionservice.service.JsJjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Primary
public class JsJjServiceImpl extends ServiceImpl<JsJjMapper,TJsJj> implements JsJjService {
	@Autowired
	private JsJjMapper jsJjMapper;
	
	@Override
	public IPage page(Query query) {
		Page<TJsJj> p = new Page<>(query.getPageNum(), query.getPageSize());
		List<TJsJj> list = jsJjMapper.list(p,query);
		p.setRecords(list);
		return p;
	}
	
}
