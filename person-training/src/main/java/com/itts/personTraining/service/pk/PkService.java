package com.itts.personTraining.service.pk;

import com.github.pagehelper.PageInfo;
import com.itts.personTraining.dto.PkDTO;
import com.itts.personTraining.model.pk.Pk;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * @param skqsnyr
     * @param skjsnyr
     * @param pcId
     * @return
     */
    Map<String, List<PkDTO>> findByPage(String skqsnyr, String skjsnyr, Long pcId);

    /**
     * 根据id查询排课详情
     * @param id
     * @return
     */
    PkDTO get(Long id);

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
     * @param pkDTO
     * @return
     */
    boolean delete(PkDTO pkDTO);

    /**
     * 批量新增排课
     * @param pkDTOs
     * @return
     */
    boolean addList(List<PkDTO> pkDTOs);
}
