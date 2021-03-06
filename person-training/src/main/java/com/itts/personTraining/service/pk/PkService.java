package com.itts.personTraining.service.pk;

import com.github.pagehelper.PageInfo;
import com.itts.personTraining.dto.PkDTO;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.model.pk.Pk;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
     * 查询排课信息
     * @param pcId
     * @return
     */
    List<PkDTO> findPkInfo(Long pcId);

    /**
     * 根据id查询排课详情
     * @param id
     * @return
     */
    Pk get(Long id);

    /**
     * 新增排课
     * @param pkDTO
     * @return
     */
    boolean add(PkDTO pkDTO);

    /**
     * 更新排课
     * @param pkDTO
     * @return
     */
    boolean update(PkDTO pkDTO);

    /**
     * 删除排课
     * @param pk
     * @return
     */
    boolean delete(Pk pk);

    /**
     * 批量新增排课
     * @param pkDTOs
     * @return
     */
    boolean addList(List<PkDTO> pkDTOs);

    /**
     * 根据开学日期查询所有排课信息
     * @param kxrq
     * @return
     */
    List<PkDTO> findPkByKxrq(Date kxrq);

    /**
     * 根据用户id查询批次ids(前)
     * @return
     */
    List<Pc> getPcsByYhId();
}
