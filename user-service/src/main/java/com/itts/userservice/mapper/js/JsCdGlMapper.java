package com.itts.userservice.mapper.js;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.userservice.model.js.JsCdGl;
import com.itts.userservice.vo.GetJsCdGlVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 角色菜单关联表 Mapper 接口
 * </p>
 *
 * @author fl
 * @since 2021-03-24
 */
public interface JsCdGlMapper extends BaseMapper<JsCdGl> {

    /**
     * 通过角色ID获取当前角色拥有的菜单
     */
    @Select("SELECT cd.id, cd.cdmc, cd.cdbm FROM t_cd cd " +
            "JOIN t_js_cd_gl jcg ON cd.id = jcg.cd_id " +
            "WHERE cd.sfsc = false " +
            "  AND jcg.sfsc = false " +
            "  AND jcg.js_id = #{jsId}")
    List<GetJsCdGlVO> getJsCdGlByJsId(@Param("jsId") Long jsId);
}
