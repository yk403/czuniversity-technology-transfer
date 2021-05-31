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
    List<SjDTO> getAll();

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
     * @return
     */
    PageInfo<SjDTO> findByPage(Integer pageNum, Integer pageSize, Long pcId, String sjlx);
}
