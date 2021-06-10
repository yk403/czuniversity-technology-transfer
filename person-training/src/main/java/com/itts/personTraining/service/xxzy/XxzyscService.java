package com.itts.personTraining.service.xxzy;


import com.baomidou.mybatisplus.extension.service.IService;
import com.itts.personTraining.model.xxzy.Xxzysc;

/**
 * <p>
 * 学习资源收藏 服务类
 * </p>
 *
 * @author liuyingming
 * @since 2021-06-09
 */
public interface XxzyscService extends IService<Xxzysc> {

    /**
     * 新增
     */
    Xxzysc add(Xxzysc xxzysc, Long userId);

    /**
     * 取消收藏
     */
    void delete(Long id);

}
