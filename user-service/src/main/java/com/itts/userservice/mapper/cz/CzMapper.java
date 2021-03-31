package com.itts.userservice.mapper.cz;

import com.itts.userservice.dto.CzDTO;
import com.itts.userservice.model.cz.Cz;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 操作表 Mapper 接口
 * </p>
 *
 * @author lym
 * @since 2021-03-19
 */
public interface CzMapper extends BaseMapper<Cz> {

    /**
     * 查询当前菜单的操作
     * @param jsid
     * @param cdid
     * @return
     */
    List<CzDTO> findcdcz(@Param("jsid") Long jsid,@Param("cdid") Long cdid);
}
