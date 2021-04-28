package com.itts.personTraining.service.kc.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.dto.KcDTO;
import com.itts.personTraining.mapper.kcSz.KcSzMapper;
import com.itts.personTraining.mapper.xyKc.XyKcMapper;
import com.itts.personTraining.model.kc.Kc;
import com.itts.personTraining.mapper.kc.KcMapper;
import com.itts.personTraining.model.kcSz.KcSz;
import com.itts.personTraining.model.xyKc.XyKc;
import com.itts.personTraining.service.kc.KcService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.personTraining.service.kcSz.KcSzService;
import com.itts.personTraining.service.xyKc.XyKcService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jcajce.BCFKSStoreParameter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.*;

/**
 * <p>
 * 课程表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class KcServiceImpl extends ServiceImpl<KcMapper, Kc> implements KcService {

    @Resource
    private KcMapper kcMapper;

    @Autowired
    private KcService kcService;

    @Autowired
    private XyKcService xyKcService;

    @Resource
    private KcSzMapper kcSzMapper;
    @Autowired
    private KcSzService kcSzService;

    /**
     * 分页条件查询课程列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<KcDTO> findByPage(Integer pageNum, Integer pageSize, String kclx, String name, Long xyId) {
        log.info("【人才培养 - 分页条件查询课程列表,课程类型:{},课程代码/名称:{},学院id:{}】",kclx,name,xyId);
        PageHelper.startPage(pageNum, pageSize);
        List<KcDTO> kcDTOS = kcMapper.findByPage(kclx,name,xyId);
        for (KcDTO kcDTO : kcDTOS) {
            QueryWrapper<KcSz> kcSzQueryWrapper = new QueryWrapper<>();
            kcSzQueryWrapper.eq("kc_id",kcDTO.getId());
            List<KcSz> kcSzList = kcSzMapper.selectList(kcSzQueryWrapper);
            List<Long> szIds = new ArrayList<>();
            for (KcSz kcSz : kcSzList) {
                szIds.add(kcSz.getSzId());
            }
            kcDTO.setSzIds(szIds);
        }
        return new PageInfo<>(kcDTOS);
    }

    /**
     * 根据id查询课程信息
     * @param id
     * @return
     */
    @Override
    public Kc get(Long id) {
        log.info("【人才培养 - 根据id:{}查询课程信息】",id);
        QueryWrapper<Kc> kcQueryWrapper = new QueryWrapper<>();
        kcQueryWrapper.eq("sfsc",false)
                      .eq("id",id);
        return kcMapper.selectOne(kcQueryWrapper);
    }

    /**
     * 课程批量下发
     * @param ids
     * @return
     */
    @Override
    public boolean issueBatch(List<Long> ids) {
        log.info("【人才培养 - 课程批量下发,ids:{}】",ids);
        List<Kc> kcs = kcMapper.selectBatchIds(ids);
        for (Kc kc : kcs) {
            kc.setSfxf(true);
        }
        return kcService.updateBatchById(kcs);
    }

    /**
     * 删除课程
     * @param kc
     * @return
     */
    @Override
    public boolean delete(Kc kc) {
        log.info("【人才培养 - 删除课程:{}】",kc);
        //设置删除状态
        kc.setSfsc(true);
        if (kcService.updateById(kc)) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("kc_id",kc.getId());
            if (xyKcService.removeByMap(map)) {
                HashMap<String, Object> map1 = new HashMap<>();
                map1.put("kc_id",kc.getId());
                return kcSzService.removeByMap(map1);
            }
            return false;
        }
        return false;
    }

    /**
     * 查询所有课程
     * @return
     */
    @Override
    public List<Kc> getAll() {
        log.info("【人才培养 - 查询所有课程信息】");
        QueryWrapper<Kc> kcQueryWrapper = new QueryWrapper<>();
        kcQueryWrapper.eq("sfsc",false);
        return kcMapper.selectList(kcQueryWrapper);
    }

    /**
     * 新增课程
     * @param kcDTO
     * @return
     */
    @Override
    public boolean add(KcDTO kcDTO) {
        log.info("【人才培养 - 新增课程:{}】",kcDTO);
        Long userId = getUserId();
        Kc kc = new Kc();
        kc.setCjr(userId);
        kc.setGxr(userId);
        BeanUtils.copyProperties(kcDTO,kc);
        if (kcService.save(kc)) {
            XyKc xyKc = new XyKc();
            xyKc.setKcId(kc.getId());
            xyKc.setXyId(kcDTO.getXyId());
            if (xyKcService.save(xyKc)) {
                return addKcSz(kcDTO, kc);
            }
            return false;
        }
        return false;
    }

    /**
     * 更新课程
     * @param kcDTO
     * @return
     */
    @Override
    public boolean update(KcDTO kcDTO) {
        log.info("【人才培养 - 更新课程:{}】",kcDTO);
        Kc kc = new Kc();
        kc.setGxr(getUserId());
        BeanUtils.copyProperties(kcDTO,kc);
        if (kcService.updateById(kc)) {
            xyKcService.removeById(kcDTO.getXyKcId());
            XyKc xyKc = new XyKc();
            xyKc.setKcId(kc.getId());
            xyKc.setXyId(kcDTO.getXyId());
            if (kcDTO.getSzIds() != null) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("kc_id",kcDTO.getId());
                if (kcSzService.removeByMap(map)) {
                    return addKcSz(kcDTO, kc);
                }
                return false;
            }
            return xyKcService.save(xyKc);
        }
        return false;
    }

    /**
     * 新增课程师资关系
     * @param kcDTO
     * @param kc
     * @return
     */
    private boolean addKcSz(KcDTO kcDTO, Kc kc) {
        List<KcSz> kcSzList = new ArrayList<>();
        List<Long> szIds = kcDTO.getSzIds();
        for (Long szId : szIds) {
            KcSz kcSz = new KcSz();
            kcSz.setSzId(szId);
            kcSz.setKcId(kc.getId());
            kcSzList.add(kcSz);
        }
        return kcSzService.saveBatch(kcSzList);
    }

    /**
     * 获取当前用户id
     * @return
     */
    private Long getUserId() {
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
