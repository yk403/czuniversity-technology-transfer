package com.itts.personTraining.mapper.xs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.personTraining.dto.JwglDTO;
import com.itts.personTraining.dto.StuDTO;
import com.itts.personTraining.dto.XsMsgDTO;
import com.itts.personTraining.model.xs.Xs;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 学生表 Mapper 接口
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
@Repository
public interface XsMapper extends BaseMapper<Xs> {


    /**
     * 课程学生关联
     */
    Boolean addKcList(@Param("id")Long id,@Param("kcId")Long kcId);

    /**
     * 分页条件查询
     * @param pcId
     * @param xslbmc
     * @param jyxs
     * @param name
     * @return
     */
    List<StuDTO> findXsList(@Param("pcId") Long pcId, @Param("xslbmc") String xslbmc, @Param("jyxs") String jyxs, @Param("name") String name,@Param("qydsId") Long qydsId,@Param("yzydsId") Long yzydsId);

    /**
     * 通过用户id查询学生信息
     * @param yhId
     * @return
     */
    XsMsgDTO getByYhId(@Param("yhId") Long yhId);

    /**
     * 通过学号和姓名查询学生信息
     * @param xh
     * @param xm
     * @return
     */
    Xs getByXhAndXm(@Param("xh") String xh, @Param("xm") String xm);

    /**
     * 根据原专业导师用户id查询学生ids
     * @param yhId
     * @return
     */
    List<Long> findXsIdsBySzYhId(@Param("yhId") Long yhId);

    /**
     * 通过批次id和报名方式(线下)查询学员ids(经纪人)
     * @param pcId
     * @param bmfs
     * @return
     */
    List<Long> findXsIdsByPcIdAndBmfs(@Param("pcId") Long pcId, @Param("bmfs") String bmfs);

    /**
     * 通过批次id查询学员ids(研究生)
     * @param pcId
     * @return
     */
    List<Long> findXsIdsByPcId(@Param("pcId") Long pcId);

    /**
     * 通过报名方式和xsids查询学生id集合
     * @param xsIdList
     * @param bmfs
     * @return
     */
    List<Long> findXsIdsByBmfs(@Param("xsIdList") List<Long> xsIdList, @Param("bmfs") String bmfs);

    /**
     * 根据原专业导师用户id查询学生ids
     * @param yhId
     * @return
     */
    List<Long> findXsIdsByQydsYhId(@Param("yhId") Long yhId);
}
