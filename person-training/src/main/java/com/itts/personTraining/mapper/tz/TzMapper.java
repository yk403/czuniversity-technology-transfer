package com.itts.personTraining.mapper.tz;

import com.itts.personTraining.dto.TzDTO;
import com.itts.personTraining.dto.XsMsgDTO;
import com.itts.personTraining.model.tz.Tz;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 通知表 Mapper 接口
 * </p>
 *
 * @author Austin
 * @since 2021-06-21
 */
public interface TzMapper extends BaseMapper<Tz> {

    /**
     * 根据学生id和通知类型查询通知列表
     * @param xsId
     * @param tzlx
     * @return
     */
    List<TzDTO> findTzDTOByXsIdAndTzlx(@Param("xsId") Long xsId, @Param("tzlx") String tzlx);

    /**
     * 根据id和学生id查询通知信息
     * @param id
     * @param xsId
     * @return
     */
    TzDTO getTzDTOByIdAndXsId(@Param("id") Long id, @Param("xsId") Long xsId);

    /**
     * 根据师资id和通知类型查询通知列表
     * @param szId
     * @param tzlx
     * @return
     */
    List<TzDTO> findTzDTOBySzIdAndTzlx(@Param("szId") Long szId, @Param("tzlx") String tzlx);

    /**
     * 根据id和师资id查询通知信息
     * @param id
     * @param szId
     * @return
     */
    TzDTO getTzDTOByIdAndSzId(@Param("id") Long id, @Param("szId") Long szId);
}
