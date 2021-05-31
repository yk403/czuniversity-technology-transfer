package com.itts.personTraining.service.sj.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.personTraining.dto.SjDTO;
import com.itts.personTraining.model.sj.Sj;
import com.itts.personTraining.mapper.sj.SjMapper;
import com.itts.personTraining.service.sj.SjService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    public PageInfo<SjDTO> findByPage(Integer pageNum, Integer pageSize, Long pcId, String sjlx) {
        log.info("【人才培养 - 根据pcId:{},实践类型:{}获取实践列表】",pcId,sjlx);
        PageHelper.startPage(pageNum, pageSize);
        List<SjDTO> sjDTOs = sjMapper.getByCondition(pcId, sjlx);
        return new PageInfo<>(sjDTOs);
    }

    /**
     * 查询所有实践
     * @return
     */
    @Override
    public List<SjDTO> getAll() {
        log.info("【人才培养 - 查询所有实践】");
        List<SjDTO> sjDTOs = sjMapper.getByCondition();
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

        return false;
    }

    /**
     * 根据id查询实践详情
     * @param id
     * @return
     */
    @Override
    public SjDTO get(Long id) {
        log.info("【人才培养 - 根据id:{}查询实践详情】",id);
        SjDTO sjDTO = sjMapper.getByCondition(id);
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
