package com.itts.personTraining.service.xyKc;

import com.itts.personTraining.model.xyKc.XyKc;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 学院课程关系表 服务类
 * </p>
 *
 * @author Austin
 * @since 2021-04-23
 */
public interface XyKcService extends IService<XyKc> {

    /**
     * 新增学院课程关系
     * @param xyKc
     * @return
     */
    boolean add(XyKc xyKc);
}
