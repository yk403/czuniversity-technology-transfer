package com.itts.userservice.service.cz;

import com.github.pagehelper.PageInfo;
import com.itts.userservice.dto.CzDTO;
import com.itts.userservice.model.cz.Cz;

import java.util.List;

/**
 * <p>
 * 操作表 服务类
 * </p>
 *
 * @author fl
 * @since 2021-03-19
 */
public interface CzService {
    /**
     * 查询列表
     */
    PageInfo<Cz> findByPage(Integer pageNum, Integer pageSize);

    /**
     * 获取详情
     */
    Cz get(Long id);

    /**
     * 新增
     */
    Cz add(Cz Cz);

    /**
     * 更新
     */
    Cz update(Cz Cz);

    /**
     * 查询操作按钮
     *
     * @param id
     * @return
     */
    List<CzDTO> findCz(Long id, Long cdid);

}
