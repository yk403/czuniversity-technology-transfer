package com.itts.userservice.mapper.js;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.userservice.model.js.JsCdCzGl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 角色菜单操作关联表 Mapper 接口
 * </p>
 *
 * @author liuyingming
 * @since 2021-04-15
 */
public interface JsCdCzGlMapper extends BaseMapper<JsCdCzGl> {

    /**
     * 通过角色ID和菜单ID获取
     */
    @Select("SELECT * " +
            "FROM t_js_cd_cz_gl " +
            "WHERE js_id = #{jsId} AND cd_id = #{cdId}")
    List<JsCdCzGl> getByJsIdAndCdId(@Param("jsId") Long jsId, @Param("cdId") Long cdId);

}
