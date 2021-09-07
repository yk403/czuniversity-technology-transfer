package com.itts.personTraining.service.sjtxpz.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.personTraining.enums.TkzyTypeEnum;
import com.itts.personTraining.mapper.sjtxpz.SjtxpzMapper;
import com.itts.personTraining.model.sjtxpz.Sjtxpz;
import com.itts.personTraining.service.sjtxpz.SjtxpzService;
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
public class SjtxpzServiceImpl extends ServiceImpl<SjtxpzMapper, Sjtxpz> implements SjtxpzService {

    @Resource
    private SjtxpzMapper sjtxpzMapper;
    @Override
    public Sjtxpz get(Long id, String mc) {
        Sjtxpz sjtxpz = sjtxpzMapper.selectOne(new QueryWrapper<Sjtxpz>().eq("sjpz_id", id)
                .eq("txmc", mc)
                .eq("sfsc", false));
        return sjtxpz;
    }
}
