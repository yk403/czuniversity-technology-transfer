package com.itts.personTraining.service.lbt;


import com.baomidou.mybatisplus.extension.service.IService;
import com.itts.personTraining.model.lbt.Lbt;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fuli
 * @since 2021-07-13
 */
public interface LbtService extends IService<Lbt> {

    /**
     * 列表
     * @param jgId
     * @return
     */
    List<Lbt> findList(Long jgId);

    /**
     * 新增
     * @param lbt
     * @return
     */
    Lbt add(Lbt lbt);

    /**
     * 更新
     * @param lbt
     * @return
     */
    Lbt update(Lbt lbt);

    /**
     * 获取
     * @param id
     * @return
     */
    Lbt get(Long id);

    /**
     * 删除
     * @param id
     * @return
     */
    boolean delete(Long id);
}
