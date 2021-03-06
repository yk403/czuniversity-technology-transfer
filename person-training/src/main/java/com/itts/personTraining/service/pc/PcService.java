package com.itts.personTraining.service.pc;


import com.github.pagehelper.PageInfo;
import com.itts.personTraining.model.kc.Kc;
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
    PageInfo<Pc> findByPage(Integer pageNum, Integer pageSize, String name, String jylx, String lx, Long fjjgId, String userType);
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
     * 根据教育类型查询批次信息
     * @param jylx
     * @param fjjgId
     * @return
     */
    List<Pc> getByJylx(String jylx,Long fjjgId);

    /**
     * 删除批次
     * @param pc
     * @return
     */
    boolean delete(Pc pc);

    /**
     * 获取所有批次详情
     * @return
     */
    List<Pc> getAll(Long fjjgId);

    /**
     * 根据id查询课程信息列表
     * @param id
     * @return
     */
    List<Kc> getKcListById(Long id);

    /**
     * 根据用户查询批次列表
     * @return
     */
    List<Pc> findByYh();

    /**
     * 查询未录入批次
     * @return
     */
    List<Pc> findPcs();
}
