package com.itts.personTraining.service.sjtxpz;

import com.itts.personTraining.model.sjtxpz.Sjtxpz;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fuli
 * @since 2021-09-07
 */
public interface SjtxpzService extends IService<Sjtxpz> {

    Sjtxpz get(Long id,String mc);
}
