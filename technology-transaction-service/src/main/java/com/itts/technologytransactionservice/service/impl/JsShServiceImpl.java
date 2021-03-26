package com.itts.technologytransactionservice.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.JsCgMapper;
import com.itts.technologytransactionservice.mapper.JsShMapper;
import com.itts.technologytransactionservice.mapper.JsXqMapper;
import com.itts.technologytransactionservice.model.TJsSh;
import com.itts.technologytransactionservice.service.JsShService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术审核业务逻辑
 */

@Service
@Primary
public class JsShServiceImpl extends ServiceImpl<JsShMapper, TJsSh> implements JsShService {
	@Autowired
	private JsShMapper jsShMapper;

	@Autowired
	private JsCgMapper jsCgMapper;

	@Autowired
	private JsXqMapper jsXqMapper;
	@Override
	public IPage page(Query query) {
		Page<TJsSh> p = new Page<>(query.getPageNum(), query.getPageSize());
		List<TJsSh> list = jsShMapper.list(p,query);
		p.setRecords(list);
		return p;
	}

	@Override
	public TJsSh selectBycgxqId(Integer cgxqId,Integer lx) {
		return jsShMapper.selectBycgxqId(cgxqId,lx);
	}

	public List<TJsSh> selectBycgxqIds(List<Integer> cgxqIds) {
		Integer[] objects = cgxqIds.toArray(new Integer[cgxqIds.size()]);
		return jsShMapper.selectBycgxqIds(objects);
	}

	@Override
	public boolean deleteById(Long cgId, Long xqId) {
		//TODO 未完成
		if (cgId != null) {

		}
		Integer isDelete = 1;
		jsShMapper.updateJsSh(cgId.intValue(),xqId.intValue());
		return false;
	}

	//@Transactional(rollbackFor = Exception.class)


}
