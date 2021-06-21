package com.itts.technologytransactionservice.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.LyZwMapper;
import com.itts.technologytransactionservice.model.*;
import com.itts.technologytransactionservice.mapper.LyZpMapper;
import com.itts.technologytransactionservice.service.LyZpService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yukai
 * @since 2021-05-18
 */
@Service
@Primary
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class LyZpServiceImpl extends ServiceImpl<LyZpMapper, LyZp> implements LyZpService {
    @Autowired
    private LyZpMapper lyZpMapper;
    @Override
    public PageInfo findLyZpFront(Map<String, Object> params) {
        log.info("【技术交易 - 分页条件查询(前台)】");
        Query query = new Query(params);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<LyZpDto> list = lyZpMapper.findLyZpFront(query);
        return new PageInfo<>(list);
    }

    @Override
    public Boolean saveZp(LyZp lyZp) {
        lyZp.setUserId(getUserId());
        if(save(lyZp)){
            return true;
        }else{
            return false;
        }
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
            //throw new ServiceException(GET_THREADLOCAL_ERROR);
            userId=null;
        }
        return userId;
    }
    @Override
    public boolean audit(Map<String, Object> params, Integer fbshzt) {
        //TJsSh tJsSh = jsShMapper.selectByCgId(Integer.parseInt(params.get("id").toString()));
        LyZp id=getById(Long.valueOf(params.get("id").toString()));

        id.setFbshzt(fbshzt);
        //如果门户在信息采集的整改中申请发布改为整改完成时将发布审核状态清空
        if(fbshzt == 5){
            id.setFbshbz("");
        }
        updateById(id);
        return true;
    }
}
