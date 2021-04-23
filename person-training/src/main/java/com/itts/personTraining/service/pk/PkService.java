package com.itts.personTraining.service.pk;

import com.github.pagehelper.PageInfo;
import com.itts.personTraining.model.pk.Pk;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 排课表 服务类
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
public interface PkService extends IService<Pk> {

    /**
     * 查询排课列表
     * @param pageNum
     * @param pageSize
     * @param pch
     * @return
     */
    PageInfo<Pk> findByPage(Integer pageNum, Integer pageSize, String pch);

    /**
     * 根据id查询排课详情
     * @param id
     * @return
     */
    Pk get(Long id);

    /**
     * 新增排课
     * @param pk
     * @return
     */
    boolean add(Pk pk);

    /**
     * 更新排课
     * @param pk
     * @return
     */
    boolean update(Pk pk);

    /**
     * 删除排课
     * @param pk
     * @return
     */
    boolean delete(Pk pk);
}
