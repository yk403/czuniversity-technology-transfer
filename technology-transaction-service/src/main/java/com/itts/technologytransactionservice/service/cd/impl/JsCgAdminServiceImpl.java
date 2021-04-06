package com.itts.technologytransactionservice.service.cd.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.JsCgMapper;
import com.itts.technologytransactionservice.mapper.JsShMapper;
import com.itts.technologytransactionservice.model.TJsCg;
import com.itts.technologytransactionservice.model.TJsSh;
import com.itts.technologytransactionservice.service.cd.JsCgAdminService;
import com.itts.technologytransactionservice.service.cd.JsShAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.itts.common.enums.ErrorCodeEnum.ISSUE_BATCH_FAIL;

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
    private JsShAdminService jsShAdminService;


	@Autowired
	private JsShMapper jsShMapper;


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
        params.put("userId",1);
        Query query = new Query(params);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<TJsCg> list = jsCgMapper.findJsCg(query);
        return new PageInfo<>(list);
    }

    /**
     * 根据成果id查询详细信息
     * @param id
     * @return
     */
    @Override
    public TJsCg getById(Integer id) {
        log.info("【技术交易 - 根据成果id:{}查询详细信息】",id);
        return jsCgMapper.getById(id);
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
        tJsCg.setIsDelete(1);
        jsCgMapper.updateById(tJsCg);
        if (tJsSh.getId() != null) {
            tJsSh.setIsDelete(1);
            if (!jsShAdminService.updateById(tJsSh)) {
                throw new ServiceException("删除成果信息失败!");
            }
        }
        return true;
    }

    /**
     * 根据成果名称查询详细信息
     * @param name
     * @return
     */
    @Override
    public TJsCg selectByName(String name) {
        log.info("【技术交易 - 根据成果名称:{}查询详细信息】",name);
        return jsCgMapper.selectByName(name);
    }

    /**
     * 新增成果信息
     */
    @Override
    public boolean saveCg(TJsCg tJsCg,Integer jylx) {
        TJsCg tJsCg2 = jsCgMapper.selectByName(tJsCg.getCgmc());
        if (tJsCg2 != null) {
            return false;
        }
        //TODO 从ThreadLocal中取userId,暂时是假数据,管理员id为1
        tJsCg.setUserId(1);
        tJsCg.setReleaseType("技术成果");
        tJsCg.setCjsj(new Date());
        log.info("【技术交易 - 新增成果信息:{},交易类型:{}】", tJsCg,jylx);
        save(tJsCg);
        TJsSh tJsSh = new TJsSh();
        tJsSh.setLx(1);
        tJsSh.setFbshzt(1);
        if (jylx != null) {
            tJsSh.setJylx(jylx);
            tJsSh.setAssistanceStatus(1);
            //TODO 发布审核是否需要改变
        }
        tJsSh.setCgId(tJsCg.getId());
        tJsSh.setCjsj(new Date());
        jsShAdminService.save(tJsSh);
        return true;
    }

    /**
     * 修改成果信息
     */
    @Override
    public void updateTJsCg(TJsCg tJsCg) {
        log.info("【技术交易 - 修改成果信息:{}】",tJsCg);
        jsCgMapper.updateTJsCg(tJsCg);
    }

    /**
     * 根据id批量发布成果
     * @param ids
     * @return
     */
    @Override
    public boolean issueBatch(List<Integer> ids) {
        log.info("【技术交易 - 根据id:{}批量发布成果】",ids);
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
        return true;
    }

	/**
	 * 技术拍卖挂牌受理批量下发
	 * @param ids
	 * @return
	 */
    @Override
    public boolean assistanceIssueBatch(List<Integer> ids) {
        log.info("【技术交易 - 技术拍卖挂牌受理批量下发,id:{}!】",ids);
        List<TJsSh> tJsShes = jsShMapper.selectByCgIds(ids);
        for (TJsSh tJsShe : tJsShes) {
            tJsShe.setAssistanceStatus(2);
            tJsShe.setReleaseAssistanceStatus(2);
        }
        if (!jsShAdminService.updateBatchById(tJsShes)) {
            return false;
        }
        return true;
    }

	/**
	 * 批量删除成果
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


}


