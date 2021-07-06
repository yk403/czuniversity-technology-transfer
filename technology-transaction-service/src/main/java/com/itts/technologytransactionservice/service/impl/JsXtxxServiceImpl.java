package com.itts.technologytransactionservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.feign.userservice.UserInfoService;
import com.itts.technologytransactionservice.mapper.JsXtxxMapper;
import com.itts.technologytransactionservice.mapper.LyBmMapper;
import com.itts.technologytransactionservice.model.JsXtxx;
import com.itts.technologytransactionservice.model.LyBm;
import com.itts.technologytransactionservice.model.LyBmDto;
import com.itts.technologytransactionservice.service.JsXtxxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;

/*
 *@Auther: yukai
 *@Date: 2021/07/06/9:49
 *@Desription:
 */
@Service
@Primary
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class JsXtxxServiceImpl extends ServiceImpl<JsXtxxMapper, JsXtxx> implements JsXtxxService {
    @Autowired
    private JsXtxxMapper jsXtxxMapper;
    @Autowired
    private UserInfoService userInfoService;
    @Override
    public PageInfo findJsXtxx(Map<String, Object> params){
        log.info("【技术交易 - 分页条件查询(前台)】");
        Query query = new Query(params);
        query.put("userId",getUserId());
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<JsXtxx> list = jsXtxxMapper.findJsXtxx(query);
        return new PageInfo<>(list);
    }
    /**
     * @Description: 发送人员id,接收人员id，模块类型(0:技术交易,1:双创路演)，所属模块(信息采集，报名，招标发布，挂牌发布等)，载体类型(如技术成果，技术需求，技术展品)，操作内容（如发布成功，发布失败，审核成功等）
     * @Param: [sendId, receiveId, mklx, ssmk, ztlx]
     * @return: void
     * @Author: yukai
     * @Date: 2021/7/6
     */
    //添加系统消息
    @Override
    public void addXtxx(Long sendId, Long receiveId, Integer mklx, String ssmk, String ztlx, String cznr) {
        JsXtxx jsXtxx=new JsXtxx();
        jsXtxx.setSendId(sendId);
        jsXtxx.setReceiveId(receiveId);
        jsXtxx.setMklx(mklx);
        jsXtxx.setSsmk(ssmk);
        jsXtxx.setZtlx(ztlx);
        jsXtxx.setCznr(cznr);
        //拼接消息模板
        if(mklx == 0){
            jsXtxx.setXxnr("技术交易"+ssmk+ztlx+cznr);
        }
        if(mklx == 1){
            jsXtxx.setXxnr("双创路演"+ssmk+ztlx+cznr);
        }
        save(jsXtxx);
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
            return null;
        }
        return userId;
    }
}
