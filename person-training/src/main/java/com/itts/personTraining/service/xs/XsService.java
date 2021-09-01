package com.itts.personTraining.service.xs;

import com.github.pagehelper.PageInfo;
import com.itts.personTraining.dto.JwglDTO;
import com.itts.personTraining.dto.StuDTO;
import com.itts.personTraining.dto.XsMsgDTO;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.model.xs.Xs;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 学生表 服务类
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
public interface XsService extends IService<Xs> {

    /**
     * 查询学员列表
     * @param pageNum
     * @param pageSize
     * @param pcId
     * @param xslbmc
     * @param jyxs
     * @param name
     * @param fjjgId
     * @return
     */
    PageInfo<StuDTO> findByPage(Integer pageNum, Integer pageSize, Long pcId, String xslbmc, String jyxs, String name,Long qydsId,Long yzydsId,Long fjjgId);

    List<StuDTO> findExport( Long pcId, String xslbmc, String jyxs, String name, Long fjjgId);

    /**
     * 根据id查询学员信息
     * @param id
     * @return
     */
    StuDTO get(Long id);

    /**
     * 新增学员
     * @param stuDTO
     * @return
     */
    boolean add(StuDTO stuDTO,String token);

    /**
     * 新增学员(外部调用)
     * @param stuDTO
     * @return
     */
    boolean addUser(StuDTO stuDTO);

    /**
     * 更新学员
     * @param stuDTO
     * param token
     * @return
     */
    boolean update(StuDTO stuDTO,String token);

    /**
     * 删除学员
     * @param stuDTO
     * @return
     */
    boolean delete(StuDTO stuDTO);

    Boolean addKcXs(Long id,Long kcId);

    /**
     * 根据条件查询学员信息
     * @param xh
     * @param lxdh
     * @param yhId
     * @return
     */
    StuDTO selectByCondition(String xh, String lxdh, Long yhId);

    /**
     * 查询所有学员详情
     * @return
     */
    List<StuDTO> getAll();

    /**
     * 查询学生综合信息
     * @return
     */
    XsMsgDTO getByYhId();

    /**
     * 根据用户id查询批次信息(前)
     * @return
     */
    List<Pc> getPcByYhId();

    /**
     * 更新学生信息(前)
     * @param stuDTO
     * @return
     */
    boolean updateUser(StuDTO stuDTO);

    /**
     * 更新学员(外部调用)
     * @param xs
     * @return
     */
    boolean updateXs(Xs xs);
}
