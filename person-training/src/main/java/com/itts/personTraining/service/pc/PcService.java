package com.itts.personTraining.service.pc;


import com.github.pagehelper.PageInfo;
import com.itts.personTraining.model.pc.Pc;

/**
 * <p>
 * 批次表 服务类
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
public interface PcService  {

    /**
     * 获取列表 - 分页
     */
    PageInfo<Pc> findByPage(Integer pageNum, Integer pageSize);
    /**
     * 获取通过id
     */
    Pc get(Long id);
    /**
     * 新增
     *
     */
    Pc add(Pc pc);
    /**
     * 更新
     */
    Pc update(Pc pc);
}
