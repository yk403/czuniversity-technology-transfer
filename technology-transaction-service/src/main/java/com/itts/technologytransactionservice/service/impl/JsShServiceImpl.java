package com.itts.technologytransactionservice.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.JsCgMapper;
import com.itts.technologytransactionservice.mapper.JsShMapper;
import com.itts.technologytransactionservice.mapper.JsXqMapper;
import com.itts.technologytransactionservice.model.TJsSh;
import com.itts.technologytransactionservice.service.JsShService;
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
@Transactional
public class JsShServiceImpl extends ServiceImpl<JsShMapper, TJsSh> implements JsShService {

    @Autowired
    private JsShMapper jsShMapper;

    @Autowired
    private JsShService jsShService;

    @Override
    public IPage page(Query query) {
        Page<TJsSh> p = new Page<>(query.getPageNum(), query.getPageSize());
        List<TJsSh> list = jsShMapper.list(p, query);
        p.setRecords(list);
        return p;
    }

    @Override
    public TJsSh selectByCgId(Integer cgId) {
        return jsShMapper.selectByCgId(cgId);
    }

    @Override
    public List<TJsSh> selectBycgxqIds(List<Integer> cgxqIds) {
        Integer[] objects = cgxqIds.toArray(new Integer[cgxqIds.size()]);
        return jsShMapper.selectBycgxqIds(objects);
    }

    @Override
    public boolean deleteById(Integer cgId, Integer xqId) {
        jsShMapper.updateJsSh(cgId, xqId);
        return true;
    }

    @Override
    public TJsSh selectByXqId(Integer xqId) {
        return jsShMapper.selectByXqId(xqId);
    }

    /**
     * 发布审核成果
     *
     * @param params
     * @param fbshzt
     * @return
     */
    @Override
    public Boolean auditCg(Map<String, Object> params, Integer fbshzt) {
        TJsSh tJsSh = jsShMapper.selectByCgId(Integer.parseInt(params.get("id").toString()));
        if(params.containsKey("fbshbz") && params.get("fbshbz") != null){
            tJsSh.setFbshbz(params.get("fbshbz").toString());
        }
        tJsSh.setFbshzt(fbshzt);
        if (!jsShService.updateById(tJsSh)) {
            throw new ServiceException("发布审核成果操作失败!");
        }
        return true;
    }

    /**
     * 发布审核需求
     *
     * @param params
     * @param fbshzt
     * @return
     */
    @Override
    public Boolean auditXq(Map<String, Object> params, Integer fbshzt) {
        TJsSh tJsSh = jsShMapper.selectByXqId(Integer.parseInt(params.get("id").toString()));
        if(params.containsKey("fbshbz") && params.get("fbshbz") != null){
            tJsSh.setFbshbz(params.get("fbshbz").toString());
        }
        tJsSh.setFbshzt(fbshzt);
        if (!jsShService.updateById(tJsSh)) {
            throw new ServiceException("发布审核需求操作失败!");
        }
        return true;
    }

    /**
     * 成果批量下发
     * @param cgIds
     * @return
     */
    @Override
    public List<TJsSh> selectBycgIds(List<Integer> cgIds) {
        Integer[] objects = cgIds.toArray(new Integer[cgIds.size()]);
        return jsShMapper.selectBycgIds(objects);
    }

    /**
     * 需求批量下发
     * @param xqIds
     * @return
     */
    @Override
    public List<TJsSh> selectByxqIds(List<Integer> xqIds) {
        Integer[] objects = xqIds.toArray(new Integer[xqIds.size()]);
        return jsShMapper.selectByxqIds(objects);
    }
}

