package com.itts.userservice.mapper.cd;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.userservice.dto.GetCdAndCzDTO;
import com.itts.userservice.dto.GetCdCzGlDTO;
import com.itts.userservice.model.cd.Cd;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author fl
 * @since 2021-03-24
 */
public interface CdMapper extends BaseMapper<Cd> {
}
