package com.itts.technologytransactionservice.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.TQyMapper;
import com.itts.technologytransactionservice.model.TQy;
import com.itts.technologytransactionservice.service.ITQyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Primary
public class TQyServiceImpl extends ServiceImpl<TQyMapper,TQy> implements ITQyService {
	@Autowired
	private TQyMapper tQyMapper;
	
	@Override
	public IPage page(Query query) {
		Page<TQy> p = new Page<>(query.getPageNum(), query.getPageSize());
		List<TQy> list = tQyMapper.list(p,query);
		p.setRecords(list);
		return p;
	}
	
}
