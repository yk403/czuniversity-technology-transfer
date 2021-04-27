package com.itts.technologytransactionservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.JsBmMapper;
import com.itts.technologytransactionservice.mapper.JsCjRcMapper;
import com.itts.technologytransactionservice.model.TJsBm;
import com.itts.technologytransactionservice.model.TJsCjRc;
import com.itts.technologytransactionservice.model.TJsCjRcDto;
import com.itts.technologytransactionservice.service.JsBmService;
import com.itts.technologytransactionservice.service.JsCjRcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.*;


@Service
@Primary
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class JsCjRcServiceImpl extends ServiceImpl<JsCjRcMapper, TJsCjRc> implements JsCjRcService {
	@Autowired
	private JsCjRcMapper jsCjRcMapper;
    @Autowired
    private JsBmMapper jsBmMapper;
	@Override
	public PageInfo page(Query query) {
		PageHelper.startPage(query.getPageNum(), query.getPageSize());
		List<TJsCjRc> list = jsCjRcMapper.list(query);
		PageInfo<TJsCjRc> page = new PageInfo<>(list);
		return page;
	}

    /**
     * 删除报名表
     * @param id
     * @return
     */
    @Override
    public boolean removeByIdCjRc(String id) {
        log.info("【技术交易 - 根据id:{}删除报名表】",id);
        TJsCjRc tJsCjRc = new TJsCjRc();
        tJsCjRc.setId(new BigInteger(id));
        tJsCjRc.setIsDelete(1);
        tJsCjRc.setGxsj(new Date());
        jsCjRcMapper.updateById(tJsCjRc);
        return true;
    }

    @Override
    public boolean saveCjRc(TJsCjRcDto tJsCjRcDto) {
        tJsCjRcDto.setJjsj(new Date());
        Map<String,Object> map=new HashMap<>();
        map.put("hdId",tJsCjRcDto.getHdId());
        map.put("userId",2);
        List<TJsBm> list = jsBmMapper.list(map);
        TJsCjRc tJsCjRc=new TJsCjRc();
        BeanUtils.copyProperties(tJsCjRcDto,tJsCjRc);
        if(list.size()==1){
            tJsCjRc.setBmId(list.get(0).getId());
        }
        return save(tJsCjRc);
    }


}
