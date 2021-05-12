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

import java.math.BigDecimal;
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
			Map<String,Object> map=new HashMap<>();//判断
		if(tJsLcKzDto.getType() == 0){
			//TJsXq xqbyId = jsXqMapper.getById(tJsLcKzDto.getXqId());
			Map<String,Object> xqmap=new HashMap<>();
			xqmap.put("jshdId",tJsLcKzDto.getJshdId());
			xqmap.put("soft",tJsLcKzDto.getSoft());
			List<TJsXq> list1 = jsXqMapper.list(xqmap);
			if(list1.size()>0){
				list1.get(0).setAuctionStatus(tJsLcKzDto.getAuctionStatus());
				jsXqMapper.updateTJsXq(list1.get(0));
				/*TJsLcKz tJsLcKz=new TJsLcKz();
				tJsLcKzDto.setXqId(list1.get(0).getId());
				tJsLcKzDto.setFdjg("1000");
				BeanUtils.copyProperties(tJsLcKzDto,tJsLcKz);
				map.put("xqId",list1.get(0).getId());
				List<TJsLcKz> list = jsLcKzMapper.list(map);
				if(list.size() !=0){

				}else{
					save(tJsLcKz);
				}*/
			}
		}
		if(tJsLcKzDto.getType() == 1){
			//TJsCg cgbyId = jsCgMapper.getById(tJsLcKzDto.getCgId());
			Map<String,Object> cgmap=new HashMap<>();
			cgmap.put("jshdId",tJsLcKzDto.getJshdId());
			cgmap.put("soft",tJsLcKzDto.getSoft());
			List<TJsCg> list1 = jsCgMapper.list(cgmap);
			if(list1.size()>0){
				list1.get(0).setAuctionStatus(tJsLcKzDto.getAuctionStatus());
				jsCgMapper.updateTJsCg(list1.get(0));
/*				TJsLcKz tJsLcKz=new TJsLcKz();
				tJsLcKzDto.setCgId(list1.get(0).getId());
				tJsLcKzDto.setFdjg("1000");
				BeanUtils.copyProperties(tJsLcKzDto,tJsLcKz);
				map.put("cgId",list1.get(0).getId());
				List<TJsLcKz> list = jsLcKzMapper.list(map);
				if(list.size() !=0){

				}else{
					save(tJsLcKz);
				}*/
			}
		}
		return true;
	}

	//落锤逻辑
	@Override
	public boolean updateLc(TJsLcKzDto tJsLcKzDto) {
		tJsLcKzDto.setLcsj(new Date());
		TJsLcKz tJsLcKz=new TJsLcKz();
		if(tJsLcKzDto.getType()== 1){
			TJsCg byId = jsCgMapper.getById(tJsLcKzDto.getCgId());
			byId.setAuctionStatus(tJsLcKzDto.getAuctionStatus());
			jsCgMapper.updateTJsCg(byId);
			BeanUtils.copyProperties(tJsLcKzDto,tJsLcKz);
			//计算服务费为落锤价格的5%
			BigDecimal num1 = new BigDecimal("0.05");
			tJsLcKz.setFwf(tJsLcKz.getLcdj().multiply(num1));
			updateById(tJsLcKz);
		}
		if(tJsLcKzDto.getType()== 0){
			TJsXq byId = jsXqMapper.getById(tJsLcKzDto.getXqId());
			byId.setAuctionStatus(tJsLcKzDto.getAuctionStatus());
			jsXqMapper.updateTJsXq(byId);
			BeanUtils.copyProperties(tJsLcKzDto,tJsLcKz);
			//计算服务费为落锤价格的5%
			BigDecimal num1 = new BigDecimal("0.05");
			tJsLcKz.setFwf(tJsLcKz.getLcdj().multiply(num1));
			updateById(tJsLcKz);
		}

		return true;
	}

}
