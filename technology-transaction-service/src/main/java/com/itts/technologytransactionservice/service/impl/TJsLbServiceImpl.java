package com.itts.technologytransactionservice.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.TJsLbMapper;
import com.itts.technologytransactionservice.model.TJsLb;
import com.itts.technologytransactionservice.service.ITJsLbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Primary
public class TJsLbServiceImpl extends ServiceImpl<TJsLbMapper, TJsLb> implements ITJsLbService {
	@Autowired
	private TJsLbMapper tJsLbMapper;
	
	@Override
	public IPage page(Query query) {
		Page<TJsLb> p = new Page<>(query.getPageNum(), query.getPageSize());
		List<TJsLb> list = tJsLbMapper.list(p,query);
		p.setRecords(list);
		return p;
	}
	
}
