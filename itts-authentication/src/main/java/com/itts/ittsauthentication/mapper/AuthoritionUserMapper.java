package com.itts.ittsauthentication.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.ittsauthentication.bean.AuthoritionUser;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author lym
 * @since 2021-03-18
 */
@Repository
public interface AuthoritionUserMapper extends BaseMapper<AuthoritionUser> {

    /**
     * 通过账号获取用户信息
     */
    @Select("SELECT * FROM t_yh WHERE yhm = #{userName}")
    AuthoritionUser getByUserName(String userName);
}
