package com.itts.userservice.service.quartz.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.userservice.common.quartz.utils.FileUtil;
import com.itts.userservice.mapper.quartz.QuartzLogMapper;
import com.itts.userservice.model.quartz.QuartzLog;
import com.itts.userservice.service.quartz.IQuartzLogService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 定时任务日志 服务实现类
 * </p>
 *
 * @author zs
 * @since 2020-03-24
 */
@Service
@Primary
public class QuartzLogServiceImpl extends ServiceImpl<QuartzLogMapper, QuartzLog> implements IQuartzLogService {

    /**
     * 导出定时任务日志
     *
     * @param quartzLogs 待导出的数据
     * @param response   /
     * @throws IOException /
     */
    @Override
    public void download(List<QuartzLog> quartzLogs, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (QuartzLog quartzLog : quartzLogs) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("任务名称", quartzLog.getJobName());
            map.put("Bean名称", quartzLog.getBeanName());
            map.put("执行方法", quartzLog.getMethodName());
            map.put("参数", quartzLog.getParams());
            map.put("表达式", quartzLog.getCronExpression());
            map.put("异常详情", quartzLog.getExceptionDetail());
            map.put("耗时/毫秒", quartzLog.getTime());
            map.put("状态", quartzLog.getIsSuccess() ? "成功" : "失败");
            map.put("创建日期", quartzLog.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
