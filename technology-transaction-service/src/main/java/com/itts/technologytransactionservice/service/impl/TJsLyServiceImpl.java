package com.itts.technologytransactionservice.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.TJsLyMapper;
import com.itts.technologytransactionservice.model.TJsLy;
import com.itts.technologytransactionservice.service.ITJsLyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Primary
public class TJsLyServiceImpl extends ServiceImpl<TJsLyMapper, TJsLy> implements ITJsLyService {
	@Autowired
	private TJsLyMapper tJsLyMapper;
	
	@Override
	public IPage page(Query query) {
		Page<TJsLy> p = new Page<>(query.getPageNum(), query.getPageSize());
		List<TJsLy> list = tJsLyMapper.list(p,query);
		p.setRecords(list);
		return p;
	}
	
}
