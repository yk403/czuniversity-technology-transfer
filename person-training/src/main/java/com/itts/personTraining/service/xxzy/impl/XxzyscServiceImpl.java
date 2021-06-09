package com.itts.personTraining.service.xxzy.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.personTraining.mapper.xxzy.XxzyscMapper;
import com.itts.personTraining.model.xxzy.Xxzysc;
import com.itts.personTraining.service.xxzy.XxzyscService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 学习资源收藏 服务实现类
 * </p>
 *
 * @author liuyingming
 * @since 2021-06-09
 */
@Service
public class XxzyscServiceImpl extends ServiceImpl<XxzyscMapper, Xxzysc> implements XxzyscService {

    @Autowired
    private XxzyscMapper xxzyscMapper;

    /**
     * 新增收藏
     */
    @Override
    public Xxzysc add(Xxzysc xxzysc, Long userId) {

        xxzysc.setCjsj(new Date());
        xxzysc.setYhId(userId);

        xxzyscMapper.insert(xxzysc);

        return xxzysc;
    }

    /**
     * 取消收藏
     */
    @Override
    public void delete(Long id) {

        xxzyscMapper.deleteById(id);
    }
}
