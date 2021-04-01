package com.itts.technologytransactionservice.service.impl;

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
import com.itts.technologytransactionservice.service.JsCgService;
import com.itts.technologytransactionservice.service.JsShService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
@Primary
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class JsCgServiceImpl extends ServiceImpl<JsCgMapper, TJsCg> implements JsCgService {
    @Autowired
    private JsCgMapper jsCgMapper;

    @Autowired
    private JsShService jsShService;

	@Autowired
	private JsShMapper jsShMapper;


    /**
     * 分页条件查询成果(前台)
     * @param params
     * @return
     */
    @Override
    public PageInfo<TJsCg> findJsCgFront(Map<String, Object> params) {
        log.info("【技术交易 - 分页条件查询成果(前台)】");
        Query query = new Query(params);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<TJsCg> list = jsCgMapper.findJsCgFront(query);
        return new PageInfo<>(list);
    }

    /**
     * 分页条件查询(个人详情)
     * @param params
     * @return
     */
    @Override
    public PageInfo<TJsCg> findJsCgUser(Map<String, Object> params) {
        log.info("【技术交易 - 分页查询成果(个人详情)】");
        //TODO 从ThreadLocal中获取用户id 暂时是假数据
        params.put("userId",2);
        Query query = new Query(params);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<TJsCg> list = jsCgMapper.findJsCgFront(query);
        return new PageInfo<>(list);
    }

    /**
     * 根据成果名称查询
     * @param name
     * @return
     */
    @Override
    public TJsCg selectByName(String name) {
        log.info("【技术交易 - 根据成果名称:{}查询详细信息】",name);
        return jsCgMapper.selectByName(name);
    }

    /**
     *
     * @param tJsCg
     * @return
     */
    @Override
    public boolean saveCg(TJsCg tJsCg) {
        if (tJsCg.getId() != null) {
            return false;
        } else {
            TJsCg tJsCg2 = selectByName(tJsCg.getCgmc());
            if (tJsCg2 != null) {
                return false;
            }
            //TODO 从ThreadLocal中取userId,暂时是假数据,用户id为2
            tJsCg.setUserId(2);
            tJsCg.setReleaseType("技术成果");
            tJsCg.setCjsj(new Date());
            log.info("【技术交易 - 新增成果信息】",tJsCg);
            save(tJsCg);
            TJsSh tJsSh = new TJsSh();
            tJsSh.setLx(1);
            tJsSh.setCjsj(new Date());
            tJsSh.setCgId(tJsCg.getId());
            jsShService.save(tJsSh);
            return true;
        }
    }



	/**
	 * 删除成果
	 * @param id
	 * @return
	 */
	@Override
    public boolean removeByIdCg(Integer id) {
	    log.info("【技术交易 - 根据id:{}删除成果】",id);
        TJsSh tJsSh = jsShService.selectByCgId(id);
        TJsCg tJsCg = new TJsCg();
        tJsCg.setId(id);
        tJsCg.setIsDelete(1);
        jsCgMapper.updateTJsCg(tJsCg);
        if (tJsSh.getId() != null) {
            tJsSh.setIsDelete(1);
            if (!jsShService.updateById(tJsSh)) {
                throw new ServiceException("删除成功失败!");
            }
        }
        return true;
    }

    @Override
    public boolean passUpdateById(Integer id) {
        TJsSh tJsSh = jsShService.selectByCgId(id);
        Integer fbshzt = tJsSh.getFbshzt();
        if (fbshzt != 2) {
            tJsSh.setFbshzt(2);
            jsShService.saveOrUpdate(tJsSh);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 成果批量下发
     *
     * @param ids
     * @return
     */
    @Override
    public boolean issueBatch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return false;
        } else {
            List<TJsSh> tJsShes = jsShService.selectBycgIds(ids);
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
     * 修改成果信息
     * @param tJsCg
     * @return
     */
    @Override
    public boolean updateTJsCg(TJsCg tJsCg) {
        tJsCg.setGxsj(new Date());
        jsCgMapper.updateTJsCg(tJsCg);
        return true;
    }

    /**
     * 已发布的成果申请拍卖挂牌(受理协办)
     * @param tJsCg
     * @return
     */
    @Override
    public boolean assistanceUpdateTJsCg(TJsCg tJsCg) {
        TJsSh tJsSh = jsShService.selectByCgId(tJsCg.getId());
        if (tJsSh.getFbshzt() != 2) {
            log.error("发布审核状态未通过,无法申请拍卖挂牌!");
            return false;
        }
        tJsSh.setAssistanceStatus(1);
        tJsSh.setReleaseAssistanceStatus(1);
        if (!jsShService.updateById(tJsSh)) {
            log.error("更新审核失败!");
            throw new ServiceException("更新审核失败!");
        }
        return true;
    }

    @Override
    public boolean assistancePassUpdateById(Integer id) {
        TJsSh tJsSh = jsShService.selectByCgId(id);
        Integer assistanceStatus = tJsSh.getAssistanceStatus();
        if (assistanceStatus != 2) {
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
        TJsSh tJsSh = jsShService.selectByCgId(Integer.valueOf(id));
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
	 * 技术转让受理批量下发
	 * @param ids
	 * @return
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
		List<TJsSh> tJsShes = jsShService.selectBycgxqIds(list);
        for (TJsSh tJsSh : tJsShes) {
            tJsSh.setIsDelete(1);
            jsShMapper.updateById(tJsSh);
        }
		return true;
	}



}


