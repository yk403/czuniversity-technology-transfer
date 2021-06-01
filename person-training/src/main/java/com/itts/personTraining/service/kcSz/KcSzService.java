package com.itts.personTraining.service.kcSz;

import com.itts.personTraining.model.kcSz.KcSz;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itts.personTraining.model.sz.Sz;

import java.util.List;

/**
 * <p>
 * 课程师资关系表 服务类
 * </p>
 *
 * @author Austin
 * @since 2021-04-25
 */
public interface KcSzService extends IService<KcSz> {

    /**
     * 根据kcId查询师资ids
     * @param kcId
     * @return
     */
    List<Sz> get(Long kcId);
}
