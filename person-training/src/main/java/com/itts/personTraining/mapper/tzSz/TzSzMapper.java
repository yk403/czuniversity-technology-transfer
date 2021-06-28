package com.itts.personTraining.mapper.tzSz;

import com.itts.personTraining.model.tzSz.TzSz;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 通知师资表 Mapper 接口
 * </p>
 *
 * @author Austin
 * @since 2021-06-25
 */
public interface TzSzMapper extends BaseMapper<TzSz> {

    /**
     * 根据师资id和通知类型查询未读取通知数
     * @param szId
     * @param tzlx
     * @param sfdq
     * @return
     */
    Long getTzCountBySzIdAndTzlx(@Param("szId") Long szId, @Param("tzlx") String tzlx, @Param("sfdq") boolean sfdq);

    /**
     * 根据通知id和师资id更新通知师资表读取状态
     * @param tzId
     * @param szId
     * @return
     */
    int updateByTzIdAndSzId(@Param("tzId") Long tzId, @Param("szId") Long szId);
}
