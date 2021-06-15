package com.itts.personTraining.service.sj.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.dto.SjDTO;
import com.itts.personTraining.model.sj.Sj;
import com.itts.personTraining.mapper.sj.SjMapper;
import com.itts.personTraining.model.xsCj.XsCj;
import com.itts.personTraining.service.sj.SjService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;

/**
 * <p>
 * 实践表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-05-28
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SjServiceImpl extends ServiceImpl<SjMapper, Sj> implements SjService {

    @Autowired
    private  SjService sjService;
    @Resource
    private SjMapper sjMapper;


    /**
     * 获取实践列表
     * @param pageNum
     * @param pageSize
     * @param pcId
     * @param sjlx
     * @return
     */
    @Override
    public PageInfo<SjDTO> findByPage(Integer pageNum, Integer pageSize, Long pcId, String sjlx, String name) {
        log.info("【人才培养 - 根据pcId:{},实践类型:{},姓名/学号:{}获取实践列表】",pcId,sjlx,name);
        PageHelper.startPage(pageNum, pageSize);
        List<SjDTO> sjDTOs = sjMapper.getByCondition(pcId, sjlx, name);
        return new PageInfo<>(sjDTOs);
    }

    /**
     * 删除实践
     * @param sjDTO
     * @return
     */
    @Override
    public boolean delete(SjDTO sjDTO) {
        log.info("【人才培养 - 查询所有实践】");
        sjDTO.setGxr(getUserId());
        sjDTO.setSfsc(true);
        Sj sj = new Sj();
        BeanUtils.copyProperties(sjDTO,sj);
        return sjService.updateById(sj);
    }

    /**
     * 实践批量下发
     * @param ids
     * @return
     */
    @Override
    public boolean issueBatch(List<Long> ids) {
        log.info("【人才培养 - 实践批量下发,ids:{}】",ids);
        List<Sj> sjList = sjMapper.selectBatchIds(ids);
        Long userId = getUserId();
        for (Sj sj : sjList) {
            sj.setGxr(userId);
            sj.setSfxf(true);
        }
        return sjService.updateBatchById(sjList);
    }

    /**
     * 根据用户id查询实践信息列表
     * @return
     */
    @Override
    public List<SjDTO> findByYhId() {
        Long userId = getUserId();
        log.info("【人才培养 - 根据用户id:{}查询实践信息列表】",userId);
        List<SjDTO> sjDTOs = sjMapper.findByCondition(userId,null);
        return sjDTOs;
    }

    /**
     * 新增实践(前)
     * @param sjDTO
     * @return
     */
    @Override
    public boolean userAdd(SjDTO sjDTO) {
        log.info("【人才培养 - 新增实践(前):{}】",sjDTO);
        Long userId = getUserId();
        sjDTO.setCjr(userId);
        sjDTO.setGxr(userId);
        Sj sj = new Sj();
        BeanUtils.copyProperties(sjDTO,sj);
        return sjService.save(sj);
    }

    /**
     * 更新实践(前)
     * @param sjDTO
     * @return
     */
    @Override
    public boolean userUpdate(SjDTO sjDTO) {
        log.info("【人才培养 - 更新实践:{}】",sjDTO);
        sjDTO.setGxr(getUserId());
        Sj sj = new Sj();
        BeanUtils.copyProperties(sjDTO,sj);
        return sjService.updateById(sj);
    }

    /**
     * 根据创建人查询实践信息(前)
     * @return
     */
    @Override
    public List<SjDTO> findByCjr() {
        Long userId = getUserId();
        log.info("【人才培养 - 根据创建人:{}查询实践信息(前)】",userId);
        List<SjDTO> sjDTOs = sjMapper.findByCondition(null,userId);
        return sjDTOs;
    }

    /**
     * 查询所有实践
     * @return
     */
    @Override
    public List<SjDTO> getAll() {
        log.info("【人才培养 - 查询所有实践】");
        List<SjDTO> sjDTOs = sjMapper.getByCondition(null,null,null);
        return sjDTOs;
    }

    /**
     * 新增实践
     * @param sjDTO
     * @return
     */
    @Override
    public boolean add(SjDTO sjDTO) {
        log.info("【人才培养 - 新增实践:{}】",sjDTO);
        Long userId = getUserId();
        sjDTO.setCjr(userId);
        sjDTO.setGxr(userId);
        Sj sj = new Sj();
        BeanUtils.copyProperties(sjDTO,sj);
        return sjService.save(sj);
    }

    /**
     * 根据id查询实践详情
     * @param id
     * @return
     */
    @Override
    public SjDTO get(Long id) {
        log.info("【人才培养 - 根据id:{}查询实践详情】",id);
        SjDTO sjDTO = sjMapper.getById(id);
        return sjDTO;
    }

    /**
     * 更新实践
     * @param sjDTO
     * @return
     */
    @Override
    public boolean update(SjDTO sjDTO) {
        log.info("【人才培养 - 更新实践:{}】",sjDTO);
        sjDTO.setGxr(getUserId());
        Sj sj = new Sj();
        BeanUtils.copyProperties(sjDTO,sj);
        return sjService.updateById(sj);
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
