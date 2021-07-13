package com.itts.personTraining.service.lmgl;


import com.baomidou.mybatisplus.extension.service.IService;
import com.itts.personTraining.model.lmgl.Lmgl;

import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fuli
 * @since 2021-07-13
 */
public interface LmglService extends IService<Lmgl> {


    List<Lmgl> findList(Long jgId);

    Lmgl add(Lmgl lmgl);

    Lmgl update(Lmgl lmgl);

    Lmgl get(Long id);

    boolean delete(Long id);

}
