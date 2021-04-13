package com.itts.userservice.mapper.yh;

import com.itts.userservice.model.yh.YhJsGl;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户角色关联表 Mapper 接口
 * </p>
 *
 * @author fl
 * @since 2021-03-24
 */
@Repository
public interface YhJsGlMapper extends BaseMapper<YhJsGl> {

    @Update("UPDATE t_yh_js_gl" +
            "SET sfsc = true" +
            "WHERE yh_id = #{userId}")
    void deleteByUserId(@Param("userId") Long userId);
}
