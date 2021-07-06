package com.itts.technologytransactionservice.service.cd.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.JsCgMapper;
import com.itts.technologytransactionservice.mapper.JsHdMapper;
import com.itts.technologytransactionservice.mapper.JsShMapper;
import com.itts.technologytransactionservice.model.TJsCg;
import com.itts.technologytransactionservice.model.TJsSh;
import com.itts.technologytransactionservice.service.JsXtxxService;
import com.itts.technologytransactionservice.service.cd.JsCgAdminService;
import com.itts.technologytransactionservice.service.cd.JsShAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;

/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术成果业务逻辑
 */

@Service
@Primary
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class JsCgAdminServiceImpl extends ServiceImpl<JsCgMapper, TJsCg> implements JsCgAdminService {
    @Autowired
    private JsCgMapper jsCgMapper;
    @Autowired
    private JsXtxxService jsXtxxService;
    @Autowired
    private JsShAdminService jsShAdminService;

    @Autowired
    private JsShMapper jsShMapper;
    @Autowired
    private JsHdMapper jsHdMapper;

    /**
     * 分页查询成果(后台管理)
     * @param params
     * @return
     */
    @Override
    public PageInfo<TJsCg> findJsCg(Map<String, Object> params) {
        log.info("【技术交易 - 分页查询成果(后台审批管理)】");
        //前端传输标识type(0：审批管理;1：信息采集)
        //TODO 从ThreadLocal中获取管理员id 暂时是假数据
        //params.put("userId", Integer.parseInt(String.valueOf(getUserId())));
        Query query = new Query(params);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<TJsCg> list = jsCgMapper.findJsCg(query);
        return new PageInfo<>(list);
    }
    /**
     * 分页查询成果(后台管理归档清算用)
     * @param params
     * @return
     */
    @Override
    public PageInfo<TJsCg> findGdJsCg(Map<String, Object> params) {
        log.info("【技术交易 - 分页查询成果(后台审批管理)】");
        //前端传输标识type(0：审批管理;1：信息采集)
        //TODO 从ThreadLocal中获取管理员id 暂时是假数据
        params.put("userId", Integer.parseInt(String.valueOf(getUserId())));
        Query query = new Query(params);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<TJsCg> list = jsCgMapper.findGdJsCg(query);
        return new PageInfo<>(list);
    }

    /**
     * 根据成果id查询详细信息
     *
     * @param id
     * @return
     */
    @Override
    public TJsCg getById(Integer id) {
        log.info("【技术交易 - 根据成果id:{}查询详细信息】", id);
        return jsCgMapper.getById(id);
    }

    /**
     * 成果上移下移 上移type为0 下移type为1
     *
     * @param
     * @return
     */
    @Override
    public boolean cgmove(Integer id,Integer type) {
        Integer index = 0;
        //QueryWrapper<TJsCg> queryWrapper = new QueryWrapper<TJsCg>();
        Map<String, Object> map=new HashMap<>();
        //创建对象
        TJsCg tJsCg = getById(id);
        tJsCg.getSoft();
        if(type == 0){
            map.put("jshdId",tJsCg.getJshdId());
            map.put("soft",tJsCg.getSoft()-1);
            //queryWrapper.eq("jshd_id",tJsCg.getJshdId());
            //queryWrapper.eq("soft",tJsCg.getSoft()-1);
        }
        if(type == 1){
            map.put("jshdId",tJsCg.getJshdId());
            map.put("soft",tJsCg.getSoft()+1);
        }
        List<TJsCg> jsCg = jsCgMapper.findJsCg(map);
        index = jsCg.get(0).getSoft();
        jsCg.get(0).setSoft(tJsCg.getSoft());
        tJsCg.setSoft(index);
        updateTJsCg(jsCg.get(0));
        updateTJsCg(tJsCg);
        return true;
    }

    //置顶置底
    @Override
    public boolean topBottom(Integer id, Integer type) {
        List<TJsCg> tempList=new ArrayList<TJsCg>();
        TJsCg byId = getById(id);
        Integer soft=byId.getSoft();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("jshdId",byId.getJshdId());
        queryMap.put("sort","soft");
        queryMap.put("order","asc");
        //按soft顺序查询出当前活动对应的所有成果
        List<TJsCg> hdJsCg = jsCgMapper.findHdJsCg(queryMap);
        //置顶
        if (type == 0) {
            int k=0;
            //判断状态为即将开始的成果或需求的第一个技术商品的序号
            for(int j=0;j<hdJsCg.size();j++){
                if(hdJsCg.get(j).getAuctionStatus()==0){
                    k=j;
                }
            }
            //将选中的那项的序号置为即将开始的第一个其他所有在此项之前的即将开始的项目往后移一位
            for(int i=k;i<soft-1;i++){
                hdJsCg.get(i).setSoft(hdJsCg.get(i).getSoft()+1);
                tempList.add(hdJsCg.get(i));
            }
            byId.setSoft(k);
            tempList.add(byId);
        }
        //置底
        if (type == 1) {
            for(int i=byId.getSoft();i<hdJsCg.size();i++){
                hdJsCg.get(i).setSoft(hdJsCg.get(i).getSoft()-1);
                tempList.add(hdJsCg.get(i));
            }
            byId.setSoft(hdJsCg.size());
            tempList.add(byId);
        }
        System.out.println(tempList);
        return updateBatchById(tempList);
    }

    /**
     * 根据成果id删除成果信息及审核信息
     * @param id
     * @return
     */
    @Override
    public boolean removeByCgId(Integer id) {
        log.info("【技术交易 - 根据id:{}删除成果信息】",id);
        TJsSh tJsSh = jsShMapper.selectByCgId(id);
        TJsCg tJsCg = new TJsCg();
        tJsCg.setId(id);
        tJsCg.setIsDelete(2);
        jsCgMapper.updateById(tJsCg);
        if (tJsSh.getId() != null) {
            tJsSh.setIsDelete(2);
            if (!jsShAdminService.updateById(tJsSh)) {
                throw new ServiceException("删除成果信息失败!");
            }
        }
        return true;
    }

    /**
     * 根据成果名称查询详细信息
     *
     * @param name
     * @return
     */
    @Override
    public TJsCg selectByName(String name) {
        log.info("【技术交易 - 根据成果名称:{}查询详细信息】", name);
        return jsCgMapper.selectByName(name);
    }

    /**
     * 新增成果信息
     */
    @Override
    public boolean saveCg(TJsCg tJsCg) {
        TJsCg tJsCg2 = jsCgMapper.selectByName(tJsCg.getCgmc());
        if (tJsCg2 != null) {
            return false;
        }
        //TODO 从ThreadLocal中取userId,暂时是假数据,管理员id为1
        tJsCg.setUserId(Integer.parseInt(String.valueOf(getUserId())));
        tJsCg.setReleaseType("技术成果");
        tJsCg.setCjsj(new Date());
        tJsCg.setGxsj(new Date());
        TJsSh tJsSh = new TJsSh();
        Integer jylx = tJsCg.getJylx();
        if (jylx == null) {
            tJsSh.setFbshzt(1);
        } else {
            tJsSh.setJylx(jylx);
            tJsSh.setReleaseAssistanceStatus(1);
        }
        tJsCg.setJylx(null);
        log.info("【技术交易 - 新增成果信息:{},交易类型:{}】", tJsCg, jylx);
        save(tJsCg);
        tJsSh.setLx(1);
        tJsSh.setCgId(tJsCg.getId());
        tJsSh.setCjsj(new Date());
        tJsSh.setGxsj(new Date());
        jsShAdminService.save(tJsSh);
        return true;
    }

    /**
     * 修改成果信息
     */
    @Override
    public void updateTJsCg(TJsCg tJsCg) {
        log.info("【技术交易 - 修改成果信息:{}】", tJsCg);
        jsCgMapper.updateTJsCg(tJsCg);
    }

    /**
     * 根据id批量发布成果
     * @param ids
     * @return
     */
    @Override
    public boolean issueBatch(List<Integer> ids) {
        log.info("【技术交易 - 根据id:{}批量发布成果】", ids);
        List<TJsSh> tJsShes = jsShMapper.selectByCgIds(ids);
        for (TJsSh tJsSh : tJsShes) {
            if (tJsSh.getFbshzt() == 1) {
                tJsSh.setFbshzt(2);
                tJsSh.setReleaseStatus(2);
            }
        }
        if (!jsShAdminService.updateBatchById(tJsShes)) {
            log.error("【技术交易 - 批量发布成果失败!】");
            return false;
        }
        //系统消息
        List<TJsCg> tJsCgs=jsCgMapper.selectBatchIds(ids);
        for (TJsCg tJsCg:tJsCgs) {
            jsXtxxService.addXtxx(jsXtxxService.getUserId(),tJsCg.getUserId().longValue(),0,"信息采集","技术成果","发布成功");
        }

        return true;
    }

    /**
     * 技术拍卖挂牌受理批量下发
     *
     * @param ids
     * @return
     */
    @Override
    public boolean assistanceIssueBatch(List<Integer> ids) {
        log.info("【技术交易 - 技术拍卖挂牌受理批量下发,id:{}!】", ids);
        List<TJsSh> tJsShes = jsShMapper.selectByCgIds(ids);
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
        List<TJsCg> tJsCgs=jsCgMapper.selectBatchIds(ids);
        for (TJsCg tJsCg:tJsCgs) {
            if(tJsShes.get(0).getJylx() == 0){
                jsXtxxService.addXtxx(jsXtxxService.getUserId(),tJsCg.getUserId().longValue(),0,"技术拍卖信息采集","技术成果","发布成功");
            }
            if(tJsShes.get(0).getJylx() == 2){
                jsXtxxService.addXtxx(jsXtxxService.getUserId(),tJsCg.getUserId().longValue(),0,"技术挂牌信息采集","技术成果","发布成功");
            }
        }
        return true;
    }

    /**
     * 批量删除成果
     *
     * @param ids
     * @return
     */
    @Override
    public boolean removeByIdsCg(List<String> ids) {
        List<Integer> list = new ArrayList<>();
        for (String id : ids) {
            Integer i = Integer.valueOf(id);
            list.add(i);
            TJsCg tJsCg = new TJsCg();
            tJsCg.setId(i);
            tJsCg.setIsDelete(1);
            jsCgMapper.updateTJsCg(tJsCg);
        }
        List<TJsSh> tJsShes = jsShAdminService.selectBycgxqIds(list);
        for (TJsSh tJsSh : tJsShes) {
            tJsSh.setIsDelete(1);
            jsShMapper.updateById(tJsSh);
        }
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

}


