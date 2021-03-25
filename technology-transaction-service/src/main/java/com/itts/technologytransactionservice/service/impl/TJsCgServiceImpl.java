package com.itts.technologytransactionservice.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.TJsCgMapper;
import com.itts.technologytransactionservice.mapper.TJsShMapper;
import com.itts.technologytransactionservice.model.TJsCg;
import com.itts.technologytransactionservice.model.TJsSh;
import com.itts.technologytransactionservice.service.ITJsCgService;
import com.itts.technologytransactionservice.service.ITJsShService;
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
public class TJsCgServiceImpl extends ServiceImpl<TJsCgMapper, TJsCg> implements ITJsCgService {
    @Autowired
    private TJsCgMapper tJsCgMapper;

    @Autowired
    private ITJsShService tJsShService;

	@Autowired
	private TJsShMapper tJsShMapper;


    @Override
    public PageInfo<TJsCg> FindtJsCgByTJsLbTJsLy(Query query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<TJsCg> list = tJsCgMapper.FindtJsCgByTJsLbTJsLy(query);
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
            tJsSh.setLx("1");
            tJsSh.setCgxqId(tJsCg.getId());
            tJsSh.setFbshzt("1");
            tJsSh.setReleaseStatus("1");
            tJsSh.setCjsj(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            tJsShService.save(tJsSh);
            return true;
        }
    }

    @Override
    public TJsCg selectByName(String name) {
        return tJsCgMapper.selectByName(name);
    }

	/**
	 * 删除成果
	 * @param id
	 * @return
	 */
	@Override
    public boolean removeByIdCg(Integer id) {
        TJsSh tJsSh = tJsShService.selectBycgxqId(id);
        TJsCg tJsCg = new TJsCg();
        tJsCg.setId(id);
        tJsCg.setIsDelete(1);
        tJsCgMapper.updateTJsCg(tJsCg);
        if (tJsSh.getId() != null) {
            tJsSh.setIsDelete(1);
            if (!tJsShService.updateById(tJsSh)) {
                throw new ServiceException("删除成功失败!");
            }
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

    @Override
    public boolean updateTJsCg(TJsCg tJsCg) {
        tJsCgMapper.updateTJsCg(tJsCg);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean assistanceUpdateTJsCg(TJsCg tJsCg) {
        TJsSh tJsSh = tJsShService.selectBycgxqId(tJsCg.getId());
        if (!"2".equals(tJsSh.getReleaseStatus())) {
            log.error("未发布的成果无法申请拍卖和招投标");
            throw new ServiceException("未发布的成果无法申请拍卖和招投标");
        } else {
            tJsSh.setAssistanceStatus("1");
            tJsSh.setReleaseAssistanceStatus("1");
            tJsShService.updateById(tJsSh);
            tJsCgMapper.updateTJsCg(tJsCg);
        }
        return true;
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
	 * 技术转让受理批量下发
	 * @param ids
	 * @return
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
            tJsCgMapper.updateTJsCg(tJsCg);
		}
		List<TJsSh> tJsShes = tJsShService.selectBycgxqIds(list);
        for (TJsSh tJsSh : tJsShes) {
            tJsSh.setIsDelete(1);
            tJsShMapper.updateById(tJsSh);
        }
		return true;
	}
}


