package com.itts.technologytransactionservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.JsHdMapper;
import com.itts.technologytransactionservice.model.TJsHd;
import com.itts.technologytransactionservice.service.JsHdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Primary
public class JsHdServiceImpl extends ServiceImpl<JsHdMapper,TJsHd> implements JsHdService {
	@Autowired
	private JsHdMapper jsHdMapper;
	
	@Override
	public PageInfo page(Query query) {
		PageHelper.startPage(query.getPageNum(), query.getPageSize());
		List<TJsHd> list = jsHdMapper.list(query);
		PageInfo<TJsHd> page = new PageInfo<>(list);
		return page;
	}
	
}
