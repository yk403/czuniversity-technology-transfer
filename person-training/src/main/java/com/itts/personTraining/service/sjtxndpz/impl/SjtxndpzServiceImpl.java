package com.itts.personTraining.service.sjtxndpz.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.personTraining.enums.SjtxndpzEnum;
import com.itts.personTraining.mapper.sjtxndpz.SjtxndpzMapper;
import com.itts.personTraining.model.sjtxndpz.Sjtxndpz;
import com.itts.personTraining.service.sjtxndpz.SjtxndpzService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fuli
 * @since 2021-09-07
 */
@Service
public class SjtxndpzServiceImpl extends ServiceImpl<SjtxndpzMapper, Sjtxndpz> implements SjtxndpzService {

    @Resource
    private SjtxndpzMapper sjtxndpzMapper;
    @Override
    public Sjtxndpz get(Long id, String mc) {
        Sjtxndpz easy = sjtxndpzMapper.selectOne(new QueryWrapper<Sjtxndpz>().eq("sjtxpz_id", id)
                .eq("nd", mc)
                .eq("sfsc", false));
        return easy;
    }
}
