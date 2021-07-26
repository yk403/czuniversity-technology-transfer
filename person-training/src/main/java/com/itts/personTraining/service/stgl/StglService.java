package com.itts.personTraining.service.stgl;

import com.itts.personTraining.model.lmgl.Lmgl;
import com.itts.personTraining.model.stgl.Stgl;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fuli
 * @since 2021-07-13
 */
public interface StglService extends IService<Stgl> {

    List<Stgl> findList(Long jgId);

    Stgl add(Stgl stgl);

    Stgl update(Stgl stgl);

    Stgl get(Long id);

    boolean delete(Long id);
    boolean use(Long id);
}
