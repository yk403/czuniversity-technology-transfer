package com.itts.technologytransactionservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.JsBmMapper;
import com.itts.technologytransactionservice.model.TJsBm;
import com.itts.technologytransactionservice.model.TJsCg;
import com.itts.technologytransactionservice.model.TJsSh;
import com.itts.technologytransactionservice.service.JsBmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Primary
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class JsBmServiceImpl extends ServiceImpl<JsBmMapper, TJsBm> implements JsBmService {
	@Autowired
	private JsBmMapper jsBmMapper;
	
	@Override
	public PageInfo page(Query query) {
		PageHelper.startPage(query.getPageNum(), query.getPageSize());
		List<TJsBm> list = jsBmMapper.list(query);
		PageInfo<TJsBm> page = new PageInfo<>(list);
		return page;
	}
	/**
	 * 删除报名表
	 * @param id
	 * @return
	 */
	@Override
	public boolean removeByIdBm(String id) {
		log.info("【技术交易 - 根据id:{}删除报名表】",id);
		TJsBm tJsBm = new TJsBm();
		tJsBm.setId(Integer.parseInt(id));
		tJsBm.setIsDelete(1);
		tJsBm.setGxsj(new Date());
		jsBmMapper.updateById(tJsBm);
		return true;
	}
	/**
	 * 批量删除成果
	 * @param ids
	 * @return
	 */
	@Override
	public boolean removeByIdsBm(List<String> ids) {
		List<TJsBm> list = new ArrayList<>();
		for (String id : ids) {
			TJsBm tJsBm =new TJsBm();
			tJsBm.setId(Integer.parseInt(id));
			tJsBm.setIsDelete(1);
			list.add(tJsBm);
		}
		updateBatchById(list);

		return true;
	}
}
