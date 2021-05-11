package com.itts.personTraining.service.kcXs;

import com.itts.personTraining.model.kcXs.KcXs;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程学生关系表 服务类
 * </p>
 *
 * @author fl
 * @since 2021-05-06
 */
public interface KcXsService  {

    KcXs insert(KcXs kcXs);
    Boolean delete(Long xsId);
}
