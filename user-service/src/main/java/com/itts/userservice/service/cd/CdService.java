package com.itts.userservice.service.cd;

import com.github.pagehelper.PageInfo;
import com.itts.userservice.dto.GetCdAndCzDTO;
import com.itts.userservice.dto.GetCdCzGlDTO;
import com.itts.userservice.model.cd.Cd;
import com.itts.userservice.request.AddCdRequest;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author lym
 * @since 2021-03-24
 */
public interface CdService {

    /**
     * 查询列表
     */
    PageInfo<GetCdAndCzDTO> findByPage(Integer pageNum, Integer pageSize, String name, String systemType, String modelType);

    /**
     * 获取详情
     */
    GetCdAndCzDTO get(Long id);

    /**
     * 新增
     */
    Cd add(AddCdRequest cd);

    /**
     * 更新
     */
    Cd update(Cd cd);

}
