package com.itts.technologytransactionservice.service.cd.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.JsShMapper;
import com.itts.technologytransactionservice.mapper.JsXqMapper;
import com.itts.technologytransactionservice.model.TJsCg;
import com.itts.technologytransactionservice.model.TJsFb;
import com.itts.technologytransactionservice.model.TJsSh;
import com.itts.technologytransactionservice.model.TJsXq;
import com.itts.technologytransactionservice.service.JsXtxxService;
import com.itts.technologytransactionservice.service.cd.JsShAdminService;
import com.itts.technologytransactionservice.service.cd.JsXqAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;
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
    @Autowired
    private JsXtxxService jsXtxxService;


    /**
     * 分页查询需求(后台管理)
     *
     * @param params
     * @return
     */
    @Override
    public PageInfo<TJsXq> findJsXq(Map<String, Object> params) {
        log.info("【技术交易 - 分页查询需求(后台审批管理)】");
        //TODO 从ThreadLocal中获取用户id 暂时是假数据,1表示管理员
        //params.put("userId", Integer.parseInt(String.valueOf(getUserId())));
        Query query = new Query(params);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<TJsXq> list = jsXqMapper.findJsXq(query);
        return new PageInfo<>(list);
    }
    /**
     * 分页查询成果(后台管理归档清算用)
     * @param params
     * @return
     */
    @Override
    public PageInfo<TJsXq> findGdJsXq(Map<String, Object> params) {
        log.info("【技术交易 - 分页查询成果(后台审批管理)】");
        //前端传输标识type(0：审批管理;1：信息采集)
        params.put("userId", Integer.parseInt(String.valueOf(getUserId())));
        Query query = new Query(params);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<TJsXq> list = jsXqMapper.findGdJsXq(query);
        return new PageInfo<>(list);
    }

    /**
     * 根据需求id查询详细信息
     *
     * @param id
     * @return
     */
    @Override
    public TJsXq getById(Integer id) {
        log.info("【技术交易 - 根据需求id:{}查询详细信息】", id);
        return jsXqMapper.getById(id);
    }

    /**
     * 根据需求名称查询需求详细信息
     *
     * @param name
     * @return
     */
    @Override
    public TJsXq selectByName(String name) {
        log.info("【技术交易 - 根据需求名称:{}查询详细信息】", name);
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
     *
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
        tJsXq.setUserId(Integer.parseInt(String.valueOf(getUserId())));
        tJsXq.setReleaseType("技术需求");
        tJsXq.setCjsj(new Date());
        tJsXq.setGxsj(new Date());
        TJsSh tJsSh = new TJsSh();
        Integer jylx = tJsXq.getJylx();
        if (jylx == null) {
            tJsSh.setFbshzt(1);
        } else {
            tJsSh.setJylx(jylx);
            tJsSh.setReleaseAssistanceStatus(1);
        }
        log.info("【技术交易 - 新增需求信息:{}】", tJsXq);
        tJsXq.setJylx(null);
        save(tJsXq);
        tJsSh.setLx(2);
        tJsSh.setXqId(tJsXq.getId());
        tJsSh.setCjsj(new Date());
        tJsSh.setGxsj(new Date());
        jsShAdminService.save(tJsSh);
        return true;
    }

    /**
     * 更新需求信息
     *
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
     *
     * @param id
     * @return
     */
    @Override
    public boolean removeByXqId(Integer id) {
        log.info("【技术交易 - 根据id:{}删除需求信息】", id);
        TJsSh tJsSh = jsShMapper.selectByXqId(id);
        TJsXq tJsXq = new TJsXq();
        tJsXq.setId(id);
        tJsXq.setIsDelete(2);
        jsXqMapper.updateById(tJsXq);
        if (tJsSh.getId() != null) {
            tJsSh.setIsDelete(2);
            if (!jsShAdminService.updateById(tJsSh)) {
                throw new ServiceException("删除成果信息失败!");
            }
        }
        return true;
    }

    /**
     * 根据id批量发布需求
     *
     * @param ids
     * @return
     */
    @Override
    public boolean issueBatch(List<Integer> ids) {
        log.info("【技术交易 - 根据id:{}批量发布需求】", ids);
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
        //系统消息
        List<TJsXq> tJsXqs=jsXqMapper.selectBatchIds(ids);
        for (TJsXq tJsXq:tJsXqs) {
            jsXtxxService.addXtxx(jsXtxxService.getUserId(),tJsXq.getUserId().longValue(),0,0,tJsXq.getXqmc());
        }
        return true;
    }

    /**
     * 技术招标受理批量下发
     */
    @Override
    public boolean assistanceIssueBatch(List<Integer> ids) {
        log.info("【技术交易 - 技术招标受理批量下发,id:{}】", ids);
        List<TJsSh> tJsShes = jsShMapper.selectByXqIds(ids);
        for (TJsSh tJsShe : tJsShes) {
            tJsShe.setFbshzt(2);
            tJsShe.setReleaseStatus(2);
            tJsShe.setAssistanceStatus(2);
            tJsShe.setReleaseAssistanceStatus(2);
        }
        if (!jsShAdminService.updateBatchById(tJsShes)) {
            return false;
        }
        //系统消息
        List<TJsXq> tJsXqs=jsXqMapper.selectBatchIds(ids);
        for (TJsXq tJsXq:tJsXqs) {
            jsXtxxService.addXtxx(jsXtxxService.getUserId(),tJsXq.getUserId().longValue(),1,0,tJsXq.getXqmc());
        }
        return true;
    }

    @Override
    public boolean xqmove(Integer id, Integer type) {
        Integer index = 0;
        //QueryWrapper<TJsCg> queryWrapper = new QueryWrapper<TJsCg>();
        Map<String, Object> map=new HashMap<>();
        //创建对象
        TJsXq tJsXq = getById(id);
        tJsXq.getSoft();
        if(type == 0){
            map.put("jshdId",tJsXq.getJshdId());
            map.put("soft",tJsXq.getSoft()-1);
            //queryWrapper.eq("jshd_id",tJsCg.getJshdId());
            //queryWrapper.eq("soft",tJsCg.getSoft()-1);
        }
        if(type == 1){
            map.put("jshdId",tJsXq.getJshdId());
            map.put("soft",tJsXq.getSoft()+1);
        }
        List<TJsXq> jsXq = jsXqMapper.findJsXq(map);
        index = jsXq.get(0).getSoft();
        jsXq.get(0).setSoft(tJsXq.getSoft());
        tJsXq.setSoft(index);
        updateTJsXq(jsXq.get(0));
        updateTJsXq(tJsXq);
        return true;
    }

    @Override
    public boolean topBottom(Integer id, Integer type) {
        List<TJsXq> tempList=new ArrayList<TJsXq>();
        TJsXq byId = getById(id);
        Integer soft=byId.getSoft();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("jshdId",byId.getJshdId());
        queryMap.put("sort","soft");
        queryMap.put("order","asc");
        //按soft顺序查询出当前活动对应的所有成果
        List<TJsXq> hdJsXq = jsXqMapper.findHdJsXq(queryMap);
        //置顶
        if (type == 0) {
            int k=0;
            //判断状态为即将开始的成果或需求的第一个技术商品的序号
            for(int j=0;j<hdJsXq.size();j++){
                if(hdJsXq.get(j).getAuctionStatus()==0){
                    k=j;
                }
            }
            //将选中的那项的序号置为即将开始的第一个其他所有在此项之前的即将开始的项目往后移一位
            for(int i=k;i<soft-1;i++){
                hdJsXq.get(i).setSoft(hdJsXq.get(i).getSoft()+1);
                tempList.add(hdJsXq.get(i));
            }
            byId.setSoft(k);
            tempList.add(byId);
        }
        //置底
        if (type == 1) {
            for(int i=byId.getSoft();i<hdJsXq.size();i++){
                hdJsXq.get(i).setSoft(hdJsXq.get(i).getSoft()-1);
                tempList.add(hdJsXq.get(i));
            }
            byId.setSoft(hdJsXq.size());
            tempList.add(byId);
        }
        System.out.println(tempList);
        return updateBatchById(tempList);
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
}
