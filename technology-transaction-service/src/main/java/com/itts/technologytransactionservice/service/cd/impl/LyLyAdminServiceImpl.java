package com.itts.technologytransactionservice.service.cd.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.LyLyMapper;
import com.itts.technologytransactionservice.model.LyHd;
import com.itts.technologytransactionservice.model.LyLy;
import com.itts.technologytransactionservice.service.LyLyService;
import com.itts.technologytransactionservice.service.cd.LyLyAdminService;
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
public class LyLyAdminServiceImpl extends ServiceImpl<LyLyMapper, LyLy> implements LyLyAdminService {
    @Autowired
    private LyLyMapper lyLyMapper;
    @Override
    public PageInfo findLyLyBack(Map<String, Object> params) {
        log.info("【技术交易 - 分页条件查询(前台)】");
        Query query = new Query(params);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<LyLy> list = lyLyMapper.findLyLyBack(query);
        return new PageInfo<>(list);
    }

    @Override
    public Boolean saveLy(LyLy lyLy) {
        if(save(lyLy)){
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
    @Override
    public boolean issueBatch(List<Long> ids) {
        log.info("【人才培养 - 培养计划批量下发,ids:{}】",ids);
        List<LyLy> lylys = lyLyMapper.selectBatchIds(ids);
        for (LyLy lyly : lylys) {
            lyly.setFbzt(1);
        }
        return updateBatchById(lylys);
    }
}
