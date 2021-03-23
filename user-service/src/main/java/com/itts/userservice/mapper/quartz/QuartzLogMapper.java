package com.itts.userservice.mapper.quartz;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.userservice.model.quartz.QuartzLog;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 定时任务日志 Mapper 接口
 * </p>
 *
 * @author zs
 * @since 2020-03-24
 */
@Repository
public interface QuartzLogMapper extends BaseMapper<QuartzLog> {

}
