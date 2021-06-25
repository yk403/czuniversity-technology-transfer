package com.itts.technologytransactionservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.LyMsgMapper;
import com.itts.technologytransactionservice.mapper.LyRyMapper;
import com.itts.technologytransactionservice.model.LyMsg;
import com.itts.technologytransactionservice.model.LyMsgDto;
import com.itts.technologytransactionservice.model.LyRy;
import com.itts.technologytransactionservice.service.LyMsgService;
import com.itts.technologytransactionservice.service.LyRyService;
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
public class LyMsgServiceImpl extends ServiceImpl<LyMsgMapper, LyMsg> implements LyMsgService {
    @Autowired
    private LyMsgMapper lyMsgMapper;
    @Override
    public PageInfo findLyMsgFront(Map<String, Object> params) {
        log.info("【技术交易 - 分页条件查询(前台)】");
        Query query = new Query(params);
        query.put("yhId",getUserId());
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<LyMsgDto> list = lyMsgMapper.findLyMsgFront(query);
        return new PageInfo<>(list);
    }

    @Override
    public Boolean saveMsg(LyMsg lyMsg) {
        lyMsg.setYhId(getUserId());
        lyMsg.setLysj(new Date());
        if(save(lyMsg)){
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
            throw new ServiceException(GET_THREADLOCAL_ERROR);
        }
        return userId;
    }
}
