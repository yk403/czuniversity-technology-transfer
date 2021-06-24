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

    List<JwglDTO> findJwglList(String string,String yx,Long pcId);
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
    List<StuDTO> findXsList(@Param("pcId") Long pcId, @Param("xslbmc") String xslbmc, @Param("jyxs") String jyxs, @Param("name") String name);

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
     * 根据师资用户id查询学生ids
     * @param yhId
     * @return
     */
    List<Long> findXsIdsBySzYhId(@Param("yhId") Long yhId);

}
