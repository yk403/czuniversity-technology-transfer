package com.itts.personTraining.service.zy;

import com.itts.personTraining.model.zy.Zy;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 专业表 服务类
 * </p>
 *
 * @author Austin
 * @since 2021-04-26
 */
public interface ZyService extends IService<Zy> {

    /**
     * 根据学院id查询专业信息
     * @return
     */
    List<Zy> getByXyId(Long xyId);

    /**
     * 查询所有专业信息
     * @return
     */
    List<Zy> getAll();

    /**
     * 新增专业
     * @param zy
     * @return
     */
    boolean add(Zy zy);

    /**
     * 根据id查询专业信息
     * @param id
     * @return
     */
    Zy get(Long id);

    /**
     * 更新专业
     * @param zy
     * @return
     */
    boolean update(Zy zy);

    /**
     * 删除专业
     * @param zy
     * @return
     */
    boolean delete(Zy zy);
}
