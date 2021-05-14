package com.itts.personTraining.service.ksExp.impl;

import com.itts.personTraining.dto.KsExpDTO;
import com.itts.personTraining.mapper.szKsExp.SzKsExpMapper;
import com.itts.personTraining.model.ksExp.KsExp;
import com.itts.personTraining.mapper.ksExp.KsExpMapper;
import com.itts.personTraining.service.ksExp.KsExpService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 考试扩展表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-05-13
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class KsExpServiceImpl extends ServiceImpl<KsExpMapper, KsExp> implements KsExpService {

    @Autowired
    private KsExpService ksExpService;
    @Resource
    private KsExpMapper ksExpMapper;
    @Resource
    private SzKsExpMapper szKsExpMapper;

    /**
     * 根据考试id查询考试扩展信息
     * @param id
     * @return
     */
    @Override
    public List<KsExpDTO> get(Long id) {
        log.info("【人才培养 - 根据考试id:{}查询考试扩展信息】",id);
        List<KsExpDTO> ksExpDTOs = ksExpMapper.findByKsId(id);
        for (KsExpDTO ksExpDTO : ksExpDTOs) {
            List<Long> szIds = szKsExpMapper.selectByKsExpId(ksExpDTO.getId());
            ksExpDTO.setSzIds(szIds);
        }
        return ksExpDTOs;
    }
}
