package com.itts.technologytransactionservice.service.cd.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.JsCgMapper;
import com.itts.technologytransactionservice.mapper.JsCjRcMapper;
import com.itts.technologytransactionservice.mapper.JsLcKzMapper;
import com.itts.technologytransactionservice.mapper.JsXqMapper;
import com.itts.technologytransactionservice.model.TJsCg;
import com.itts.technologytransactionservice.model.TJsXq;
import com.itts.technologytransactionservice.model.TJsCjRc;
import com.itts.technologytransactionservice.model.TJsLcKz;
import com.itts.technologytransactionservice.model.TJsLcKzDto;
import com.itts.technologytransactionservice.service.cd.JsCjRcAdminService;
import com.itts.technologytransactionservice.service.cd.JsLcKzAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Primary
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class JsLcKzAdminServiceImpl extends ServiceImpl<JsLcKzMapper, TJsLcKz> implements JsLcKzAdminService {
	@Autowired
	private JsLcKzMapper jsLcKzMapper;
	@Autowired
	private JsCgMapper jsCgMapper;
	@Autowired
	private JsXqMapper jsXqMapper;
	@Override
	public PageInfo page(Query query) {
		PageHelper.startPage(query.getPageNum(), query.getPageSize());
		List<TJsLcKz> list = jsLcKzMapper.list(query);
		PageInfo<TJsLcKz> page = new PageInfo<>(list);
		return page;
	}

	@Override
	public boolean removeByIdLcKz(String id) {
		log.info("【技术交易 - 根据id:{}删除报名表】",id);
		TJsLcKz tJsLcKz = new TJsLcKz();
		tJsLcKz.setId(new BigInteger(id));
		tJsLcKz.setIsDelete(1);
		tJsLcKz.setGxsj(new Date());
		jsLcKzMapper.updateById(tJsLcKz);
		return true;
	}
	/*
	拍卖大厅开始竞拍逻辑
	 */
	@Override
	public boolean saveLcKz(TJsLcKzDto tJsLcKzDto) {
			Map<String,Object> map=new HashMap<>();
		if(tJsLcKzDto.getType() == 0){
			TJsXq xqbyId = jsXqMapper.getById(tJsLcKzDto.getXqId());
			xqbyId.setAuctionStatus(tJsLcKzDto.getAuctionStatus());
			jsXqMapper.updateTJsXq(xqbyId);
			TJsLcKz tJsLcKz=new TJsLcKz();
			BeanUtils.copyProperties(tJsLcKzDto,tJsLcKz);
			map.put("xqId",tJsLcKzDto.getXqId());
			List<TJsLcKz> list = jsLcKzMapper.list(map);
			if(list.size() !=0){

			}else{
				save(tJsLcKz);
			}
		}
		if(tJsLcKzDto.getType() == 1){
			TJsCg cgbyId = jsCgMapper.getById(tJsLcKzDto.getCgId());
			cgbyId.setAuctionStatus(tJsLcKzDto.getAuctionStatus());
			jsCgMapper.updateTJsCg(cgbyId);
			TJsLcKz tJsLcKz=new TJsLcKz();
			BeanUtils.copyProperties(tJsLcKzDto,tJsLcKz);
			map.put("cgId",tJsLcKzDto.getCgId());
			List<TJsLcKz> list = jsLcKzMapper.list(map);
			if(list.size() !=0){

			}else{
				save(tJsLcKz);
			}
		}
		return true;
	}

	@Override
	public boolean updateLc(TJsLcKzDto tJsLcKzDto) {
		tJsLcKzDto.setLcsj(new Date());
		TJsLcKz tJsLcKz=new TJsLcKz();
		if(tJsLcKzDto.getType()== 1){
			TJsCg byId = jsCgMapper.getById(tJsLcKzDto.getCgId());
			byId.setAuctionStatus(tJsLcKzDto.getAuctionStatus());
			jsCgMapper.updateTJsCg(byId);
			BeanUtils.copyProperties(tJsLcKzDto,tJsLcKz);
			updateById(tJsLcKz);
		}
		if(tJsLcKzDto.getType()== 0){
			TJsXq byId = jsXqMapper.getById(tJsLcKzDto.getXqId());
			byId.setAuctionStatus(tJsLcKzDto.getAuctionStatus());
			jsXqMapper.updateTJsXq(byId);
			BeanUtils.copyProperties(tJsLcKzDto,tJsLcKz);
			updateById(tJsLcKz);
		}

		return true;
	}

}
