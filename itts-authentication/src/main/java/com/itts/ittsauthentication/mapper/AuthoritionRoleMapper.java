package com.itts.ittsauthentication.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.ittsauthentication.bean.AuthoritionRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author lym
 * @since 2021-03-19
 */
@Repository
public interface AuthoritionRoleMapper extends BaseMapper<AuthoritionRole> {

    /**
     * 通过用户ID获取用户角色列表
     */
    @Select("SELECT js.* FROM t_js js " +
            "JOIN t_yh_js_gl tyjg ON js.id = tyjg.js_id " +
            "WHERE yh_id = #{yhId}")
    List<AuthoritionRole> findByYhId(@Param("yhId") Long yhId);

}
