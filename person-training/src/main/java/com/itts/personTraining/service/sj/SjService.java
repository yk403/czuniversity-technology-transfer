package com.itts.personTraining.service.sj;

import com.github.pagehelper.PageInfo;
import com.itts.personTraining.dto.SjDTO;
import com.itts.personTraining.model.sj.Sj;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 实践表 服务类
 * </p>
 *
 * @author Austin
 * @since 2021-05-28
 */
public interface SjService extends IService<Sj> {

    /**
     * 查询所有实践
     * @return
     */
    List<SjDTO> getAll(Long fjjgId);

    /**
     * 新增实践
     * @param sjDTO
     * @return
     */
    boolean add(SjDTO sjDTO);

    /**
     * 根据id查询实践详情
     * @param id
     * @return
     */
    SjDTO get(Long id);

    /**
     * 更新实践
     * @param sjDTO
     * @return
     */
    boolean update(SjDTO sjDTO);

    /**
     * 获取实践列表
     * @param pageNum
     * @param pageSize
     * @param pcId
     * @param sjlx
     * @param name
     * @param export
     * @param fjjgId
     * @return
     */
    PageInfo<SjDTO> findByPage(Integer pageNum, Integer pageSize, Long pcId, String sjlx, String name, Integer export, Long fjjgId);

    /**
     * 删除实践
     * @param sjDTO
     * @return
     */
    boolean delete(SjDTO sjDTO);

    /**
     * 实践批量下发
     * @param ids
     * @return
     */
    boolean issueBatch(List<Long> ids);

    /**
     * 根据用户id查询实践信息列表
     * @return
     */
    List<SjDTO> findByYhId();

    /**
     * 新增实践(前)
     * @param sjDTO
     * @return
     */
    boolean userAdd(SjDTO sjDTO);

    /**
     * 更新实践(前)
     * @param sjDTO
     * @return
     */
    boolean userUpdate(SjDTO sjDTO);

    /**
     * 根据用户类别查询实践信息(前)
     * @return
     */
    List<SjDTO> findByCategory(Long pcId);

}
