package com.itts.technologytransactionservice.service.cd.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.JsShMapper;
import com.itts.technologytransactionservice.mapper.JsXqMapper;
import com.itts.technologytransactionservice.model.TJsFb;
import com.itts.technologytransactionservice.model.TJsSh;
import com.itts.technologytransactionservice.model.TJsXq;
import com.itts.technologytransactionservice.service.cd.JsShAdminService;
import com.itts.technologytransactionservice.service.cd.JsXqAdminService;
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
public class JsXqAdminServiceImpl extends ServiceImpl<JsXqMapper, TJsXq> implements JsXqAdminService {
    @Autowired
    private JsXqMapper jsXqMapper;
    @Autowired
    private JsShAdminService JsShAdminService;
    @Autowired
    private JsShMapper jsShMapper;

    @Override
    public PageInfo FindTJsXqByTJsLbTJsLy(Query query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<TJsXq> list = jsXqMapper.FindTJsXqByTJsLbTJsLy(query);
        PageInfo<TJsXq> page = new PageInfo<>(list);
        return page;
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
        return jsXqMapper.selectByName(name);
    }

    @Override
    public boolean saveXq(TJsXq tJsXq) throws Exception {
        if (tJsXq.getId() != null) {
            return false;
        } else {
            TJsXq tJsXq3 = selectByName(tJsXq.getXqmc());
            if (tJsXq3 != null) {
                return false;
            }
            tJsXq.setReleaseType("技术需求");
            save(tJsXq);
            TJsSh tJsSh = new TJsSh();
            tJsSh.setLx(2);
            tJsSh.setXqId(tJsXq.getId());
            tJsSh.setCjsj(new Date());
            JsShAdminService.save(tJsSh);
            return true;
        }

    }

    @Override
    public boolean removeByIdXq(Integer id) {
        Integer lx = 2;
        TJsSh tJsSh = JsShAdminService.selectBycgxqId(id,lx);
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
    public boolean passUpdateById(Integer id) {
        Integer lx = 2;
        TJsSh tJsSh = JsShAdminService.selectBycgxqId(id,lx);
        Integer fbshzt = tJsSh.getFbshzt();
        if (fbshzt != 2) {
            tJsSh.setFbshzt(2);
            JsShAdminService.saveOrUpdate(tJsSh);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean assistancePassUpdateById(Integer id) {
        Integer lx = 2;
        TJsSh tJsSh = JsShAdminService.selectBycgxqId(id,lx);
        Integer assistanceStatus = tJsSh.getAssistanceStatus();
        if (assistanceStatus != 2) {
            tJsSh.setAssistanceStatus(2);
            JsShAdminService.saveOrUpdate(tJsSh);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean assistanceDisPassById(Map<String, Object> params) {
        String id = params.get("id").toString();
        Integer lx = 2;
        String assistanceRemark = params.get("assistanceRemark").toString();
        TJsSh tJsSh = JsShAdminService.selectBycgxqId(Integer.valueOf(id),lx);
        Integer assistanceStatus = tJsSh.getAssistanceStatus();
        if (assistanceStatus != 2) {
            tJsSh.setAssistanceStatus(3);
            tJsSh.setAssistanceRemark(assistanceRemark);
            JsShAdminService.saveOrUpdate(tJsSh);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean disPassById(Map<String, Object> params) {
        String id = params.get("id").toString();
        Integer lx = 2;
        String fbwtgsm = params.get("fbwtgsm").toString();
        TJsSh tJsSh = JsShAdminService.selectBycgxqId(Integer.valueOf(id),lx);
        Integer fbshzt = tJsSh.getFbshzt();
        if (fbshzt != 2) {
            tJsSh.setFbshzt(3);
            tJsSh.setFbwtgsm(fbwtgsm);
            JsShAdminService.saveOrUpdate(tJsSh);
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
            List<TJsSh> tJsShes = JsShAdminService.selectBycgxqIds(ids);
            for (TJsSh tJsShe : tJsShes) {
                if (tJsShe.getFbshzt() == 2) {
                    tJsShe.setReleaseStatus(2);
                }
            }
            JsShAdminService.updateBatchById(tJsShes);
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
            List<TJsSh> tJsShes = JsShAdminService.selectBycgxqIds(ids);
            for (TJsSh tJsShe : tJsShes) {
                tJsShe.setReleaseAssistanceStatus(2);
            }
            JsShAdminService.updateBatchById(tJsShes);
            return true;
        }

    }

    @Override
    public boolean updateTJsXq(TJsXq tJsXq) {
        jsXqMapper.updateTJsXq(tJsXq);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean assistanceUpdateTJsXq(TJsXq tJsXq) {
        Integer lx = 2;
        TJsSh tJsSh = JsShAdminService.selectBycgxqId(tJsXq.getId(),lx);
        if (!"2".equals(tJsSh.getReleaseStatus())) {
            throw new ServiceException("未发布的需求无法申请挂牌");
        } else {
            tJsSh.setAssistanceStatus(1);
            tJsSh.setReleaseAssistanceStatus(1);
            JsShAdminService.updateById(tJsSh);
            jsXqMapper.updateTJsXq(tJsXq);
        }
        return true;
    }

    /**
     * 根据id查询技术需求
     * @param id
     * @return
     */
    @Override
    public TJsXq selectById(Integer id) {
        TJsXq tJsXq = jsXqMapper.findById(id);
        return tJsXq;
    }


}
