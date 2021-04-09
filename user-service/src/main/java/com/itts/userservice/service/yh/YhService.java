package com.itts.userservice.service.yh;

import com.github.pagehelper.PageInfo;
import com.itts.userservice.dto.JsDTO;
import com.itts.userservice.dto.MenuDTO;
import com.itts.userservice.dto.YhDTO;
import com.itts.userservice.model.yh.Yh;
import com.itts.userservice.vo.YhVO;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author fl
 * @since 2021-03-18
 */
public interface YhService {

    /**
     * 查询列表
     */
    PageInfo<YhDTO> findByPage(Integer pageNum, Integer pageSize, String type, Long groupId);

    /**
     * 获取详情
     */
    Yh get(Long id);

    /**
     *通过用户名获取用户信息
     */
    Yh getByUserName(String userName);

    /**
     * 新增
     */
    Yh add(Yh Yh);

    /**
     * 更新
     */
    Yh update(Yh Yh);

    /**
     * 查询角色菜单目录
     * @param
     * @return
     */
    YhVO findMenusByUserID(Long userId, String systemType);
}
