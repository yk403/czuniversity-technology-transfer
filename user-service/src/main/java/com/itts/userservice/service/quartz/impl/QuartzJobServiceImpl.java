package com.itts.userservice.service.quartz.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.common.utils.R;
import com.itts.userservice.common.quartz.exception.BadRequestException;
import com.itts.userservice.common.quartz.utils.FileUtil;
import com.itts.userservice.service.quartz.IQuartzJobService;
import org.apache.logging.log4j.core.util.CronExpression;
import com.itts.userservice.common.quartz.utils.QuartzManage;
import com.itts.userservice.mapper.quartz.QuartzJobMapper;
import com.itts.userservice.model.quartz.QuartzJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 定时任务 服务实现类
 * </p>
 *
 * @author zs
 * @since 2020-03-24
 */
@Service
@Primary
public class QuartzJobServiceImpl extends ServiceImpl<QuartzJobMapper, QuartzJob> implements IQuartzJobService {
    @Autowired
    QuartzJobMapper quartzJobMapper;

    @Autowired
    private QuartzManage quartzManage;

    /**
     * 创建
     *
     * @param resources /
     * @return /
     */
    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public R create(QuartzJob resources) {
        if (!CronExpression.isValidExpression(resources.getCronExpression())) {
            return R.error("cron表达式格式错误");
        }
        resources.setCreateTime(new Date());
        int num = quartzJobMapper.insert(resources);
        return quartzManage.addJob(resources);
    }

    /**
     * 更改定时任务状态
     *
     * @param quartzJob /
     */
    @Override
    public void updateIsPause(QuartzJob quartzJob) {
        if (quartzJob.getId().equals(1L)) {
            throw new BadRequestException("该任务不可操作");
        }
        if (quartzJob.getIsPause()) {
            quartzManage.resumeJob(quartzJob);
            quartzJob.setIsPause(false);
        } else {
            quartzManage.pauseJob(quartzJob);
            quartzJob.setIsPause(true);
        }
        quartzJobMapper.updateById(quartzJob);
    }

    /**
     * 导出定时任务
     *
     * @param quartzJobs 待导出的数据
     * @param response   /
     * @throws IOException /
     */
    @Override
    public void download(List<QuartzJob> quartzJobs, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (QuartzJob quartzJob : quartzJobs) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("任务名称", quartzJob.getJobName());
            map.put("Bean名称", quartzJob.getBeanName());
            map.put("执行方法", quartzJob.getMethodName());
            map.put("参数", quartzJob.getParams());
            map.put("表达式", quartzJob.getCronExpression());
            map.put("状态", quartzJob.getIsPause() ? "暂停中" : "运行中");
            map.put("描述", quartzJob.getRemark());
            map.put("创建日期", quartzJob.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    /**
     * 立即执行定时任务
     *
     * @param quartzJob /
     */
    @Override
    public R execution(QuartzJob quartzJob) {
        if (quartzJob.getId().equals(1L)) {
            return R.error("该任务不可操作");
//            throw new BadRequestException("该任务不可操作");
        }
        return quartzManage.runJobNow(quartzJob);
    }

    /**
     * 删除任务
     *
     * @param ids
     * @return
     */
    @Override
    public void delete(List<Long> ids) {
        for (Long id : ids) {
            if (id.equals(1L)) {
                throw new BadRequestException("更新访客记录不可删除，你可以在后台代码中取消该限制");
            }
            QuartzJob quartzJob = getById(id);
            quartzManage.deleteJob(quartzJob);
            quartzJobMapper.deleteById(quartzJob);
        }
    }

    /**
     * 编辑
     *
     * @param quartzJob /
     */
    @Override
    public R update(QuartzJob quartzJob) {
        if (quartzJob.getId().equals(1L)) {
            return R.error("该任务不可操作");
        }
        if (!org.quartz.CronExpression.isValidExpression(quartzJob.getCronExpression())) {
            return R.error("cron表达式格式错误");
        }
        int num = quartzJobMapper.updateById(quartzJob);
        quartzManage.updateJobCron(quartzJob);
        return R.ok();
    }
}
