package com.itts.technologytransactionservice.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.TJsShMapper;
import com.itts.technologytransactionservice.mapper.TJsXqMapper;
import com.itts.technologytransactionservice.model.TJsFb;
import com.itts.technologytransactionservice.model.TJsSh;
import com.itts.technologytransactionservice.model.TJsXq;
import com.itts.technologytransactionservice.service.ITJsShService;
import com.itts.technologytransactionservice.service.ITJsXqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
@Primary
public class TJsXqServiceImpl extends ServiceImpl<TJsXqMapper, TJsXq> implements ITJsXqService {
    @Autowired
    private TJsXqMapper tJsXqMapper;
    @Autowired
    private ITJsShService tJsShService;
    @Autowired
    private TJsShMapper tJsShMapper;

    @Override
    public PageInfo FindTJsXqByTJsLbTJsLy(Query query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<TJsXq> list = tJsXqMapper.FindTJsXqByTJsLbTJsLy(query);
        PageInfo<TJsXq> page = new PageInfo<>(list);
        return page;
    }

    @Override
    public PageInfo PageByTJsFb(Query query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<TJsFb> list = tJsXqMapper.PageByTJsFb(query);
        PageInfo<TJsFb> page = new PageInfo<>(list);
        return page;
    }

    @Override
    public TJsXq selectByName(String name) {
        return tJsXqMapper.selectByName(name);
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
            tJsSh.setLx("2");
            tJsSh.setCgxqId(tJsXq.getId());
            tJsSh.setFbshzt("1");
            tJsSh.setReleaseStatus("1");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String format = simpleDateFormat.format(date);
            tJsSh.setCjsj(format);
            tJsShService.save(tJsSh);
            return true;
        }

    }

    @Override
    public boolean removeByIdXq(Integer id) {
        TJsSh tJsSh = tJsShService.selectBycgxqId(id);
        TJsXq tJsXq = new TJsXq();
        tJsXq.setId(id);
        tJsXq.setIsDelete(1);
        tJsXqMapper.updateById(tJsXq);
        if (tJsSh.getId() != null) {
            tJsSh.setIsDelete(1);
            tJsShMapper.updateById(tJsSh);
        }
        return true;
    }

    @Override
    public boolean passUpdateById(Integer id) {
        TJsSh tJsSh = tJsShService.selectBycgxqId(id);
        String fbshzt = tJsSh.getFbshzt();
        if (!"2".equals(fbshzt)) {
            tJsSh.setFbshzt("2");
            tJsShService.saveOrUpdate(tJsSh);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean assistancePassUpdateById(Integer id) {
        TJsSh tJsSh = tJsShService.selectBycgxqId(id);
        String assistanceStatus = tJsSh.getAssistanceStatus();
        if (!"2".equals(assistanceStatus)) {
            tJsSh.setAssistanceStatus("2");
            tJsShService.saveOrUpdate(tJsSh);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean assistanceDisPassById(Map<String, Object> params) {
        String id = params.get("id").toString();
        String assistanceRemark = params.get("assistanceRemark").toString();
        TJsSh tJsSh = tJsShService.selectBycgxqId(Integer.valueOf(id));
        String assistanceStatus = tJsSh.getAssistanceStatus();
        if (!"2".equals(assistanceStatus)) {
            tJsSh.setAssistanceStatus("3");
            tJsSh.setAssistanceRemark(assistanceRemark);
            tJsShService.saveOrUpdate(tJsSh);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean disPassById(Map<String, Object> params) {
        String id = params.get("id").toString();
        String fbwtgsm = params.get("fbwtgsm").toString();
        TJsSh tJsSh = tJsShService.selectBycgxqId(Integer.valueOf(id));
        String fbshzt = tJsSh.getFbshzt();
        if (!"2".equals(fbshzt)) {
            tJsSh.setFbshzt("3");
            tJsSh.setFbwtgsm(fbwtgsm);
            tJsShService.saveOrUpdate(tJsSh);
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
            List<TJsSh> tJsShes = tJsShService.selectBycgxqIds(ids);
            for (TJsSh tJsShe : tJsShes) {
                if ("2".equals(tJsShe.getFbshzt())) {
                    tJsShe.setReleaseStatus("2");
                }
            }
            tJsShService.updateBatchById(tJsShes);
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
            List<TJsSh> tJsShes = tJsShService.selectBycgxqIds(ids);
            for (TJsSh tJsShe : tJsShes) {
                tJsShe.setReleaseAssistanceStatus("2");
            }
            tJsShService.updateBatchById(tJsShes);
            return true;
        }

    }

    @Override
    public boolean updateTJsXq(TJsXq tJsXq) {
        tJsXqMapper.updateTJsXq(tJsXq);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean assistanceUpdateTJsXq(TJsXq tJsXq) {
        TJsSh tJsSh = tJsShService.selectBycgxqId(tJsXq.getId());
        if (!"2".equals(tJsSh.getReleaseStatus())) {
            throw new ServiceException("未发布的需求无法申请挂牌");
        } else {
            tJsSh.setAssistanceStatus("1");
            tJsSh.setReleaseAssistanceStatus("1");
            tJsShService.updateById(tJsSh);
            tJsXqMapper.updateTJsXq(tJsXq);
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
        TJsXq tJsXq = tJsXqMapper.findById(id);
        return tJsXq;
    }


}
