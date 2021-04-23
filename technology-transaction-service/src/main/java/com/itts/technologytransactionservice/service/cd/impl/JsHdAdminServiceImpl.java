package com.itts.technologytransactionservice.service.cd.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.JsCgMapper;
import com.itts.technologytransactionservice.mapper.JsHdMapper;
import com.itts.technologytransactionservice.mapper.JsXqMapper;
import com.itts.technologytransactionservice.model.JsHdDTO;
import com.itts.technologytransactionservice.model.TJsCg;
import com.itts.technologytransactionservice.model.TJsHd;
import com.itts.technologytransactionservice.model.TJsXq;
import com.itts.technologytransactionservice.service.cd.JsHdAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.itts.common.enums.ErrorCodeEnum.UPDATE_FAIL;

/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术活动业务逻辑
 */
@Service
@Primary
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class JsHdAdminServiceImpl extends ServiceImpl<JsHdMapper,TJsHd> implements JsHdAdminService {
	@Autowired
	private JsHdMapper jsHdMapper;

	@Autowired
	private JsCgMapper jsCgMapper;

	@Autowired
	private JsXqMapper jsXqMapper;

	@Autowired
	private JsHdAdminService jsHdAdminService;
	
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
		List<Map<String,Integer>> ids = jsHdDTO.getIds();
		jsHdDTO.setCjsj(new Date());
		jsHdDTO.setGxsj(new Date());
		BeanUtils.copyProperties(jsHdDTO,tJsHd);
		if (!jsHdAdminService.save(tJsHd)) {
			return false;
		}
		if (hdlx == 0 || hdlx == 2) {
			for (Map item : ids) {
				TJsCg tJsCg = jsCgMapper.getById((Integer) item.get("id"));
				tJsCg.setJshdId(tJsHd.getId());
				tJsCg.setIndes((Integer) item.get("indes"));
				jsCgMapper.updateTJsCg(tJsCg);
			}
		} else if (hdlx == 1) {
			for (Map item : ids) {
				TJsXq tJsXq = jsXqMapper.getById((Integer) item.get("id"));
				tJsXq.setJshdId(tJsHd.getId());
				tJsXq.setIndes((Integer) item.get("indes"));
				jsXqMapper.updateTJsXq(tJsXq);
			}
		}
		return true;
	}

	/**
	 * 批量发布活动
	 * @param ids
	 * @return
	 */
	@Override
	public boolean issueBatch(List<Integer> ids) {
		log.info("【技术交易 - 批量发布活动,ids:{}】",ids);
		List<TJsHd> tJsHds = jsHdMapper.selectBatchIds(ids);
		for (TJsHd tJsHd : tJsHds) {
			tJsHd.setHdfbzt(1);
			try {
				jsHdMapper.updateById(tJsHd);
			} catch (Exception e) {
				log.error("更新失败!",e);
				throw new ServiceException(UPDATE_FAIL);
			}
		}
		return true;
	}

	@Override
	public boolean removeByIdHd(Integer id) {
		TJsHd tJsHd = new TJsHd();
		tJsHd.setId(id);
		tJsHd.setIsDelete(2);
		jsHdMapper.updateById(tJsHd);
		return true;
	}

}
