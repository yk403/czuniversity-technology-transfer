package com.itts.personTraining.service.kc;

import com.github.pagehelper.PageInfo;
import com.itts.personTraining.dto.KcDTO;
import com.itts.personTraining.dto.KcXsXfDTO;
import com.itts.personTraining.dto.KcbDTO;
import com.itts.personTraining.model.kc.Kc;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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
     * 查询课程列表
     * @param pageNum
     * @param pageSize
     * @param kclx
     * @param name
     * @param jylx
     * @param xylx
     * @param fjjgId
     * @param userType
     * @return
     */
    PageInfo<KcDTO> findByPage(Integer pageNum, Integer pageSize, String kclx, String name, String jylx, String xylx, Long fjjgId, String userType);

    /**
     * 新增课程
     * @param kcDTO
     */
    boolean add(KcDTO kcDTO);

    /**
     * 更新课程
     * @param kcDTO
     * @return
     */
    boolean update(KcDTO kcDTO);

    /**
     * 根据id查询课程信息
     * @param id
     * @return
     */
    KcDTO get(Long id);

    /**
     * 课程批量下发
     * @param ids
     * @return
     */
    boolean issueBatch(List<Long> ids);

    /**
     * 删除课程
     * @param kcDTO
     * @return
     */
    boolean delete(KcDTO kcDTO);

    /**
     * 查询所有课程
     * @param xylx
     * @param fjjgId
     * @return
     */
    List<KcDTO> getByCondition(String xylx, Long fjjgId);
    /**
     * 根据用户id查询课程列表
     * @param pcId
     * @return
     */
    List<KcXsXfDTO> findByYh(Long pcId);

    /**
     * 查询课程表
     * @param pcId
     * @return
     */
    List<KcbDTO> findByPcId(@Param("pcId") Long pcId);

    /**
     * 根据学员类型查询课程列表
     * @param xylx
     * @param jylx
     * @return
     */
    List<KcDTO> findByXylx(String xylx,String jylx);
}
