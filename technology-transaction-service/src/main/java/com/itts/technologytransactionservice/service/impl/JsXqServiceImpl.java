package com.itts.technologytransactionservice.service.impl;

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
import com.itts.technologytransactionservice.service.JsShService;
import com.itts.technologytransactionservice.service.JsXqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.itts.common.enums.ErrorCodeEnum.AUDIT_STATUS_FAIL;


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
            //TODO 从ThreadLocal中取userId,暂时是假数据,用户id为2
            tJsXq.setUserId(2);
            tJsXq.setReleaseType("技术需求");
            tJsXq.setCjsj(new Date());
            save(tJsXq);
            TJsSh tJsSh = new TJsSh();
            tJsSh.setLx(2);
            tJsSh.setCjsj(new Date());
            tJsSh.setXqId(tJsXq.getId());
            jsShService.save(tJsSh);
            return true;
        }

    }

    /**
     * 根据需求id删除需求信息
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

    @Override
    public boolean assistanceUpdateTJsXq(TJsXq tJsXq) {
        TJsSh tJsSh = jsShService.selectByXqId(tJsXq.getId());
        if (tJsSh.getFbshzt() != 2) {
            return false;
        }
        tJsXq.setGxsj(new Date());
        jsXqMapper.updateTJsXq(tJsXq);
        tJsSh.setAssistanceStatus(1);
        //tJsSh.setReleaseAssistanceStatus(1);
        jsShService.updateById(tJsSh);
        return true;
    }

}
