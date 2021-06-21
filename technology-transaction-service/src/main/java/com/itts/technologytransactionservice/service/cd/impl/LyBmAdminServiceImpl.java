package com.itts.technologytransactionservice.service.cd.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.LyBmMapper;
import com.itts.technologytransactionservice.model.LyBm;
import com.itts.technologytransactionservice.model.LyBmDto;
import com.itts.technologytransactionservice.model.TJsXq;
import com.itts.technologytransactionservice.service.LyBmService;
import com.itts.technologytransactionservice.service.cd.LyBmAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
 * @since 2021-05-17
 */
@Service
@Primary
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class LyBmAdminServiceImpl extends ServiceImpl<LyBmMapper, LyBm> implements LyBmAdminService {
    @Autowired
    private LyBmMapper lyBmMapper;
    @Override
    public PageInfo findLyBmBack(Map<String, Object> params) {
       log.info("【技术交易 - 分页条件查询(前台)】");
        Query query = new Query(params);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<LyBmDto> list = lyBmMapper.findLyBmBack(query);
        return new PageInfo<>(list);
    }

    @Override
    public Boolean saveBm(LyBm lyBm) {
        Long userId = getUserId();
        lyBm.setUserId(userId);
        if(save(lyBm)){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 更新报名信息
     *
     * @param
     * @return
     */
    @Override
    public boolean updateLyBm(LyBm lyBm) {
        log.info("【技术交易 - 更新需求信息:{}】", lyBm);
        lyBmMapper.updateLyBm(lyBm);
        return true;
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
