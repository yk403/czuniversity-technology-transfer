package com.itts.technologytransactionservice.service.cd.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.LyZpMapper;
import com.itts.technologytransactionservice.model.LyZp;
import com.itts.technologytransactionservice.model.LyZpDto;
import com.itts.technologytransactionservice.model.TJsSh;
import com.itts.technologytransactionservice.service.JsXtxxService;
import com.itts.technologytransactionservice.service.LyZpService;
import com.itts.technologytransactionservice.service.cd.LyZpAdminService;
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
public class LyZpAdminServiceImpl extends ServiceImpl<LyZpMapper, LyZp> implements LyZpAdminService {
    @Autowired
    private LyZpMapper lyZpMapper;
    @Autowired
    private JsXtxxService jsXtxxService;
    @Override
    public PageInfo findLyZpBack(Map<String, Object> params) {
        log.info("【技术交易 - 分页条件查询(前台)】");
        Query query = new Query(params);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<LyZpDto> list = lyZpMapper.findLyZpBack(query);
        return new PageInfo<>(list);
    }

    @Override
    public Boolean saveZp(LyZp lyZp) {
        if(save(lyZp)){
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
     * 发布审核需求(0待提交;1待审核;2通过;3整改;4拒绝)
     *
     * @param params
     * @param fbshzt
     * @return
     */
    @Override
    public Boolean audit(Map<String, Object> params, Integer fbshzt) {
        //TJsSh tJsSh = jsShMapper.selectByXqId(Integer.parseInt(params.get("id").toString()));
        LyZp lyZp = lyZpMapper.selectById(Long.valueOf(params.get("id").toString()));
        Object fbshbz = params.get("fbshbz");
        if (fbshzt != 2 && fbshbz == null) {
            return false;
        }
        if (fbshbz != null) {
            lyZp.setFbshbz(params.get("fbshbz").toString());
        }
        lyZp.setFbshzt(fbshzt);
        if (!updateById(lyZp)) {
            throw new ServiceException("审核操作失败!");
        }
        if(lyZp.getFbshzt().equals(2)){
            jsXtxxService.addXtxx(jsXtxxService.getUserId(),lyZp.getUserId().longValue(),4,0,lyZp.getZpmc());
        }
        if(lyZp.getFbshzt().equals(4)){
            jsXtxxService.addXtxx(jsXtxxService.getUserId(),lyZp.getUserId().longValue(),4,1,lyZp.getZpmc());
        }
        return true;
    }
}
