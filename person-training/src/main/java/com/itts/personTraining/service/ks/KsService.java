package com.itts.personTraining.service.ks;

import com.github.pagehelper.PageInfo;
import com.itts.personTraining.dto.KsDTO;
import com.itts.personTraining.model.ks.Ks;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 考试表 服务类
 * </p>
 *
 * @author Austin
 * @since 2021-05-06
 */
public interface KsService extends IService<Ks> {

    /**
     * 查询考试列表
     * @param pageNum
     * @param pageSize
     * @param pcId
     * @param ksmc
     * @param kslx
     * @return
     */
    PageInfo<KsDTO> findByPage(Integer pageNum, Integer pageSize, Long pcId, String ksmc, Integer kslx);

    /**
     * 根据id查询考试详情
     * @param id
     * @return
     */
    KsDTO get(Long id);

    /**
     * 新增考试
     * @param ksDTO
     * @return
     */
    boolean add(KsDTO ksDTO);

    /**
     * 更新考试
     * @param ksDTO
     * @return
     */
    boolean update(KsDTO ksDTO);

    /**
     * 删除考试
     * @param ksDTO
     * @return
     */
    boolean delete(KsDTO ksDTO);

    /**
     * 考试批量下发
     * @param ids
     * @return
     */
    boolean issueBatch(List<Long> ids);
}
