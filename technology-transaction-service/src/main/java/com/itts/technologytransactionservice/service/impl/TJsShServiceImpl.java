package com.itts.technologytransactionservice.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.TJsShMapper;
import com.itts.technologytransactionservice.model.TJsSh;
import com.itts.technologytransactionservice.service.ITJsShService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Primary
public class TJsShServiceImpl extends ServiceImpl<TJsShMapper, TJsSh> implements ITJsShService {
	@Autowired
	private TJsShMapper tJsShMapper;
	@Override
	public IPage page(Query query) {
		Page<TJsSh> p = new Page<>(query.getPageNum(), query.getPageSize());
		List<TJsSh> list = tJsShMapper.list(p,query);
		p.setRecords(list);
		return p;
	}

	@Override
	public TJsSh selectBycgxqId(Long cgxqId) {
		return tJsShMapper.selectBycgxqId(cgxqId);
	}

/*	@Override
	public int updateById(Long id) {
		return tJsShMapper.updateById(id);
	}*/
	public List<TJsSh> selectBycgxqIds(List<Long> cgxqIds) {
		Long[] objects = cgxqIds.toArray(new Long[cgxqIds.size()]);
		return tJsShMapper.selectBycgxqIds(objects);
	}

	//@Transactional(rollbackFor = Exception.class)


}
