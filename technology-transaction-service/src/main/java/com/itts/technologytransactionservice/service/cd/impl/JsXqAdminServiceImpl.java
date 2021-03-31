package com.itts.technologytransactionservice.service.cd.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.JsShMapper;
import com.itts.technologytransactionservice.mapper.JsXqMapper;
import com.itts.technologytransactionservice.model.TJsFb;
import com.itts.technologytransactionservice.model.TJsSh;
import com.itts.technologytransactionservice.model.TJsXq;
import com.itts.technologytransactionservice.service.cd.JsShAdminService;
import com.itts.technologytransactionservice.service.cd.JsXqAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术需求业务逻辑
 */

@Service
@Primary
@Slf4j
@Transactional
public class JsXqAdminServiceImpl extends ServiceImpl<JsXqMapper, TJsXq> implements JsXqAdminService {
    @Autowired
    private JsXqMapper jsXqMapper;
    @Autowired
    private JsShAdminService jsShAdminService;
    @Autowired
    private JsShMapper jsShMapper;



    /**
     * 分页查询需求(后台审批管理(用户录入信息))
     * @param params
     * @return
     */
    @Override
    public PageInfo<TJsXq> findJsXq(Map<String, Object> params) {
        log.info("【技术交易 - 分页查询需求(后台审批管理)】");
        //TODO 从ThreadLocal中获取用户id 暂时是假数据
        params.put("userId",1);
        Query query = new Query(params);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<TJsXq> list = jsXqMapper.findJsCg(query);
        return new PageInfo<>(list);
    }

    /**
     * 根据需求id查询详细信息
     * @param id
     * @return
     */
    @Override
    public TJsXq getById(Integer id) {
        log.info("【技术交易 - 根据需求id:{}查询详细信息】",id);
        return jsXqMapper.getById(id);
    }

    /**
     * 根据需求名称查询需求详细信息
     * @param name
     * @return
     */
    @Override
    public TJsXq selectByName(String name) {
        log.info("【技术交易 - 根据需求名称:{}查询详细信息】",name);
        return jsXqMapper.selectByName(name);
    }

    @Override
    public PageInfo PageByTJsFb(Query query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<TJsFb> list = jsXqMapper.PageByTJsFb(query);
        PageInfo<TJsFb> page = new PageInfo<>(list);
        return page;
    }

    /**
     * 新增需求信息
     * @param tJsXq
     * @return
     */
    @Override
    public boolean saveXq(TJsXq tJsXq) {
        TJsXq tJsXq2 = selectByName(tJsXq.getXqmc());
        if (tJsXq2 != null) {
            return false;
        }
        tJsXq.setReleaseType("技术需求");
        tJsXq.setCjsj(new Date());
        log.info("【技术交易 - 新增需求信息:{}】", tJsXq);
        save(tJsXq);
        TJsSh tJsSh = new TJsSh();
        tJsSh.setLx(2);
        tJsSh.setXqId(tJsXq.getId());
        tJsSh.setCjsj(new Date());
        jsShAdminService.save(tJsSh);
        return true;
    }


    /**
     * 技术采集批量下发
     */
    @Override
    public boolean issueBatch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return false;
        } else {
            List<TJsSh> tJsShes = jsShAdminService.selectBycgxqIds(ids);
            for (TJsSh tJsShe : tJsShes) {
                if (tJsShe.getFbshzt() == 2) {
                    tJsShe.setReleaseStatus(2);
                }
            }
            jsShAdminService.updateBatchById(tJsShes);
            return true;
        }
    }

    /**
     * 技术转让受理批量下发
     */
    @Override
    public boolean assistanceIssueBatch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return false;
        } else {
            List<TJsSh> tJsShes = jsShAdminService.selectBycgxqIds(ids);
            for (TJsSh tJsShe : tJsShes) {
                tJsShe.setReleaseAssistanceStatus(2);
            }
            jsShAdminService.updateBatchById(tJsShes);
            return true;
        }

    }

    @Override
    public boolean updateTJsXq(TJsXq tJsXq) {
        jsXqMapper.updateTJsXq(tJsXq);
        return true;
    }





}
