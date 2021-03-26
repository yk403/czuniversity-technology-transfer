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
public class JsCgAdminServiceImpl extends ServiceImpl<JsCgMapper, TJsCg> implements JsCgAdminService {
    @Autowired
    private JsCgMapper jsCgMapper;

    @Autowired
    private JsShAdminService jsShAdminService;

	@Autowired
	private JsShMapper jsShMapper;


    @Override
    public PageInfo<TJsCg> FindtJsCgByTJsLbTJsLy(Query query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<TJsCg> list = jsCgMapper.FindtJsCgByTJsLbTJsLy(query);
        return new PageInfo<>(list);
    }

    @Override
    public boolean saveCg(TJsCg tJsCg) throws Exception {
        if (tJsCg.getId() != null) {
            return false;
        } else {
            TJsCg tJsCg2 = selectByName(tJsCg.getCgmc());
            if (tJsCg2 != null) {
                return false;
            }
            tJsCg.setReleaseType("技术成果");
            save(tJsCg);
            TJsSh tJsSh = new TJsSh();
            tJsSh.setLx(1);
            tJsSh.setCgId(tJsCg.getId());
            tJsSh.setCjsj(new Date());
            jsShAdminService.save(tJsSh);
            return true;
        }
    }

    @Override
    public TJsCg selectByName(String name) {
        return jsCgMapper.selectByName(name);
    }

	/**
	 * 删除成果
	 * @param id
	 * @return
	 */
	@Override
    public boolean removeByIdCg(Integer id) {
	    log.info("【技术交易 - 根据id:{}删除成果】",id);
	    Integer lx = 1;
        TJsSh tJsSh = jsShAdminService.selectBycgxqId(id,lx);
        TJsCg tJsCg = new TJsCg();
        tJsCg.setId(id);
        tJsCg.setIsDelete(1);
        jsCgMapper.updateTJsCg(tJsCg);
        if (tJsSh.getId() != null) {
            tJsSh.setIsDelete(1);
            tJsSh.setLx(1);
            if (!jsShAdminService.updateById(tJsSh)) {
                throw new ServiceException("删除成功失败!");
            }
        }
        return true;
    }

    @Override
    public boolean passUpdateById(Integer id) {
	    Integer lx = 1;
        TJsSh tJsSh = jsShAdminService.selectBycgxqId(id,lx);
        Integer fbshzt = tJsSh.getFbshzt();
        if (!"2".equals(fbshzt)) {
            tJsSh.setFbshzt(2);
            jsShAdminService.saveOrUpdate(tJsSh);
            return true;
        } else {
            return false;
        }
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

    @Override
    public boolean updateTJsCg(TJsCg tJsCg) {
        jsCgMapper.updateTJsCg(tJsCg);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean assistanceUpdateTJsCg(TJsCg tJsCg) {
        Integer lx = 1;
        TJsSh tJsSh = jsShAdminService.selectBycgxqId(tJsCg.getId(),lx);
        if (!"2".equals(tJsSh.getReleaseStatus())) {
            log.error("未发布的成果无法申请拍卖和招投标");
            throw new ServiceException("未发布的成果无法申请拍卖和招投标");
        } else {
            tJsSh.setAssistanceStatus(1);
            tJsSh.setReleaseAssistanceStatus(1);
            jsShAdminService.updateById(tJsSh);
            jsCgMapper.updateTJsCg(tJsCg);
        }
        return true;
    }

    @Override
    public boolean assistancePassUpdateById(Integer id) {
        Integer lx = 1;
        TJsSh tJsSh = jsShAdminService.selectBycgxqId(id,lx);
        Integer assistanceStatus = tJsSh.getAssistanceStatus();
        if (assistanceStatus != 2) {
            tJsSh.setAssistanceStatus(2);
            jsShAdminService.saveOrUpdate(tJsSh);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean assistanceDisPassById(Map<String, Object> params) {
        String id = params.get("id").toString();
        Integer lx = 1;
        String assistanceRemark = params.get("assistanceRemark").toString();
        TJsSh tJsSh = jsShAdminService.selectBycgxqId(Integer.valueOf(id),lx);
        Integer assistanceStatus = tJsSh.getAssistanceStatus();
        if (assistanceStatus != 2) {
            tJsSh.setAssistanceStatus(3);
            tJsSh.setAssistanceRemark(assistanceRemark);
            jsShAdminService.saveOrUpdate(tJsSh);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean disPassById(Map<String, Object> params) {
        String id = params.get("id").toString();
        Integer lx = 1;
        String fbwtgsm = params.get("fbwtgsm").toString();
        TJsSh tJsSh = jsShAdminService.selectBycgxqId(Integer.valueOf(id),lx);
        Integer fbshzt = tJsSh.getFbshzt();
        if (fbshzt != 2) {
            tJsSh.setFbshzt(3);
            tJsSh.setFbwtgsm(fbwtgsm);
            jsShAdminService.saveOrUpdate(tJsSh);
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


