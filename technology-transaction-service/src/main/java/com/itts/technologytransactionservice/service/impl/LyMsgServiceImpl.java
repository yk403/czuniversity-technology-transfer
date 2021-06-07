package com.itts.technologytransactionservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.LyMsgMapper;
import com.itts.technologytransactionservice.mapper.LyRyMapper;
import com.itts.technologytransactionservice.model.LyMsg;
import com.itts.technologytransactionservice.model.LyRy;
import com.itts.technologytransactionservice.service.LyMsgService;
import com.itts.technologytransactionservice.service.LyRyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<LyMsg> list = lyMsgMapper.findLyMsgFront(query);
        return new PageInfo<>(list);
    }

    @Override
    public Boolean saveMsg(LyMsg lyMsg) {
        if(save(lyMsg)){
            return true;
        }else{
            return false;
        }
    }
}
