package com.itts.personTraining.service.pk.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.dto.PkDTO;
import com.itts.personTraining.model.kc.Kc;
import com.itts.personTraining.model.pk.Pk;
import com.itts.personTraining.mapper.pk.PkMapper;
import com.itts.personTraining.service.pk.PkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;

/**
 * <p>
 * 排课表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
@Service
@Slf4j
@Transactional
public class PkServiceImpl extends ServiceImpl<PkMapper, Pk> implements PkService {

    @Resource
    private PkMapper pkMapper;
    @Autowired
    private PkService pkService;
    /**
     * 查询排课列表
     * @param pageNum
     * @param pageSize
     * @param pcId
     * @return
     */
    @Override
    public PageInfo<PkDTO> findByPage(Integer pageNum, Integer pageSize, Long pcId) {
        log.info("【人才培养 - 分页条件查询排课列表,批次id:{}】",pcId);
        PageHelper.startPage(pageNum, pageSize);
        List<PkDTO> pkDTOs = pkMapper.findPage(pcId);
        return new PageInfo<>(pkDTOs);
    }

    /**
     * 根据id查询排课详情
     * @param id
     * @return
     */
    @Override
    public PkDTO get(Long id) {
        log.info("【人才培养 - 根据id:{}查询排课信息】",id);
        return pkMapper.getById(id);
    }

    /**
     * 新增排课
     * @param pkDTO
     * @return
     */
    @Override
    public boolean add(PkDTO pkDTO) {
        log.info("【人才培养 - 新增排课:{}】",pkDTO);
        Long userId = getUserId();
        pkDTO.setCjr(userId);
        pkDTO.setGxr(userId);
        Pk pk = new Pk();
        BeanUtils.copyProperties(pkDTO,pk);
        return pkService.save(pk);
    }

    /**
     * 更新排课
     * @param pkDTO
     * @return
     */
    @Override
    public boolean update(PkDTO pkDTO) {
        log.info("【人才培养 - 更新排课:{}】",pkDTO);
        Long userId = getUserId();
        pkDTO.setGxr(userId);
        Pk pk = new Pk();
        BeanUtils.copyProperties(pkDTO,pk);
        return pkService.updateById(pk);
    }

    /**
     * 删除排课
     * @param pkDTO
     * @return
     */
    @Override
    public boolean delete(PkDTO pkDTO) {
        log.info("【人才培养 - 删除排课:{}】",pkDTO);
        //设置删除状态
        pkDTO.setSfsc(true);
        Pk pk = new Pk();
        BeanUtils.copyProperties(pkDTO,pk);
        return pkService.updateById(pk);
    }

    /**
     * 批量新增排课
     * @param pkDTOs
     * @return
     */
    @Override
    public boolean addList(List<PkDTO> pkDTOs) {
        log.info("【人才培养 - 批量新增排课:{}】",pkDTOs);
        Long userId = getUserId();
        List<Pk> pks = new ArrayList<>();
        for (PkDTO pkDTO : pkDTOs) {
            Pk pk = new Pk();
            pkDTO.setCjr(userId);
            pkDTO.setGxr(userId);
            BeanUtils.copyProperties(pkDTO,pk);
            pks.add(pk);
        }
        return pkService.saveBatch(pks);
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
