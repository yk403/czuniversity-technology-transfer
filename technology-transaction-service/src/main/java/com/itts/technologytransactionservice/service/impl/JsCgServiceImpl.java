package com.itts.technologytransactionservice.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.itts.common.bean.LoginUser;
import com.itts.common.enums.ErrorCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.exception.ServiceException;
import com.itts.common.exception.WebException;
import com.itts.common.utils.DateUtils;
import com.itts.common.utils.Query;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.feign.userservice.JgglFeignService;
import com.itts.technologytransactionservice.mapper.JsCgMapper;
import com.itts.technologytransactionservice.mapper.JsShMapper;
import com.itts.technologytransactionservice.model.JgglVO;
import com.itts.technologytransactionservice.model.TJsCg;
import com.itts.technologytransactionservice.model.TJsSh;
import com.itts.technologytransactionservice.service.JsCgService;
import com.itts.technologytransactionservice.service.JsShService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.*;

import java.time.LocalDate;
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

	@Resource
    private JgglFeignService jgglFeignService;

    /**
     * 分页条件查询成果(前台)
     * @param params
     * @return
     */
    @Override
    public PageInfo<TJsCg> findJsCgFront(Map<String, Object> params) {
        //log.info("【技术交易 - 分页条件查询成果(前台)】");
        Long fjjgId = getFjjgId();
        if(params.get("jgCode") != null){
            ResponseUtil response = jgglFeignService.getByCode(params.get("jgCode").toString());
            if(response == null || response.getErrCode().intValue() != 0){
                throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
            }
            JgglVO jggl = response.conversionData(new TypeReference<JgglVO>() {});
            fjjgId = jggl.getId();
        }
        params.put("fjjgId",fjjgId);
        Query query = new Query(params);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<TJsCg> list = jsCgMapper.findJsCgFront(query);
        return new PageInfo<>(list);
    }

    /**
     * 分页条件查询成果(个人详情)
     * @param params
     * @return
     */
    @Override
    public PageInfo<TJsCg> findJsCgUser(Map<String, Object> params) {
        log.info("【技术交易 - 分页查询成果(个人详情)】");
        //TODO 从ThreadLocal中获取用户id 暂时是假数据
        Long fjjgId = getFjjgId();
        if(params.get("jgCode") != null){
            ResponseUtil response = jgglFeignService.getByCode(params.get("jgCode").toString());
            if(response == null || response.getErrCode().intValue() != 0){
                throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
            }
            JgglVO jggl = response.conversionData(new TypeReference<JgglVO>() {});
            fjjgId = jggl.getId();
        }
        params.put("fjjgId",fjjgId);
        params.put("userId",Integer.parseInt(String.valueOf(getUserId())));
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
        Long fjjgId = getFjjgId();
        return jsCgMapper.selectByName(name,fjjgId);
    }

    /**
     *新增成果信息
     * @param tJsCg
     * @return
     */
    @Override
    public boolean saveCg(TJsCg tJsCg) {
        Long fjjgId = getFjjgId();
        if (tJsCg.getId() != null) {
            return false;
        } else {
            TJsCg tJsCg2 = jsCgMapper.selectByName(tJsCg.getCgmc(),fjjgId);
            if (tJsCg2 != null) {
                throw new ServiceException(NAME_REPEAT);
            }
            //TODO 从ThreadLocal中取userId,暂时是假数据,用户id为2
            tJsCg.setUserId(Integer.parseInt(String.valueOf(getUserId())));
            tJsCg.setReleaseType("技术成果");
            tJsCg.setCjsj(new Date());
            tJsCg.setGxsj(new Date());
            tJsCg.setFjjgId(fjjgId);
            log.info("【技术交易 - 新增成果信息】",tJsCg);
            save(tJsCg);
            TJsSh tJsSh = new TJsSh();
            tJsSh.setLx(1);
            tJsSh.setCjsj(new Date());
            tJsSh.setGxsj(new Date());
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
        tJsCg.setGxsj(new Date());
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
     * @param params
     * @return
     */
    @Override
    public boolean assistanceUpdateTJsCg(Map<String, Object> params,Integer jylx) {
        TJsSh tJsSh = jsShService.selectByCgId(Integer.valueOf(params.get("id").toString()));
        Long fjjgId = getFjjgId();
        if (tJsSh.getFbshzt() != 2) {
            log.error("发布审核状态未通过,无法申请拍卖挂牌!");
            return false;
        }
        TJsCg cg= new TJsCg();
        if(params.get("zzqk")!=null){
            cg.setId(Integer.valueOf(params.get("id").toString()));
            cg.setBz(params.get("bz").toString());
            cg.setCghqfs(params.get("cghqfs").toString());
            cg.setCgjs(params.get("cgjs").toString());
            cg.setHjqk(params.get("hjqk").toString());
            cg.setJscsd(params.get("jscsd").toString());
            cg.setJszb(params.get("jszb").toString());
            cg.setSyfx(params.get("syfx").toString());
            cg.setZscqxs(params.get("zscqxs").toString());
            cg.setZzqk(params.get("zzqk").toString());
            cg.setFjjgId(fjjgId);
            cg.setGxsj(new Date());
            tJsSh.setAssistanceStatus(1);
            tJsSh.setJylx(jylx);
            tJsSh.setReleaseAssistanceStatus(1);
            jsCgMapper.updateTJsCg(cg);
        }else if(params.get("assistanceStatus")!=null){
            tJsSh.setAssistanceStatus(Integer.parseInt(params.get("assistanceStatus").toString()));
            tJsSh.setJylx(jylx);
            tJsSh.setSlxbbz("");
            tJsSh.setReleaseAssistanceStatus(1);
        }
        jsShMapper.updateTJsShs(tJsSh);
//        if (!jsShService.updateById(tJsSh)) {
//            log.error("更新审核失败!");
//            throw new ServiceException("更新审核失败!");
//        }
        return true;
    }

    /**
     * 个人发布审核成果申请(0待提交;1待审核;2通过;3整改;4拒绝)
     * @param params
     * @return
     */
    @Override
    public boolean auditCg(Map<String, Object> params, Integer fbshzt) {
        TJsSh tJsSh = jsShMapper.selectByCgId(Integer.parseInt(params.get("id").toString()));
        tJsSh.setFbshzt(fbshzt);
        tJsSh.setGxsj(new Date());
        //如果门户在信息采集的整改中申请发布改为整改完成时将发布审核状态清空
        if(fbshzt == 5){
            tJsSh.setFbshbz("");
        }
        jsShMapper.updateTJsShs(tJsSh);
//        if (!jsShService.updateById(tJsSh)) {
//            throw new ServiceException("发布审核成果申请失败!");
//        }
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

    private Long getFjjgId(){
        LoginUser loginUser = threadLocal.get();
        Long fjjgId = null;
        if (loginUser != null) {
            fjjgId = loginUser.getFjjgId();
        }
        return fjjgId;
    }
}


