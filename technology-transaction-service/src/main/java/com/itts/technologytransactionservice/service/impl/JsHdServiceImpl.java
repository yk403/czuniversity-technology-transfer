package com.itts.technologytransactionservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.JsCgMapper;
import com.itts.technologytransactionservice.mapper.JsHdMapper;
import com.itts.technologytransactionservice.mapper.JsXqMapper;
import com.itts.technologytransactionservice.model.JsHdDTO;
import com.itts.technologytransactionservice.model.TJsCg;
import com.itts.technologytransactionservice.model.TJsHd;
import com.itts.technologytransactionservice.model.TJsXq;
import com.itts.technologytransactionservice.service.JsCgService;
import com.itts.technologytransactionservice.service.JsHdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
@Primary
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class JsHdServiceImpl extends ServiceImpl<JsHdMapper,TJsHd> implements JsHdService {
	@Autowired
	private JsHdMapper jsHdMapper;

	@Autowired
	private JsCgMapper jsCgMapper;

	@Autowired
	private JsXqMapper jsXqMapper;

	@Autowired
	private JsHdService jsHdService;
	
	@Override
	public PageInfo page(Query query) {
		PageHelper.startPage(query.getPageNum(), query.getPageSize());
		List<TJsHd> list = jsHdMapper.list(query);
		PageInfo<TJsHd> page = new PageInfo<>(list);
		return page;
	}

	/**
	 * 新增技术活动
	 * @param jsHdDTO
	 * @return
	 */
	@Override
	public boolean add(JsHdDTO jsHdDTO) {
		log.info("【技术交易 - 新增技术活动:{}】",jsHdDTO);
		TJsHd tJsHd = new TJsHd();
		Integer hdlx = jsHdDTO.getHdlx();
		List<Integer> ids = jsHdDTO.getIds();
		jsHdDTO.setCjsj(new Date());
		BeanUtils.copyProperties(jsHdDTO,tJsHd);
		if (!jsHdService.save(tJsHd)) {
			return false;
		}
		if (hdlx == 0 || hdlx == 2) {
			for (Integer id : ids) {
				TJsCg tJsCg = jsCgMapper.getById(id);
				tJsCg.setJshdId(tJsHd.getId());
				jsCgMapper.updateTJsCg(tJsCg);
			}
		} else if (hdlx == 1) {
			for (Integer id : ids) {
				TJsXq tJsXq = jsXqMapper.getById(id);
				tJsXq.setJshdId(tJsHd.getId());
				jsXqMapper.updateTJsXq(tJsXq);
			}
		}
		return true;
	}

}
