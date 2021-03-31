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

/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术成果业务逻辑
 */

@Service
@Primary
@Slf4j
@Transactional
public class JsCgAdminServiceImpl extends ServiceImpl<JsCgMapper, TJsCg> implements JsCgAdminService {
    @Autowired
    private JsCgMapper jsCgMapper;

    @Autowired
    private JsShAdminService jsShAdminService;


	@Autowired
	private JsShMapper jsShMapper;


    /**
     * 分页查询成果(后台审批管理(用户录入信息))
     * @param params
     * @return
     */
    @Override
    public PageInfo<TJsCg> findJsCg(Map<String, Object> params) {
        log.info("【技术交易 - 分页查询成果(后台审批管理)】");
        //TODO 从ThreadLocal中获取用户id 暂时是假数据
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
    public TJsCg findById(Integer id) {
        log.info("【技术交易 - 根据成果id:{}查询详细信息】",id);
        return jsCgMapper.findById(id);
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
    public boolean saveCg(TJsCg tJsCg) {
        TJsCg tJsCg2 = selectByName(tJsCg.getCgmc());
        if (tJsCg2 != null) {
            return false;
        }
        tJsCg.setReleaseType("技术成果");
        tJsCg.setCjsj(new Date());
        log.info("【技术交易 - 新增成果信息:{}】", tJsCg);
        save(tJsCg);
        TJsSh tJsSh = new TJsSh();
        tJsSh.setLx(1);
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
     * 批量下发
     *
     * @param ids
     * @return
     */
    @Override
    public boolean issueBatch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return false;
        } else {
            List<TJsSh> tJsShes = jsShAdminService.selectBycgxqIds(ids);
            for (TJsSh tJsShe : tJsShes) {
                if ("2".equals(tJsShe.getFbshzt())) {
					tJsShe.setReleaseStatus(2);
				}
            }
            jsShAdminService.updateBatchById(tJsShes);
            return true;
        }
    }





	/**
	 * 技术转让受理批量下发
	 * @param ids
	 * @return
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


