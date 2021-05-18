package com.itts.technologytransactionservice.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.LyLyMapper;
import com.itts.technologytransactionservice.model.LyLy;
import com.itts.technologytransactionservice.model.LyZw;
import com.itts.technologytransactionservice.mapper.LyZwMapper;
import com.itts.technologytransactionservice.service.LyZwService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class LyZwServiceImpl extends ServiceImpl<LyZwMapper, LyZw> implements LyZwService {
    @Autowired
    private LyZwMapper lyZwMapper;
    @Override
    public PageInfo findLyZwFront(Map<String, Object> params) {
        log.info("【技术交易 - 分页条件查询(前台)】");
        Query query = new Query(params);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<LyZw> list = lyZwMapper.findLyZwFront(query);
        return new PageInfo<>(list);
    }

    @Override
    public Boolean saveZw(LyZw lyZw) {
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
}
