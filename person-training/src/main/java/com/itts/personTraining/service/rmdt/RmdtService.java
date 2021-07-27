package com.itts.personTraining.service.rmdt;

import com.itts.personTraining.model.rmdt.Rmdt;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fuli
 * @since 2021-07-26
 */
public interface RmdtService extends IService<Rmdt> {


    Boolean use(Long id);
    Boolean up(Long jgId,Long id);
    Boolean down(Long jgId,Long id);
}
