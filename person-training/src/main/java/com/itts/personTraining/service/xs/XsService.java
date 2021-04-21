package com.itts.personTraining.service.xs;

import com.github.pagehelper.PageInfo;
import com.itts.personTraining.model.xs.Xs;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 学生表 服务类
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
public interface XsService extends IService<Xs> {

    /**
     * 查询学员列表
     * @param pageNum
     * @param pageSize
     * @param pcId
     * @param jyxs
     * @return
     */
    PageInfo<Xs> findByPage(Integer pageNum, Integer pageSize, Long pcId, Long xslbId, String jyxs);

    /**
     * 根据id查询学员信息
     * @param id
     * @return
     */
    Xs get(Long id);

    /**
     * 新增学员
     * @param xs
     * @return
     */
    boolean add(Xs xs);

    /**
     * 更新学员
     * @param xs
     * @return
     */
    boolean update(Xs xs);

    /**
     * 删除学员
     * @param xs
     * @return
     */
    boolean delete(Xs xs);
}
