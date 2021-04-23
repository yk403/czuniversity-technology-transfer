package com.itts.userservice.service.quartz;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itts.common.utils.R;
import com.itts.userservice.model.quartz.QuartzJob;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 定时任务 服务类
 * </p>
 *
 * @author zs
 * @since 2020-03-24
 */
public interface IQuartzJobService extends IService<QuartzJob> {

    /**
     * 创建
     *
     * @param resources /
     * @return /
     */
    R create(QuartzJob resources);

    /**
     * 更改定时任务状态
     *
     * @param quartzJob /
     */
    void updateIsPause(QuartzJob quartzJob);

    /**
     * 导出定时任务
     *
     * @param quartzJobs 待导出的数据
     * @param response   /
     * @throws IOException /
     */
    void download(List<QuartzJob> quartzJobs, HttpServletResponse response) throws IOException;

    /**
     * 立即执行定时任务
     *
     * @param quartzJob /
     */
    R execution(QuartzJob quartzJob);

    /**
     * 删除任务
     *
     * @param ids
     */
    void delete(List<Long> ids);

    /**
     * 编辑
     *
     * @param quartzJob /
     */
    R update(QuartzJob quartzJob);
}
