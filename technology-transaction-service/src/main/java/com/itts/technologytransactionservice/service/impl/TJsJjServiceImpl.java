package com.itts.technologytransactionservice.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.TJsJjMapper;
import com.itts.technologytransactionservice.model.TJsJj;
import com.itts.technologytransactionservice.service.ITJsJjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Primary
public class TJsJjServiceImpl extends ServiceImpl<TJsJjMapper,TJsJj> implements ITJsJjService {
	@Autowired
	private TJsJjMapper tJsJjMapper;
	
	@Override
	public IPage page(Query query) {
		Page<TJsJj> p = new Page<>(query.getPageNum(), query.getPageSize());
		List<TJsJj> list = tJsJjMapper.list(p,query);
		p.setRecords(list);
		return p;
	}
	
}
