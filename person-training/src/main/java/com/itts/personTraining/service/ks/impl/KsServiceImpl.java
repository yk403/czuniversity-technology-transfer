package com.itts.personTraining.service.ks.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.dto.KsDTO;
import com.itts.personTraining.mapper.ksXs.KsXsMapper;
import com.itts.personTraining.mapper.szKs.SzKsMapper;
import com.itts.personTraining.model.kc.Kc;
import com.itts.personTraining.model.ks.Ks;
import com.itts.personTraining.mapper.ks.KsMapper;
import com.itts.personTraining.model.ksXs.KsXs;
import com.itts.personTraining.model.szKs.SzKs;
import com.itts.personTraining.model.xyKc.XyKc;
import com.itts.personTraining.service.ks.KsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.personTraining.service.ksXs.KsXsService;
import com.itts.personTraining.service.szKs.SzKsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;

/**
 * <p>
 * 考试表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-05-06
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class KsServiceImpl extends ServiceImpl<KsMapper, Ks> implements KsService {

    @Autowired
    private KsService ksService;
    @Resource
    private KsMapper ksMapper;
    @Autowired
    private SzKsService szKsService;
    @Resource
    private SzKsMapper szKsMapper;

    /**
     * 查询考试列表
     * @param pageNum
     * @param pageSize
     * @param kslx
     * @param pcId
     * @param pclx
     * @param kcmc
     * @return
     */
    @Override
    public PageInfo<KsDTO> findByPage(Integer pageNum, Integer pageSize, String kslx, Long pcId, String pclx, String kcmc) {
        log.info("【人才培养 - 分页条件查询考试列表,考试类型:{},批次id:{},批次类型:{},课程名称:{}】",kslx,pcId,pclx,kcmc);
        PageHelper.startPage(pageNum, pageSize);
        List<KsDTO> ksDTOs = ksMapper.findByPage(kslx,pcId,pclx,kcmc);
        return new PageInfo<>(ksDTOs);
    }

    /**
     * 根据id查询考试详情
     * @param id
     * @return
     */
    @Override
    public KsDTO get(Long id) {
        log.info("【人才培养 - 根据id:{}查询考试详情】",id);
        KsDTO ksDTO = ksMapper.findById(id);
        ksDTO.setSzIds(szKsMapper.selectByKsId(id));
        return ksDTO;
    }



    /**
     * 新增考试
     * @param ksDTO
     * @return
     */
    @Override
    public boolean add(KsDTO ksDTO) {
        log.info("【人才培养 - 新增考试:{}】",ksDTO);
        Long userId = getUserId();
        Ks ks = new Ks();
        ksDTO.setCjr(userId);
        ksDTO.setGxr(userId);
        BeanUtils.copyProperties(ksDTO,ks);
        if (ksService.save(ks)) {
            List<Long> szIds = ksDTO.getSzIds();
            List<SzKs> szKsList = new ArrayList<>();
            for (Long szId : szIds) {
                SzKs szKs = new SzKs();
                szKs.setSzId(szId);
                szKs.setKsId(ks.getId());
                szKsList.add(szKs);
            }
            return szKsService.saveBatch(szKsList);
        }
        return false;
    }

    /**
     * 更新考试
     * @param ksDTO
     * @return
     */
    @Override
    public boolean update(KsDTO ksDTO) {
        return false;
    }

    /**
     * 删除考试
     * @param ksDTO
     * @return
     */
    @Override
    public boolean delete(KsDTO ksDTO) {
        log.info("【人才培养 - 删除考试:{}】",ksDTO);
        //设置删除状态
        ksDTO.setSfsc(true);
        ksDTO.setGxr(getUserId());
        Ks ks = new Ks();
        BeanUtils.copyProperties(ksDTO,ks);
        if (ksService.updateById(ks)) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("ks_id",ks.getId());
            return szKsService.removeByMap(map);
        }
        return false;
    }

    /**
     * 考试批量下发
     * @param ids
     * @return
     */
    @Override
    public boolean issueBatch(List<Long> ids) {
        return false;
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
