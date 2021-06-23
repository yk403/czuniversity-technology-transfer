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
    List<TzDTO> findTzDTOByXsIdAndTzlx(@Param("xsId") XsMsgDTO xsId, @Param("tzlx") String tzlx);
}
