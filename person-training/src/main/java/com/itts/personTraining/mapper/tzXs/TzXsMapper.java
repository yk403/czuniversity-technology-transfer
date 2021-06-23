package com.itts.personTraining.mapper.tzXs;

import com.itts.personTraining.model.tzXs.TzXs;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 通知学生关系表 Mapper 接口
 * </p>
 *
 * @author Austin
 * @since 2021-06-22
 */
public interface TzXsMapper extends BaseMapper<TzXs> {

    /**
     * 通过学生id,通知类型和读取状态查询通知数量
     * @param xsId
     * @return
     */
    Long getTzCountByXsIdAndTzlx(@Param("xsId") Long xsId,@Param("tzlx") String tzlx,@Param("sfdq") boolean sfdq);
}
