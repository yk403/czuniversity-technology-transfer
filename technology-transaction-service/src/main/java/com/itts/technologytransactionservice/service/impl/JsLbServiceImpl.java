package com.itts.technologytransactionservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.JsLbMapper;
import com.itts.technologytransactionservice.model.TJsLb;
import com.itts.technologytransactionservice.service.JsLbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Primary
public class JsLbServiceImpl extends ServiceImpl<JsLbMapper, TJsLb> implements JsLbService {
	@Autowired
	private JsLbMapper jsLbMapper;
	
	@Override
	public PageInfo page(Query query) {
		PageHelper.startPage(query.getPageNum(), query.getPageSize());
		List<TJsLb> list = jsLbMapper.list(query);
		PageInfo<TJsLb> page = new PageInfo<>(list);
		return page;
	}
	
}
