package com.itts.personTraining.service.pcXs.impl;

import com.itts.personTraining.dto.StuDTO;
import com.itts.personTraining.model.pcXs.PcXs;
import com.itts.personTraining.mapper.pcXs.PcXsMapper;
import com.itts.personTraining.service.pcXs.PcXsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 批次学生关系表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-05-18
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class PcXsServiceImpl extends ServiceImpl<PcXsMapper, PcXs> implements PcXsService {

    @Resource
    private PcXsMapper pcXsMapper;

    /**
     * 根据pcId查询学生信息
     * @param pcId
     * @return
     */
    @Override
    public List<StuDTO> getByPcId(Long pcId) {
        log.info("【人才培养 - 根据pcId:{}查询学生ids】",pcId);
        List<StuDTO> stuDTOs = pcXsMapper.selectStuByPcId(pcId);
        return stuDTOs;
    }
}
