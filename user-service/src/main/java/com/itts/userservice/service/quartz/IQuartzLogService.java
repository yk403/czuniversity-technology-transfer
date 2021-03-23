package com.itts.userservice.service.quartz;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itts.userservice.model.quartz.QuartzLog;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 定时任务日志 服务类
 * </p>
 *
 * @author zs
 * @since 2020-03-24
 */
public interface IQuartzLogService extends IService<QuartzLog> {
    /**
     * 导出定时任务日志
     * @param quartzLogs 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<QuartzLog> quartzLogs, HttpServletResponse response) throws IOException;


}
