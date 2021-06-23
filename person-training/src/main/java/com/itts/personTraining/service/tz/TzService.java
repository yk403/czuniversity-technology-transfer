package com.itts.personTraining.service.tz;

import com.itts.personTraining.model.tz.Tz;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 通知表 服务类
 * </p>
 *
 * @author Austin
 * @since 2021-06-21
 */
public interface TzService extends IService<Tz> {

    /**
     * 根据用户类别查询通知信息(前)
     * @return
     */
    Tz findByCategory();
}
