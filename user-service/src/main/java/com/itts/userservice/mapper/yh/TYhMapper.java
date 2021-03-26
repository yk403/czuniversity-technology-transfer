package com.itts.userservice.mapper.yh;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.userservice.model.yh.TYh;
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
public interface TYhMapper extends BaseMapper<TYh> {

    /**
     * 通过账号获取用户信息
     */
    @Select("SELECT * FROM t_yh WHERE yhm = #{userName}")
    TYh getByUserName(String userName);

}
