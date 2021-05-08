package com.itts.technologytransactionservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
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

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;


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
		List<TJsCjRcDto> list = jsCjRcMapper.listRcHd(query);
		PageInfo<TJsCjRcDto> page = new PageInfo<>(list);
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
        Map<String,Object> map=new HashMap<>();
        map.put("hdId",tJsCjRcDto.getHdId());
        map.put("userId",2);
        List<TJsBm> list = jsBmMapper.list(map);
        TJsCjRc tJsCjRc=new TJsCjRc();
        BeanUtils.copyProperties(tJsCjRcDto,tJsCjRc);
        //暂时设定单个活动多个报名信息默认显示最新的那个
        if(list.size()>0){
            tJsCjRc.setBmId(list.get(0).getId());
            tJsCjRc.setCjf(list.get(0).getDwmc());
        }
        return save(tJsCjRc);
    }
    /**
     * 获取当前用户id
     * @return
     */
    public Long getUserId() {
        LoginUser loginUser = threadLocal.get();
        Long userId;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        } else {
            throw new ServiceException(GET_THREADLOCAL_ERROR);
        }
        return userId;
    }


}
