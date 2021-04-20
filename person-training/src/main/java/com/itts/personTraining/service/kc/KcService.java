package com.itts.personTraining.service.kc;

import com.github.pagehelper.PageInfo;
import com.itts.personTraining.model.kc.Kc;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程表 服务类
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
public interface KcService extends IService<Kc> {

    /**
     * 查询列表
     * @param pageNum
     * @param pageSize
     * @param kclx
     * @param name
     * @return
     */
    PageInfo<Kc> findByPage(Integer pageNum, Integer pageSize, String kclx, String name);

    /**
     * 新增课程
     * @param kc
     */
    boolean add(Kc kc);

    /**
     * 更新课程
     * @param kc
     * @return
     */
    boolean update(Kc kc);

    /**
     * 根据id查询课程信息
     * @param id
     * @return
     */
    Kc get(Long id);

    /**
     * 课程批量下发
     * @param ids
     * @return
     */
    boolean issueBatch(List<Long> ids);

}
