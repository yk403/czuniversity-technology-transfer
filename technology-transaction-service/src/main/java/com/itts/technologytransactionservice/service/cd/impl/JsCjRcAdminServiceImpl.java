package com.itts.technologytransactionservice.service.cd.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.JsBmMapper;
import com.itts.technologytransactionservice.mapper.JsCjRcMapper;
import com.itts.technologytransactionservice.model.TJsBm;
import com.itts.technologytransactionservice.model.TJsCjRc;
import com.itts.technologytransactionservice.model.TJsCjRcDto;
import com.itts.technologytransactionservice.service.JsCjRcService;
import com.itts.technologytransactionservice.service.cd.JsBmAdminService;
import com.itts.technologytransactionservice.service.cd.JsCjRcAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Primary
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class JsCjRcAdminServiceImpl extends ServiceImpl<JsCjRcMapper, TJsCjRc> implements JsCjRcAdminService {
	@Autowired
	private JsCjRcMapper jsCjRcMapper;

	@Override
	public PageInfo page(Query query) {
		PageHelper.startPage(query.getPageNum(), query.getPageSize());
		List<TJsCjRcDto> list = jsCjRcMapper.listRcJy(query);
		PageInfo<TJsCjRcDto> page = new PageInfo<>(list);
		return page;
	}

	/**
	 * 删除报名表
	 * @param id
	 * @return
	 */
	@Override
	public boolean removeByIdCjRc(String id) {
		log.info("【技术交易 - 根据id:{}删除报名表】",id);
		TJsCjRc tJsCjRc = new TJsCjRc();
		tJsCjRc.setId(new BigInteger(id));
		tJsCjRc.setIsDelete(1);
		tJsCjRc.setGxsj(new Date());
		jsCjRcMapper.updateById(tJsCjRc);
		return true;
	}
	
}
