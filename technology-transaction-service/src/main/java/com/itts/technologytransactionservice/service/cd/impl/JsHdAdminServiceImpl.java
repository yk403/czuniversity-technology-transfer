package com.itts.technologytransactionservice.service.cd.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.JsCgMapper;
import com.itts.technologytransactionservice.mapper.JsHdMapper;
import com.itts.technologytransactionservice.mapper.JsLcKzMapper;
import com.itts.technologytransactionservice.mapper.JsXqMapper;
import com.itts.technologytransactionservice.model.*;
import com.itts.technologytransactionservice.service.cd.JsCgAdminService;
import com.itts.technologytransactionservice.service.cd.JsHdAdminService;
import com.itts.technologytransactionservice.service.cd.JsLcKzAdminService;
import com.itts.technologytransactionservice.service.cd.JsXqAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

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
    private JsLcKzMapper jsLcKzMapper;
	@Autowired
	private JsCgMapper jsCgMapper;
	@Autowired
	private JsCgAdminService jsCgAdminService;
	@Autowired
	private JsXqAdminService jsXqAdminService;
    @Autowired
    private JsLcKzAdminService jsLcKzAdminService;
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
	 * 新增技术活动(需调用redis或mq优化)
	 * 
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
		LoginUser loginUser = SystemConstant.threadLocal.get();
		Long fjjgId = loginUser.getFjjgId();
		tJsHd.setFjjgId(fjjgId);
		if (!jsHdAdminService.save(tJsHd)) {
			return false;
		}
		//判断需求还是成果
		if (hdlx == 0 || hdlx == 2) {
			for (Map item : ids) {
				TJsCg tJsCg = jsCgMapper.getById((Integer) item.get("id"));
				tJsCg.setJshdId(tJsHd.getId());
				tJsCg.setSoft((Integer) item.get("soft"));
				tJsCg.setAuctionStatus(0);
                Map<String,Object>querymap=new HashMap<String,Object>();
                querymap.put("cgId",tJsCg.getId());
                List<TJsLcKz> list2 = jsLcKzMapper.list(querymap);
                if(list2.size()==0){
                    TJsLcKz tJsLcKz=new TJsLcKz();
                    tJsLcKz.setCgId(tJsCg.getId());
                    tJsLcKz.setFdjg(new BigDecimal(item.get("fdjg").toString()));
                    tJsLcKz.setDj(new BigDecimal(item.get("dj").toString()));
					tJsLcKz.setDqzgjg(new BigDecimal(item.get("dj").toString()));
                    jsLcKzAdminService.save(tJsLcKz);
                }else if(list2.size()==1){
					list2.get(0).setCgId(tJsCg.getId());
					list2.get(0).setFdjg(new BigDecimal(item.get("fdjg").toString()));
					list2.get(0).setDj(new BigDecimal(item.get("dj").toString()));
					list2.get(0).setDqzgjg(new BigDecimal(item.get("dj").toString()));
					list2.get(0).setJjjgzt(0);
					jsLcKzAdminService.updateById(list2.get(0));
				}
				jsCgMapper.updateTJsCg(tJsCg);
			}
		} else if (hdlx == 1) {
			for (Map item : ids) {
				TJsXq tJsXq = jsXqMapper.getById((Integer) item.get("id"));
				tJsXq.setJshdId(tJsHd.getId());
				tJsXq.setSoft((Integer) item.get("soft"));
				tJsXq.setAuctionStatus(0);
                Map<String,Object>querymap=new HashMap<String,Object>();
                querymap.put("xqId",tJsXq.getId());
                List<TJsLcKz> list2 = jsLcKzMapper.list(querymap);
                if(list2.size()==0){
                    TJsLcKz tJsLcKz=new TJsLcKz();
                    tJsLcKz.setXqId(tJsXq.getId());
                    tJsLcKz.setFdjg(new BigDecimal(item.get("fdjg").toString()));
                    tJsLcKz.setDj(new BigDecimal(item.get("dj").toString()));
					tJsLcKz.setDqzgjg(new BigDecimal(item.get("dj").toString()));
                    jsLcKzAdminService.save(tJsLcKz);
                }else if(list2.size()==1){
					list2.get(0).setXqId(tJsXq.getId());
					list2.get(0).setFdjg(new BigDecimal(item.get("fdjg").toString()));
					list2.get(0).setDj(new BigDecimal(item.get("dj").toString()));
					list2.get(0).setDqzgjg(new BigDecimal(item.get("dj").toString()));
					list2.get(0).setJjjgzt(0);
					jsLcKzAdminService.updateById(list2.get(0));
				}
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
	//级联删除(需使用mq或redis优化)
	@Override
	public boolean removeByIdHd(Integer id) {
		TJsHd tJsHd = getById(id);
		if(tJsHd!=null && tJsHd.getHdzt() == 0){
			tJsHd.setId(id);
			tJsHd.setIsDelete(2);
			jsHdMapper.updateById(tJsHd);
			if(tJsHd.getHdlx() == 0 || tJsHd.getHdlx() == 2){
				Map<String,Object>querymap=new HashMap<String,Object>();
				querymap.put("jshdId",tJsHd.getId());
				List<TJsCg> cgList = jsCgMapper.list(querymap);
				if(cgList.size()>0){
					List<TJsCg> cgListUpdate =new ArrayList<TJsCg>();
					List<Integer> cgids =new ArrayList<Integer>();
					for (TJsCg item:cgList) {
						item.setAuctionStatus(0);
						item.setSoft(null);
						item.setJshdId(null);
						//将要更新的成果实体类和成果id缓存保存到list和数组中，集中进行sql操作
						cgListUpdate.add(item);
						cgids.add(item.getId());
					}
					jsLcKzMapper.deleteLcKzByCgIds(cgids);
					jsCgAdminService.updateBatchById(cgListUpdate);
				}
			}else if(tJsHd.getHdlx() == 1){
				Map<String,Object>querymap=new HashMap<String,Object>();
				querymap.put("jshdId",tJsHd.getId());
				List<TJsXq> xqList = jsXqMapper.list(querymap);
				if(xqList.size()>0){
					List<TJsXq> xqListUpdate =new ArrayList<TJsXq>();
					List<Integer> xqids =new ArrayList<Integer>();
					for (TJsXq item:xqList) {
						item.setAuctionStatus(0);
						item.setSoft(null);
						item.setJshdId(null);
						//将要更新的需求实体类和成果id缓存保存到list和数组中，集中进行sql操作
						xqListUpdate.add(item);
						xqids.add(item.getId());
					}
					jsLcKzMapper.deleteLcKzByXqIds(xqids);
					jsXqAdminService.updateBatchById(xqListUpdate);
				}
			}
		}else{
			throw new ServiceException("活动已开始过，无法删除");
		}
		return true;
	}

	@Override
	public boolean updateHd(TJsHd tJsHd) {
		tJsHd.setHdjssj(new Date());
		return updateById(tJsHd);
	}

}
