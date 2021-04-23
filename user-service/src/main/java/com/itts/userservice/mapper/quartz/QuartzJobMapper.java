package com.itts.userservice.mapper.quartz;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.userservice.model.quartz.QuartzJob;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 定时任务 Mapper 接口
 * </p>
 *
 * @author zs
 * @since 2020-03-24
 */
@Repository
public interface QuartzJobMapper extends BaseMapper<QuartzJob> {
    /**
     * 查询启用的任务
     *
     * @return List
     */
    List<QuartzJob> findByIsPauseIsFalse();
}
