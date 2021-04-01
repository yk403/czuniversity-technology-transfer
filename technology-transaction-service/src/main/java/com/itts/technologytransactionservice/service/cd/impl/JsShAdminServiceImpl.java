package com.itts.technologytransactionservice.service.cd.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.JsShMapper;
import com.itts.technologytransactionservice.model.TJsSh;
import com.itts.technologytransactionservice.service.cd.JsShAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术审核业务逻辑
 */

@Service
@Primary
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class JsShAdminServiceImpl extends ServiceImpl<JsShMapper, TJsSh> implements JsShAdminService {
	@Autowired
	private JsShMapper jsShMapper;
	@Autowired
	private JsShAdminService jsShAdminService;

	@Override
	public IPage page(Query query) {
		Page<TJsSh> p = new Page<>(query.getPageNum(), query.getPageSize());
		List<TJsSh> list = jsShMapper.list(p,query);
		p.setRecords(list);
		return p;
	}

	public List<TJsSh> selectBycgxqIds(List<Integer> cgxqIds) {
		Integer[] objects = cgxqIds.toArray(new Integer[cgxqIds.size()]);
		return jsShMapper.selectBycgxqIds(objects);
	}

	/**
	 * 发布审核成果(0待提交;1待审核;2通过;3整改;4拒绝)
	 * @param params
	 * @param fbshzt
	 * @return
	 */
	@Override
	public Boolean auditCg(Map<String, Object> params, Integer fbshzt) {
		TJsSh tJsSh = jsShMapper.selectByCgId(Integer.parseInt(params.get("id").toString()));
		String fbshbz = params.get("fbshbz").toString();
		if (fbshzt == 2) {
			tJsSh.setReleaseStatus(2);
		}
		tJsSh.setFbshzt(fbshzt);
		tJsSh.setFbshbz(fbshbz);
		if (!jsShAdminService.updateById(tJsSh)) {
			throw new ServiceException("发布审核成果操作失败!");
		}
		return true;
	}

	/**
	 * 发布审核需求(0待提交;1待审核;2通过;3整改;4拒绝)
	 * @param params
	 * @param fbshzt
	 * @return
	 */
	@Override
	public Boolean auditXq(Map<String, Object> params, Integer fbshzt) {
		TJsSh tJsSh = jsShMapper.selectByCgId(Integer.parseInt(params.get("id").toString()));
		String fbshbz = params.get("fbshbz").toString();
		if (fbshzt == 2) {
			tJsSh.setReleaseStatus(2);
		}
		tJsSh.setFbshzt(fbshzt);
		tJsSh.setFbshbz(fbshbz);
		if (!jsShAdminService.updateById(tJsSh)) {
			throw new ServiceException("发布审核需求操作失败!");
		}
		return true;
	}



}
