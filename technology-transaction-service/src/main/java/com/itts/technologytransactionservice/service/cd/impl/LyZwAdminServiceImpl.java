package com.itts.technologytransactionservice.service.cd.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.LyZwMapper;
import com.itts.technologytransactionservice.model.LyZw;
import com.itts.technologytransactionservice.model.LyZwDto;
import com.itts.technologytransactionservice.service.LyZwService;
import com.itts.technologytransactionservice.service.cd.LyZwAdminService;
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
 * @since 2021-05-18
 */
@Service
@Primary
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class LyZwAdminServiceImpl extends ServiceImpl<LyZwMapper, LyZw> implements LyZwAdminService {
    @Autowired
    private LyZwMapper lyZwMapper;
    @Override
    public PageInfo findLyZwBack(Map<String, Object> params) {
        log.info("【技术交易 - 分页条件查询(前台)】");
        Query query = new Query(params);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<LyZwDto> list = lyZwMapper.findLyZwBack(query);
        return new PageInfo<>(list);
    }
    @Override
    public PageInfo findLyZwBackSelect(Map<String, Object> params) {
        log.info("【双创路演 - 分页条件查询(前台复选框用)】");
        Query query = new Query(params);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<LyZwDto> list = lyZwMapper.findLyZwBackSelect(query);
        return new PageInfo<>(list);
    }
    @Override
    public Boolean saveZw(LyZw lyZw) {
        lyZw.setFjjgId(getFjjgId());
        if(save(lyZw)){
            return true;
        }else{
            return false;
        }
    }
//    *
//     * 获取当前用户id
//     * @return

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

    /**
     * 获取父级机构ID
     */
    public Long getFjjgId() {
        LoginUser loginUser = threadLocal.get();
        Long fjjgId;
        if (loginUser != null) {
            fjjgId = loginUser.getFjjgId();
        } else {
            throw new ServiceException(GET_THREADLOCAL_ERROR);
        }
        return fjjgId;
    }
}
