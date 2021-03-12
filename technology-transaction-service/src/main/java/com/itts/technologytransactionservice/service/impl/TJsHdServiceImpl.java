package com.itts.technologytransactionservice.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.TJsHdMapper;
import com.itts.technologytransactionservice.model.TJsHd;
import com.itts.technologytransactionservice.service.ITJsHdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Primary
public class TJsHdServiceImpl extends ServiceImpl<TJsHdMapper,TJsHd> implements ITJsHdService {
	@Autowired
	private TJsHdMapper tJsHdMapper;
	
	@Override
	public IPage page(Query query) {
		Page<TJsHd> p = new Page<>(query.getPageNum(), query.getPageSize());
		List<TJsHd> list = tJsHdMapper.list(p,query);
		p.setRecords(list);
		return p;
	}
	
}
