package com.itts.userservice.mapper.cz;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.userservice.dto.CzDTO;
import com.itts.userservice.model.cz.Cz;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 操作表 Mapper 接口
 * </p>
 *
 * @author lym
 * @since 2021-03-19
 */
@Repository
public interface CzMapper extends BaseMapper<Cz> {

    /**
     * 查询当前菜单的操作
     *
     * @param jsid
     * @param cdid
     * @return
     */
    List<CzDTO> findCdCzGlByJsIdAndCdId(@Param("jsid") Long jsid, @Param("cdid") Long cdid);

    /**
     * 通过菜单ID获取当前菜单拥有的操作
     */
    @Select("SELECT cz.id, cz.czmc, cz.czbm " +
            "    FROM t_cz cz " +
            "LEFT JOIN t_cd_cz_gl cdczgl ON cz.id = cdczgl.cz_id " +
            "WHERE cz.sfsc = false " +
            "      AND cdczgl.cd_id = #{cdId} ")
    List<Cz> findByCdId(@Param("cdId") Long cdId);
}
