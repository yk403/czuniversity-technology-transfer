package com.itts.technologytransactionservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.JsLyMapper;
import com.itts.technologytransactionservice.model.TJsLy;
import com.itts.technologytransactionservice.service.JsLyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Primary
public class JsLyServiceImpl extends ServiceImpl<JsLyMapper, TJsLy> implements JsLyService {
	@Autowired
	private JsLyMapper jsLyMapper;
	
	@Override
	public PageInfo page(Query query) {
		PageHelper.startPage(query.getPageNum(), query.getPageSize());
		List<TJsLy> list = jsLyMapper.list(query);
		PageInfo<TJsLy> page = new PageInfo<>(list);
		return page;
	}
	
}
