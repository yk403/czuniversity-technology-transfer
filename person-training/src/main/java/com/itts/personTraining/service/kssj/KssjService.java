package com.itts.personTraining.service.kssj;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itts.personTraining.model.kssj.Kssj;
import com.itts.personTraining.request.kssj.AddKssjRequest;
import com.itts.personTraining.request.kssj.RandomKssjRequest;
import com.itts.personTraining.request.kssj.UpdateKssjRequest;
import com.itts.personTraining.vo.kssj.GetKssjVO;
import com.itts.personTraining.vo.sjpz.SjpzVO;

/**
 * <p>
 * 考试试卷 服务类
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-14
 */
public interface KssjService extends IService<Kssj> {

    /**
     * 详情
     */
    GetKssjVO get(Long id);

    Boolean randomAdd(RandomKssjRequest randomKssjRequest);
    /**
     * 新增
     */
    Kssj add(AddKssjRequest addKssjRequest);

    /**
     * 更新
     */
    Kssj update(UpdateKssjRequest updateKssjRequest, Kssj kssj, Long userId);
}
