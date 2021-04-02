package com.itts.technologytransactionservice.service.cd.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.JsShMapper;
import com.itts.technologytransactionservice.mapper.JsXqMapper;
import com.itts.technologytransactionservice.model.TJsCg;
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

import static com.itts.common.enums.ErrorCodeEnum.ISSUE_BATCH_FAIL;

/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术需求业务逻辑
 */

@Service
@Primary
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class JsXqAdminServiceImpl extends ServiceImpl<JsXqMapper, TJsXq> implements JsXqAdminService {
    @Autowired
    private JsXqMapper jsXqMapper;
    @Autowired
    private JsShAdminService jsShAdminService;
    @Autowired
    private JsShMapper jsShMapper;



    /**
     * 分页查询需求(后台管理)
     * @param params
     * @return
     */
    @Override
    public PageInfo<TJsXq> findJsXq(Map<String, Object> params) {
        log.info("【技术交易 - 分页查询需求(后台审批管理)】");
        //TODO 从ThreadLocal中获取用户id 暂时是假数据,1表示管理员
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
        //TODO 从ThreadLocal中取userId,暂时是假数据,管理员id为1
        tJsXq.setUserId(1);
        tJsXq.setReleaseType("技术需求");
        tJsXq.setCjsj(new Date());
        log.info("【技术交易 - 新增需求信息:{}】", tJsXq);
        save(tJsXq);
        TJsSh tJsSh = new TJsSh();
        tJsSh.setLx(2);
        tJsSh.setFbshzt(1);
        tJsSh.setXqId(tJsXq.getId());
        tJsSh.setCjsj(new Date());
        jsShAdminService.save(tJsSh);
        return true;
    }

    /**
     * 更新需求信息
     * @param tJsXq
     * @return
     */
    @Override
    public void updateTJsXq(TJsXq tJsXq) {
        log.info("【技术交易 - 更新需求信息:{}】", tJsXq);
        jsXqMapper.updateTJsXq(tJsXq);
    }

    /**
     * 根据需求id删除需求信息
     * @param id
     * @return
     */
    @Override
    public boolean removeByXqId(Integer id) {
        log.info("【技术交易 - 根据id:{}删除需求信息】",id);
        TJsSh tJsSh = jsShMapper.selectByXqId(id);
        TJsXq tJsXq = new TJsXq();
        tJsXq.setId(id);
        tJsXq.setIsDelete(1);
        jsXqMapper.updateById(tJsXq);
        if (tJsSh.getId() != null) {
            tJsSh.setIsDelete(1);
            if (!jsShAdminService.updateById(tJsSh)) {
                throw new ServiceException("删除成果信息失败!");
            }
        }
        return true;
    }

    /**
     * 根据id批量发布需求
     * @param ids
     * @return
     */
    @Override
    public boolean issueBatch(List<Integer> ids) {
        log.info("【技术交易 - 根据id:{}批量发布需求】",ids);
        List<TJsSh> tJsShes = jsShMapper.selectByXqIds(ids);
        for (TJsSh tJsSh : tJsShes) {
            if (tJsSh.getFbshzt() == 1) {
                tJsSh.setFbshzt(2);
                tJsSh.setReleaseStatus(2);
            }
        }
        if (!jsShAdminService.updateBatchById(tJsShes)) {
            log.error("【技术交易 - 批量发布需求失败!】");
            return false;
        }
        return true;
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




}
