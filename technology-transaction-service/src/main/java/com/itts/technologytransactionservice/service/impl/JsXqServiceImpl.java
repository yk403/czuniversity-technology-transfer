package com.itts.technologytransactionservice.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.ServiceException;
import com.itts.common.exception.WebException;
import com.itts.common.utils.Query;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.feign.userservice.JgglFeignService;
import com.itts.technologytransactionservice.mapper.JsShMapper;
import com.itts.technologytransactionservice.mapper.JsXqMapper;
import com.itts.technologytransactionservice.model.*;
import com.itts.technologytransactionservice.service.JsShService;
import com.itts.technologytransactionservice.service.JsXqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;


@Service
@Primary
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class JsXqServiceImpl extends ServiceImpl<JsXqMapper, TJsXq> implements JsXqService {
    @Autowired
    private JsXqMapper jsXqMapper;
    @Autowired
    private JsShService jsShService;
    @Autowired
    private JsShMapper jsShMapper;

    @Resource
    private JgglFeignService jgglFeignService;
    /**
     * 分页条件查询需求(前台)
     *
     * @param params
     * @return
     */
    @Override
    public PageInfo findJsXqFront(Map<String, Object> params) {
        log.info("【技术交易 - 分页条件查询(前台)】");
        Long fjjgId = getFjjgId();
        if(params.get("jgCode") != null){
            ResponseUtil response = jgglFeignService.getByCode(params.get("jgCode").toString());
            if(response == null || response.getErrCode().intValue() != 0){
                throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
            }
            JgglVO jggl = response.conversionData(new TypeReference<JgglVO>() {});
            fjjgId = jggl.getId();
        }
        params.put("fjjgId",fjjgId);
        Query query = new Query(params);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<TJsXq> list = jsXqMapper.findJsXqFront(query);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo PageByTJsFb(Query query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<TJsFb> list = jsXqMapper.PageByTJsFb(query);
        PageInfo<TJsFb> page = new PageInfo<>(list);
        return page;
    }

    @Override
    public TJsXq selectByName(String name) {
        Long fjjgId = getFjjgId();
        return jsXqMapper.selectByName(name,fjjgId);
    }

    @Override
    public boolean saveXq(TJsXq tJsXq) throws Exception {
        Long fjjgId = getFjjgId();
        if (tJsXq.getId() != null) {
            return false;
        } else {
            TJsXq tJsXq3 = selectByName(tJsXq.getXqmc());
            if (tJsXq3 != null) {
                return false;
            }
            //TODO 从ThreadLocal中取userId,暂时是假数据,用户id为2
            tJsXq.setUserId(Integer.parseInt(String.valueOf(getUserId())));
            tJsXq.setReleaseType("技术需求");
            tJsXq.setFjjgId(fjjgId);
            //tJsXq.setCjsj(new Date());
            //tJsXq.setGxsj(new Date());
            save(tJsXq);
            TJsSh tJsSh = new TJsSh();
            tJsSh.setLx(2);
            tJsSh.setCjsj(new Date());
            tJsSh.setGxsj(new Date());
            tJsSh.setXqId(tJsXq.getId());
            jsShService.save(tJsSh);
            return true;
        }

    }

    /**
     * 根据需求id删除需求信息
     *
     * @param id
     * @return
     */
    @Override
    public boolean removeByIdXq(Integer id) {
        TJsSh tJsSh = jsShService.selectByXqId(id);
        TJsXq tJsXq = new TJsXq();
        tJsXq.setId(id);
        tJsXq.setIsDelete(1);
        jsXqMapper.updateById(tJsXq);
        if (tJsSh.getId() != null) {
            tJsSh.setIsDelete(1);
            jsShMapper.updateById(tJsSh);
        }
        return true;
    }

    @Override
    public boolean assistancePassUpdateById(Integer id) {
        Integer lx = 2;
        TJsSh tJsSh = jsShService.selectByXqId(id);
        Integer assistanceStatus = tJsSh.getAssistanceStatus();
        if (!"2".equals(assistanceStatus)) {
            tJsSh.setAssistanceStatus(2);
            jsShService.saveOrUpdate(tJsSh);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean assistanceDisPassById(Map<String, Object> params) {
        String id = params.get("id").toString();
        String assistanceRemark = params.get("assistanceRemark").toString();
        TJsSh tJsSh = jsShService.selectByXqId(Integer.valueOf(id));
        Integer assistanceStatus = tJsSh.getAssistanceStatus();
        if (assistanceStatus != 2) {
            tJsSh.setAssistanceStatus(3);
            tJsSh.setSlxbbz(assistanceRemark);
            jsShService.saveOrUpdate(tJsSh);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 技术采集批量下发
     */
    @Override
    public boolean issueBatch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return false;
        } else {
            List<TJsSh> tJsShes = jsShService.selectByxqIds(ids);
            for (TJsSh tJsShe : tJsShes) {
                if (tJsShe.getFbshzt() == 2) {
                    tJsShe.setReleaseStatus(2);
                }
            }
            jsShService.updateBatchById(tJsShes);
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
            List<TJsSh> tJsShes = jsShService.selectBycgxqIds(ids);
            for (TJsSh tJsShe : tJsShes) {
                tJsShe.setReleaseAssistanceStatus(2);
            }
            jsShService.updateBatchById(tJsShes);
            return true;
        }

    }

    @Override
    public boolean updateTJsXq(TJsXq tJsXq) {
        jsXqMapper.updateTJsXq(tJsXq);
        return true;
    }

    /**
     * 分页条件查询需求(个人详情)
     *
     * @param params
     * @return
     */
    @Override
    public PageInfo<TJsXq> findJsXqUser(Map<String, Object> params) {
        log.info("【技术交易 - 分页查询需求(个人详情)】");
        //TODO 从ThreadLocal中获取用户id 暂时是假数据
        params.put("userId", Integer.parseInt(String.valueOf(getUserId())));
        Long fjjgId = getFjjgId();
        if(params.get("jgCode") != null){
            ResponseUtil response = jgglFeignService.getByCode(params.get("jgCode").toString());
            if(response == null || response.getErrCode().intValue() != 0){
                throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
            }
            JgglVO jggl = response.conversionData(new TypeReference<JgglVO>() {});
            fjjgId = jggl.getId();
        }
        params.put("fjjgId",fjjgId);
        Query query = new Query(params);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<TJsXq> list = jsXqMapper.findJsXqFront(query);
        return new PageInfo<>(list);
    }

    /**
     * 个人发布审核需求申请(0待提交;1待审核;2通过;3整改;4拒绝)
     *
     * @param params
     * @return
     */
    @Override
    public boolean auditXq(Map<String, Object> params, Integer fbshzt) {
        TJsSh tJsSh = jsShMapper.selectByXqId(Integer.parseInt(params.get("id").toString()));
        tJsSh.setFbshzt(fbshzt);
        //如果门户在信息采集的整改中申请发布改为整改完成时将发布审核状态清空
        if(fbshzt == 5){
            tJsSh.setFbshbz("");
        }
        jsShMapper.updateTJsShs(tJsSh);
//        if (!jsShService.updateById(tJsSh)) {
//            throw new ServiceException("发布审核需求申请失败!");
//        }
        return true;
    }

    /**
     * 已发布的需求申请招标(受理协办)
     *
     * @param params
     * @return
     */
    @Override
    public boolean assistanceUpdateTJsXq(Map<String, Object> params, Integer jylx) throws ParseException {
        TJsSh tJsSh = jsShService.selectByXqId(Integer.valueOf(params.get("id").toString()));
        if (tJsSh.getFbshzt() != 2) {
            log.error("发布审核状态未通过,无法申请拍卖挂牌!");
            return false;
        }
        TJsXq xq = new TJsXq();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(params.get("xqxq")!=null){
            xq.setId(Integer.valueOf(params.get("id").toString()));
            xq.setXqxq(params.get("xqxq").toString());
            xq.setJszb(params.get("jszb").toString());
            xq.setRemarks(params.get("remarks").toString());
            xq.setZbxmgs(params.get("zbxmgs").toString());
            xq.setZbxmqd(params.get("zbxmqd").toString());
            if(params.get("xqfj")!=null){
                xq.setXqfj(params.get("xqfj").toString());
            }
            if(params.get("xqfj")!=null){
                xq.setXqfjmc(params.get("xqfjmc").toString());
            }
            xq.setJscs(params.get("jscs").toString());
            xq.setFwnr(params.get("fwnr").toString());
            xq.setJhrq(simpleDateFormat.parse(params.get("jhrq").toString()));
            xq.setYsrq(simpleDateFormat.parse(params.get("ysrq").toString()));

            tJsSh.setAssistanceStatus(1);
            tJsSh.setJylx(jylx);
            tJsSh.setReleaseAssistanceStatus(1);
            jsXqMapper.updateTJsXq(xq);
        }else if(params.get("assistanceStatus")!=null){
            tJsSh.setAssistanceStatus(Integer.parseInt(params.get("assistanceStatus").toString()));
            tJsSh.setJylx(jylx);
            tJsSh.setSlxbbz("");
            tJsSh.setReleaseAssistanceStatus(1);
        }
        jsShMapper.updateTJsShs(tJsSh);
//        if (!jsShService.updateById(tJsSh)) {
//            log.error("更新审核失败!");
//            throw new ServiceException("更新审核失败!");
//        }
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
    private Long getFjjgId(){
        LoginUser loginUser = threadLocal.get();
        Long fjjgId = null;
        if (loginUser != null) {
            fjjgId = loginUser.getFjjgId();
        }
        return fjjgId;
    }
}
