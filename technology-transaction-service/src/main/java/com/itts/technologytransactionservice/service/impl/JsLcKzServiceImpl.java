package com.itts.technologytransactionservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.JsCjRcMapper;
import com.itts.technologytransactionservice.mapper.JsLcKzMapper;
import com.itts.technologytransactionservice.model.TJsCjRc;
import com.itts.technologytransactionservice.model.TJsLcKz;
import com.itts.technologytransactionservice.service.JsCjRcService;
import com.itts.technologytransactionservice.service.JsLcKzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;


@Service
@Primary
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class JsLcKzServiceImpl extends ServiceImpl<JsLcKzMapper, TJsLcKz> implements JsLcKzService {
	@Autowired
	private JsLcKzMapper jsLcKzMapper;
	
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




}
