package com.itts.technologytransactionservice.service.cd.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.JsCgMapper;
import com.itts.technologytransactionservice.mapper.JsHdMapper;
import com.itts.technologytransactionservice.mapper.JsLcKzMapper;
import com.itts.technologytransactionservice.mapper.JsXqMapper;
import com.itts.technologytransactionservice.model.*;
import com.itts.technologytransactionservice.service.cd.JsHdAdminService;
import com.itts.technologytransactionservice.service.cd.JsLcKzAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
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
    private JsLcKzMapper jsLcKzMapper;
	@Autowired
	private JsCgMapper jsCgMapper;
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

	@Override
	public boolean removeByIdHd(Integer id) {
		TJsHd tJsHd = getById(id);

		if(tJsHd!=null && tJsHd.getHdzt() == 0){
			tJsHd.setId(id);
			tJsHd.setIsDelete(2);
			jsHdMapper.updateById(tJsHd);
		}else if(tJsHd!=null && tJsHd.getHdzt() != 0){
			tJsHd.setId(id);
			tJsHd.setIsDelete(2);
			jsHdMapper.updateById(tJsHd);
		}else{
			throw new ServiceException("删除错误");
		}
		return true;
	}

	@Override
	public boolean updateHd(TJsHd tJsHd) {
		tJsHd.setHdjssj(new Date());
		return updateById(tJsHd);
	}

}
