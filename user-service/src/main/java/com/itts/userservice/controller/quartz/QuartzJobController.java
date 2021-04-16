package com.itts.userservice.controller.quartz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itts.common.utils.R;
import com.itts.userservice.common.quartz.exception.BadRequestException;
import com.itts.userservice.model.quartz.QuartzJob;
import com.itts.userservice.model.quartz.QuartzLog;
import com.itts.userservice.service.quartz.IQuartzJobService;
import com.itts.userservice.service.quartz.IQuartzLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zs
 * @since 2020-03-09
 */
@Slf4j
@RestController
@Api("前端控制器")
@RequestMapping("/quartzJob")
public class QuartzJobController {
    @Autowired
    IQuartzJobService quartzJobService;
    @Autowired
    IQuartzLogService quartzLogService;

    private static final String ENTITY_NAME = "quartzJob";

    /**
     * 查询定时任务
     *
     * @param pageSize
     * @param pageNum
     * @param quartzJob
     * @return
     */
    @ApiOperation(value = "查询定时任务", notes = "查询定时任务")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
//    @GetMapping("/list")
    public Object getJobs(Long pageSize, Long pageNum, QuartzJob quartzJob, @RequestParam(value = "createTimeList", required = false) List<Date> createTimeList) {
        if (pageNum == null) {
            pageNum = new Long(0);
        }
        if (pageSize == null) {
            pageSize = new Long(10);
        }
        IPage<QuartzJob> quartzJobIPage = new Page<>(pageNum, pageSize);
        QueryWrapper<QuartzJob> quartzJobQueryWrapper = new QueryWrapper<>();
        quartzJobQueryWrapper.like("job_name", quartzJob.getJobName() == null ? "" : quartzJob.getJobName());
        if (createTimeList != null && createTimeList.size() >= 2) {
            quartzJobQueryWrapper.between("create_time", createTimeList.get(0), createTimeList.get(1));
        }
        return quartzJobService.page(quartzJobIPage, quartzJobQueryWrapper);
    }


    @GetMapping("/test")
    public Object test(@RequestParam(value = "createTimeList", required = false) List<String> createTimeList) {
        return 0;
    }

    /**
     * 导出任务数据
     *
     * @param response
     * @param quartzJob
     * @throws IOException
     */
    @ApiOperation(value = "导出任务数据", notes = "导出任务数据")
    @RequestMapping(value = "/download", method = RequestMethod.GET)
//    @GetMapping("/download")
    public void download(HttpServletResponse response, QuartzJob quartzJob) throws IOException {
        QueryWrapper<QuartzJob> quartzJobQueryWrapper = new QueryWrapper<>(quartzJob);
        quartzJobService.download(quartzJobService.list(quartzJobQueryWrapper), response);
    }


    /**
     * 导出日志数据
     *
     * @param response
     * @param quartzLog
     * @throws IOException
     */
    @ApiOperation(value = "导出日志数据", notes = "导出日志数据")
    @RequestMapping(value = "/downloadLog", method = RequestMethod.GET)
//    @GetMapping("/downloadLog")
    public void downloadLog(HttpServletResponse response, QuartzLog quartzLog) throws IOException {
        QueryWrapper<QuartzLog> quartzJobQueryWrapper = new QueryWrapper<>(quartzLog);
        quartzLogService.download(quartzLogService.list(quartzJobQueryWrapper), response);
    }

    /**
     * 查询任务执行日志
     *
     * @param quartzLog
     * @return
     */
    @ApiOperation(value = "查询任务执行日志", notes = "查询任务执行日志")
    @RequestMapping(value = "/logs", method = RequestMethod.GET)
//    @GetMapping("/logs")
    public Object getJobLogs(QuartzLog quartzLog, Long pageNum, Long pageSize, @RequestParam(value = "createTimeList", required = false) List<Date> createTimeList) {
        if (pageNum == null) {
            pageNum = new Long(0);
        }
        if (pageSize == null) {
            pageSize = new Long(10);
        }
        IPage<QuartzLog> quartzJobIPage = new Page<>(pageNum, pageSize);
        QueryWrapper<QuartzLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("job_name", quartzLog.getJobName() == null ? "" : quartzLog.getJobName());
        if (createTimeList != null && createTimeList.size() >= 2) {
            queryWrapper.between("create_time", createTimeList.get(0), createTimeList.get(1));
        }
        return R.okData(quartzLogService.page(quartzJobIPage, queryWrapper));
    }

    /**
     * 新增定时任务
     *
     * @param quartzJob
     * @return
     */
    @ApiOperation(value = "新增定时任务", notes = "新增定时任务")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    @PostMapping("/add")
    public R create(@Validated @RequestBody QuartzJob quartzJob) {
        if (quartzJob.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        return quartzJobService.create(quartzJob);
    }


    @ApiOperation(value = "修改", notes = "修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
//    @PostMapping("/update")
    public R update(@RequestBody QuartzJob quartzJob) {
        return quartzJobService.update(quartzJob);
    }


    /**
     * 更改定时任务状态
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "更改定时任务状态", notes = "更改定时任务状态")
    @RequestMapping(value = "/updateIsPause", method = RequestMethod.GET)
//    @GetMapping("/updateIsPause")
    public R updateIsPause(Long id) {
        quartzJobService.updateIsPause(quartzJobService.getById(id));
        return R.ok();
    }

    /**
     * 立即执行定时任务
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "立即执行定时任务", notes = "立即执行定时任务")
    @RequestMapping(value = "/execution", method = RequestMethod.GET)
//    @GetMapping("/execution")
    public R execution(Long id) {
        return quartzJobService.execution(quartzJobService.getById(id));
    }

    /**
     * 删除定时任务
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除定时任务", notes = "删除定时任务")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
//    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        quartzJobService.delete(Arrays.asList(ids));
        return R.ok();
    }
}