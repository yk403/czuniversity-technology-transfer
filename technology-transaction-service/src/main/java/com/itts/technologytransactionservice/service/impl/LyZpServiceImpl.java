package com.itts.technologytransactionservice.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.ServiceException;
import com.itts.common.exception.WebException;
import com.itts.common.utils.Query;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.feign.userservice.JgglFeignService;
import com.itts.technologytransactionservice.mapper.LyZwMapper;
import com.itts.technologytransactionservice.model.*;
import com.itts.technologytransactionservice.mapper.LyZpMapper;
import com.itts.technologytransactionservice.service.JsXtxxService;
import com.itts.technologytransactionservice.service.LyZpService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    @Autowired
    private JsXtxxService jsXtxxService;
    @Resource
    private JgglFeignService jgglFeignService;
    @Override
    public PageInfo findLyZpFront(Map<String, Object> params) {
        log.info("【技术交易 - 分页条件查询(前台)】");
        Long fjjgId = getFjjgId();
        if(params.get("jgCode") != null){
            ResponseUtil response = jgglFeignService.getByCode(params.get("jgCode").toString());
            if(response == null || response.getErrCode().intValue() != 0){
                throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
            }
            JgglVO jggl = response.conversionData(new TypeReference<JgglVO>() {});
            fjjgId = jggl.getId();
        }
        params.put("fjjgId",fjjgId);
        Query query = new Query(params);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<LyZpDto> list = lyZpMapper.findLyZpFront(query);
        return new PageInfo<>(list);
    }
    @Override
    public PageInfo findLyZpFrontUser(Map<String, Object> params) {
        log.info("【技术交易 - 分页条件查询(前台)】");
        Query query = new Query(params);
        query.put("userId",getUserId());
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
        if(fbshzt == 2){
            jsXtxxService.addXtxx(jsXtxxService.getUserId(),id.getUserId() == null ? null:id.getUserId(),4,0,id.getZpmc());
        }
        if(fbshzt == 4){
            jsXtxxService.addXtxx(jsXtxxService.getUserId(),id.getUserId() == null ? null:id.getUserId(),4,1,id.getZpmc());
        }
        return true;
    }
    private Long getFjjgId(){
        LoginUser loginUser = threadLocal.get();
        Long fjjgId = null;
        if (loginUser != null) {
            fjjgId = loginUser.getFjjgId();
        }
        return fjjgId;
    }
}
