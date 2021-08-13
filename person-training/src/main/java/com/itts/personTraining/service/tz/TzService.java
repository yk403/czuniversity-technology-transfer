package com.itts.personTraining.service.tz;

import com.github.pagehelper.PageInfo;
import com.itts.personTraining.dto.StuDTO;
import com.itts.personTraining.dto.TzCountDTO;
import com.itts.personTraining.dto.TzDTO;
import com.itts.personTraining.model.tz.Tz;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 通知表 服务类
 * </p>
 *
 * @author Austin
 * @since 2021-06-21
 */
public interface TzService extends IService<Tz> {

    /**
     * 根据用户类别查询通知信息(前)
     * @param pageNum
     * @param pageSize
     * @param tzlx
     * @return
     */
    PageInfo<TzDTO> findByCategory(Integer pageNum,Integer pageSize,String tzlx);

    /**
     * 根据通知id查询通知信息
     * @param id
     * @return
     */
    TzDTO getTzDTOById(Long id);

    /**
     * 根据用户类别查询通知数
     * @return
     */
    TzCountDTO getTzCountByCategory();
}
