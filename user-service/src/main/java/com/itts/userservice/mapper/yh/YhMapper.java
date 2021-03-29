package com.itts.userservice.mapper.yh;

import com.itts.userservice.dto.JsDTO;
import com.itts.userservice.dto.MenuDTO;
import com.itts.userservice.model.yh.Yh;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author lym
 * @since 2021-03-18
 */
public interface YhMapper extends BaseMapper<Yh> {

    /**
     * 查询菜单目录
     * @param id
     * @return
     */
    List<JsDTO> findByUserId(Long id);
}
