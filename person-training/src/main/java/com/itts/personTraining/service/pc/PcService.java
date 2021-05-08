package com.itts.personTraining.service.pc;


import com.github.pagehelper.PageInfo;
import com.itts.personTraining.model.pc.Pc;

import java.util.List;

/**
 * <p>
 * 批次表 服务类
 * </p>
 *
 * @author FL
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
     * 获取通过id
     */
    List<Pc> getList(List<Long> ids);
    /**
     * 新增
     *
     */
    boolean add(Pc pc);
    /**
     * 更新
     */
    boolean update(Pc pc);
    /**
     * 批量更新
     */
    boolean updateBatch(List<Long> ids);

    /**
     * 根据数据字典ID查询批次信息
     * @param sjzdId
     * @return
     */
    List<Pc> getBySjzdId(Long sjzdId);

    /**
     * 删除批次
     * @param pc
     * @return
     */
    boolean delete(Pc pc);
}
