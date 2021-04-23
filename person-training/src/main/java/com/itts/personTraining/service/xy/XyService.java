package com.itts.personTraining.service.xy;

import com.itts.personTraining.model.xy.Xy;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 学院表 服务类
 * </p>
 *
 * @author Austin
 * @since 2021-04-23
 */
public interface XyService extends IService<Xy> {

    /**
     * 查询所有学院
     * @return
     */
    List<Xy> getAll();

    /**
     * 新增学院
     * @param xy
     * @return
     */
    boolean add(Xy xy);

    /**
     * 根据id查询学院
     * @param id
     * @return
     */
    Xy get(Long id);

    /**
     * 更新学院
     * @param xy
     * @return
     */
    boolean update(Xy xy);

    /**
     * 删除学院
     * @param xy
     * @return
     */
    boolean delete(Xy xy);
}
