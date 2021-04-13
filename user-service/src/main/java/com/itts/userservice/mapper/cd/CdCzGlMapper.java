package com.itts.userservice.mapper.cd;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.userservice.dto.GetCdCzGlDTO;
import com.itts.userservice.model.cd.CdCzGl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 菜单操作关联表 Mapper 接口
 * </p>
 *
 * @author liuyingming
 * @since 2021-04-13
 */
@Repository
public interface CdCzGlMapper extends BaseMapper<CdCzGl> {

    @Select("SELECT tc.id, tc.czbm, tc.czmc FROM t_cz tc " +
            "LEFT JOIN t_cd_cz_gl tccg on tc.id = tccg.cz_id " +
            "WHERE tccg.cd_id = #{cdId}")
    List<GetCdCzGlDTO> getCdCzGlByCdId(@Param("cdId") Long cdId);

}
