package com.itts.technologytransactionservice.service.cd.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.JsCgMapper;
import com.itts.technologytransactionservice.mapper.JsShMapper;
import com.itts.technologytransactionservice.mapper.JsXqMapper;
import com.itts.technologytransactionservice.model.TJsCg;
import com.itts.technologytransactionservice.model.TJsSh;
import com.itts.technologytransactionservice.model.TJsXq;
import com.itts.technologytransactionservice.service.JsXtxxService;
import com.itts.technologytransactionservice.service.cd.JsShAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术审核业务逻辑
 */

@Service
@Primary
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class JsShAdminServiceImpl extends ServiceImpl<JsShMapper, TJsSh> implements JsShAdminService {
    @Autowired
    private JsShMapper jsShMapper;
    @Autowired
    private JsShAdminService jsShAdminService;
    @Autowired
    private JsXtxxService jsXtxxService;
    @Autowired
    private JsCgMapper jsCgMapper;
    @Autowired
    private JsXqMapper jsXqMapper;

    @Override
    public IPage page(Query query) {
        Page<TJsSh> p = new Page<>(query.getPageNum(), query.getPageSize());
        List<TJsSh> list = jsShMapper.list(p, query);
        p.setRecords(list);
        return p;
    }

    public List<TJsSh> selectBycgxqIds(List<Integer> cgxqIds) {
        Integer[] objects = cgxqIds.toArray(new Integer[cgxqIds.size()]);
        return jsShMapper.selectBycgxqIds(objects);
    }

    /**
     * 发布审核成果(0待提交;1待审核;2通过;3整改;4拒绝)
     *
     * @param params
     * @param fbshzt
     * @return
     */
    @Override
    public Boolean auditCg(Map<String, Object> params, Integer fbshzt) {
        TJsSh tJsSh = jsShMapper.selectByCgId(Integer.parseInt(params.get("id").toString()));
        Object fbshbz = params.get("fbshbz");
        if (fbshzt != 2 && fbshbz == null) {
            return false;
        }
        if (fbshbz != null) {
            tJsSh.setFbshbz(params.get("fbshbz").toString());
        }
        if (fbshzt == 2) {
            tJsSh.setReleaseStatus(2);
        }
        tJsSh.setFbshzt(fbshzt);
        if (!jsShAdminService.updateById(tJsSh)) {
            throw new ServiceException("发布审核成果操作失败!");
        }
        //系统消息
        if(fbshzt == 2){
            TJsCg cg=jsCgMapper.getById(Integer.parseInt(params.get("id").toString()));
            /**
             * @Description: 发送人员id,接收人员id，模块类型(0:技术交易,1:双创路演)，所属模块(信息采集，报名，招标发布，挂牌发布等)，载体类型(如技术成果，技术需求，技术展品)，操作内容（如发布成功，发布失败，审核成功等）
             * @Param: [sendId, receiveId, mklx, ssmk, ztlx]
             * @return: void
             * @Author: yukai
             * @Date: 2021/7/6
             */
            jsXtxxService.addXtxx(jsXtxxService.getUserId(),cg.getUserId().longValue(),0,"信息采集审核管理","技术成果","审核成功",cg.getCgmc());
        }else if(fbshzt == 4){
            TJsCg cg=jsCgMapper.getById(Integer.parseInt(params.get("id").toString()));
            /**
             * @Description: 发送人员id,接收人员id，模块类型(0:技术交易,1:双创路演)，所属模块(信息采集，报名，招标发布，挂牌发布等)，载体类型(如技术成果，技术需求，技术展品)，操作内容（如发布成功，发布失败，审核成功等）
             * @Param: [sendId, receiveId, mklx, ssmk, ztlx]
             * @return: void
             * @Author: yukai
             * @Date: 2021/7/6
             */
            jsXtxxService.addXtxx(jsXtxxService.getUserId(),cg.getUserId().longValue(),0,"信息采集审核管理","技术成果","审核拒绝",cg.getCgmc());
        }
        return true;
    }

    /**
     * 发布审核需求(0待提交;1待审核;2通过;3整改;4拒绝)
     *
     * @param params
     * @param fbshzt
     * @return
     */
    @Override
    public Boolean auditXq(Map<String, Object> params, Integer fbshzt) {
        TJsSh tJsSh = jsShMapper.selectByXqId(Integer.parseInt(params.get("id").toString()));
        Object fbshbz = params.get("fbshbz");
        if (fbshzt != 2 && fbshbz == null) {
            return false;
        }
        if (fbshbz != null) {
            tJsSh.setFbshbz(params.get("fbshbz").toString());
        }
        if (fbshzt == 2) {
            tJsSh.setReleaseStatus(2);
        }
        tJsSh.setFbshzt(fbshzt);
        if (!jsShAdminService.updateById(tJsSh)) {
            throw new ServiceException("发布审核需求操作失败!");
        }
        //系统消息
        if(fbshzt == 2){
            TJsXq xq=jsXqMapper.getById(Integer.parseInt(params.get("id").toString()));
            /**
             * @Description: 发送人员id,接收人员id，模块类型(0:技术交易,1:双创路演)，所属模块(信息采集，报名，招标发布，挂牌发布等)，载体类型(如技术成果，技术需求，技术展品)，操作内容（如发布成功，发布失败，审核成功等）
             * @Param: [sendId, receiveId, mklx, ssmk, ztlx]
             * @return: void
             * @Author: yukai
             * @Date: 2021/7/6
             */
            jsXtxxService.addXtxx(jsXtxxService.getUserId(),xq.getUserId().longValue(),0,"信息采集审核管理","技术需求","审核成功",xq.getXqmc());
        }else if(fbshzt == 4){
            TJsXq xq=jsXqMapper.getById(Integer.parseInt(params.get("id").toString()));
            /**
             * @Description: 发送人员id,接收人员id，模块类型(0:技术交易,1:双创路演)，所属模块(信息采集，报名，招标发布，挂牌发布等)，载体类型(如技术成果，技术需求，技术展品)，操作内容（如发布成功，发布失败，审核成功等）
             * @Param: [sendId, receiveId, mklx, ssmk, ztlx]
             * @return: void
             * @Author: yukai
             * @Date: 2021/7/6
             */
            jsXtxxService.addXtxx(jsXtxxService.getUserId(),xq.getUserId().longValue(),0,"信息采集审核管理","技术需求","审核拒绝",xq.getXqmc());
        }
        return true;
    }

    @Override
    public Boolean assistanceAuditXq(Map<String, Object> params, Integer assistanceStatus) {
        TJsSh tJsSh = jsShMapper.selectByXqId(Integer.parseInt(params.get("id").toString()));
        Object slxbbz = params.get("slxbbz");
        if (assistanceStatus != 2 && slxbbz == null) {
            return false;
        }
        if (slxbbz != null) {
            tJsSh.setSlxbbz(params.get("slxbbz").toString());
        }
        if (assistanceStatus == 2) {
            tJsSh.setReleaseAssistanceStatus(2);
        }
        tJsSh.setAssistanceStatus(assistanceStatus);
        if (!jsShAdminService.updateById(tJsSh)) {
            throw new ServiceException("发布审核需求操作失败!");
        }
        //系统消息
        if(assistanceStatus == 2){
            TJsXq xq=jsXqMapper.getById(Integer.parseInt(params.get("id").toString()));
            /**
             * @Description: 发送人员id,接收人员id，模块类型(0:技术交易,1:双创路演)，所属模块(信息采集，报名，招标发布，挂牌发布等)，载体类型(如技术成果，技术需求，技术展品)，操作内容（如发布成功，发布失败，审核成功等）
             * @Param: [sendId, receiveId, mklx, ssmk, ztlx]
             * @return: void
             * @Author: yukai
             * @Date: 2021/7/6
             */
            jsXtxxService.addXtxx(jsXtxxService.getUserId(),xq.getUserId().longValue(),0,"技术招标审批管理","技术需求","审核成功",xq.getXqmc());
        }else if(assistanceStatus == 4){
            TJsXq xq=jsXqMapper.getById(Integer.parseInt(params.get("id").toString()));
            /**
             * @Description: 发送人员id,接收人员id，模块类型(0:技术交易,1:双创路演)，所属模块(信息采集，报名，招标发布，挂牌发布等)，载体类型(如技术成果，技术需求，技术展品)，操作内容（如发布成功，发布失败，审核成功等）
             * @Param: [sendId, receiveId, mklx, ssmk, ztlx]
             * @return: void
             * @Author: yukai
             * @Date: 2021/7/6
             */
            jsXtxxService.addXtxx(jsXtxxService.getUserId(),xq.getUserId().longValue(),0,"技术招标审批管理","技术需求","审核拒绝",xq.getXqmc());
        }
        return true;
    }

    @Override
    public Boolean assistanceAuditCg(Map<String, Object> params, Integer assistanceStatus) {
        TJsSh tJsSh = jsShMapper.selectByCgId(Integer.parseInt(params.get("id").toString()));
        Object slxbbz = params.get("slxbbz");
        if (assistanceStatus != 2 && slxbbz == null) {
            return false;
        }
        if (slxbbz != null) {
            tJsSh.setSlxbbz(params.get("slxbbz").toString());
        }
        if (assistanceStatus == 2) {
            tJsSh.setReleaseAssistanceStatus(2);
        }
        tJsSh.setAssistanceStatus(assistanceStatus);
        if (!jsShAdminService.updateById(tJsSh)) {
            throw new ServiceException("发布审核需求操作失败!");
        }
        //系统消息
        if(assistanceStatus == 2){
            TJsCg cg=jsCgMapper.getById(Integer.parseInt(params.get("id").toString()));
            /**
             * @Description: 发送人员id,接收人员id，模块类型(0:技术交易,1:双创路演)，所属模块(信息采集，报名，招标发布，挂牌发布等)，载体类型(如技术成果，技术需求，技术展品)，操作内容（如发布成功，发布失败，审核成功等）
             * @Param: [sendId, receiveId, mklx, ssmk, ztlx]
             * @return: void
             * @Author: yukai
             * @Date: 2021/7/6
             */
            if(tJsSh.getJylx() == 0){
                jsXtxxService.addXtxx(jsXtxxService.getUserId(),cg.getUserId().longValue(),0,"技术拍卖审核管理","技术成果","审核成功",cg.getCgmc());
            }
            if(tJsSh.getJylx() == 2){
                jsXtxxService.addXtxx(jsXtxxService.getUserId(),cg.getUserId().longValue(),0,"技术挂牌审核管理","技术成果","审核成功",cg.getCgmc());
            }
        }else if(assistanceStatus == 4){
            TJsCg cg=jsCgMapper.getById(Integer.parseInt(params.get("id").toString()));
            /**
             * @Description: 发送人员id,接收人员id，模块类型(0:技术交易,1:双创路演)，所属模块(信息采集，报名，招标发布，挂牌发布等)，载体类型(如技术成果，技术需求，技术展品)，操作内容（如发布成功，发布失败，审核成功等）
             * @Param: [sendId, receiveId, mklx, ssmk, ztlx]
             * @return: void
             * @Author: yukai
             * @Date: 2021/7/6
             */
            if(tJsSh.getJylx() == 0){
                jsXtxxService.addXtxx(jsXtxxService.getUserId(),cg.getUserId().longValue(),0,"技术拍卖审核管理","技术成果","审核拒绝",cg.getCgmc());
            }
            if(tJsSh.getJylx() == 2){
                jsXtxxService.addXtxx(jsXtxxService.getUserId(),cg.getUserId().longValue(),0,"技术挂牌审核管理","技术成果","审核拒绝",cg.getCgmc());
            }
        }
        return true;
    }

    /**
     * 根据id批量发布成果
     *
     * @param ids
     * @return
     */
    @Override
    public List<TJsSh> selectByCgIds(List<Integer> ids) {
        List<TJsSh> tJsShes = jsShMapper.selectByCgIds(ids);
        return tJsShes;
    }
    @Override
    public boolean updateByCgId(Integer cgId) {
        jsShMapper.updateByCgId(cgId,1);
        return true;
    }
    @Override
    public boolean updateByXqId(Integer xqId) {
        jsShMapper.updateByXqId(xqId,1);
        return true;
    }

}
