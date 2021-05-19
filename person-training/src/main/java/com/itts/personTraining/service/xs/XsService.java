package com.itts.personTraining.service.xs;

import com.github.pagehelper.PageInfo;
import com.itts.personTraining.dto.JwglDTO;
import com.itts.personTraining.dto.StuDTO;
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
     * @return
     */
    PageInfo<StuDTO> findByPage(Integer pageNum, Integer pageSize, Long pcId, String xslbmc, String jyxs, String name);

    /**
     * 查询教务管理列表
     */
    PageInfo<JwglDTO> findJwglByPage(Integer pageNum, Integer pageSize,String string,String yx,Long pcId);
    /**
     * 根据id查询学员信息
     * @param id
     * @return
     */
    StuDTO get(Long id);

    StuDTO getByXh(String xh);
    /**
     * 新增学员
     * @param stuDTO
     * @return
     */
    boolean add(StuDTO stuDTO);

    /**
     * 更新学员
     * @param stuDTO
     * @return
     */
    boolean update(StuDTO stuDTO);

    /**
     * 删除学员
     * @param stuDTO
     * @return
     */
    boolean delete(StuDTO stuDTO);

    /**
     * 根据条件查询学员信息
     * @param xh
     * @return
     */
    List<Xs> selectByCondition(String xh);

    Boolean addKcXs(Long id,Long kcId);
}
