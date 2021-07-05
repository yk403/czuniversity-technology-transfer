package com.itts.technologytransactionservice.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.LyHdMapper;
import com.itts.technologytransactionservice.model.LyHd;
import com.itts.technologytransactionservice.model.LyHdDto;
import com.itts.technologytransactionservice.model.TJsHd;
import com.itts.technologytransactionservice.service.LyHdService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
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
public class LyHdServiceImpl extends ServiceImpl<LyHdMapper, LyHd> implements LyHdService {
    @Autowired
    private LyHdMapper lyHdMapper;
    @Override
    public PageInfo findLyHdFront(Map<String, Object> params) {
        log.info("【技术交易 - 分页条件查询(前台)】");
        Query query = new Query(params);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<LyHdDto> list = lyHdMapper.findLyHd(query);
        if(getUserId()!=null){
            HashMap<String, Object> userMap = new HashMap<>();
            //门户报名暂定为userId为2
            userMap.put("userId",getUserId());
            List<LyHdDto> list1 = lyHdMapper.findLyHdFront(userMap);
            for (LyHdDto item:list1) {
                for (LyHdDto item2:list) {
                    //判断是否已报过名，报过isBm为1，未报过为0
                    if(item.getId().equals(item2.getId())){
                        item2.setIsBm(1);
                    }else{
                        if(item2.getIsBm()!=null) {
                            if (item2.getIsBm() == 1) {

                            } else {
                                item2.setIsBm(0);
                            }
                        }else{
                            item2.setIsBm(0);
                        }

                    }

                }
            }
            for (LyHdDto item:list) {
                //判断是否已报过名，报过isBm为1，未报过为0
                if(item.getIsBm()==null){
                    item.setIsBm(0);
                }

            }
        }
        return new PageInfo<>(list);
    }
    @Override
    public PageInfo findLyHdFrontUser(Map<String, Object> params) {
        log.info("【技术交易 - 分页条件查询(前台)】");
        Query query = new Query(params);
        query.put("userId",getUserId());
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<LyHdDto> list = lyHdMapper.findLyHdFrontUser(query);
        return new PageInfo<>(list);
    }

    @Override
    public Boolean saveHd(LyHd lyHd) {
        if(save(lyHd)){
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
            userId = null;
        }
        return userId;
    }
}
