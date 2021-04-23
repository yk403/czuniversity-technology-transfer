package com.itts.technologytransactionservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itts.common.exception.WebException;
import com.itts.common.utils.OssUtil;
import com.itts.common.utils.Result;
import com.itts.technologytransactionservice.model.BackOss;
import com.itts.technologytransactionservice.service.BackOssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.itts.common.constant.SystemConstant.BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.UPLOAD_FAIL_ERROR;

/**
 * <p>
 * 文件上传 前端控制器
 * </p>
 *
 * @author lyh
 * @since 2020-06-19
 */
@RestController
@RequestMapping(BASE_URL + "/v1/oss/back-oss")
@Api(tags = "文件上传")
public class BackOssController {

    @Autowired
    private OssUtil ossUtil;
    @Autowired
    private BackOssService backOssService;

    @ApiOperation(value = "删除文件")
    @DeleteMapping("/delete/object")
    public Result deleteObject(@RequestParam String key) {
        ossUtil.deleteObject(key);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("oss_name", key);
        backOssService.remove(queryWrapper);
        return Result.ok();
    }

    @ApiOperation(value = "获取签名数据")
    @GetMapping("/policy")
    public Result policy() {
        return Result.ok().data("policyData", ossUtil.getPolicy());
    }

    @ApiOperation(value = "保存并获取文件 url")
    @PostMapping("/saveUrl")
    public Result saveUrl(@RequestParam String key, @RequestParam String fileName) {
        BackOss backOss = new BackOss();
        backOss.setOssName(key);
        backOss.setFileName(fileName);
        backOss.setFileUrl(ossUtil.getUrl(key));
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("oss_name", key);
        // 数据不存在则添加，否则根据 oss_name 更新数据
        backOssService.saveOrUpdate(backOss, queryWrapper);
        return Result.ok().data("file", backOssService.getOne(queryWrapper));
    }

    @ApiOperation(value = "上传文件")
    @PostMapping("/upload")
    public Result upload(@ApiParam MultipartFile file) {
        // 用于保存文件 url
        String url = null;
        // 用于保存文件信息
        BackOss backOss = new BackOss();
        try {
            // 获取文件上传路径
            url = ossUtil.uploadSuffix(file.getInputStream(), "aliyun", file.getOriginalFilename());

            // 保存文件路径到数据库中
            backOss.setFileName(file.getOriginalFilename());
            backOss.setOssName(url);
            backOss.setFileUrl(ossUtil.getUrl(url));
            backOssService.save(backOss);
        } catch (IOException e) {
            throw new WebException(UPLOAD_FAIL_ERROR);
        }
        return Result.ok().message("文件上传成功").data("file", backOss);
    }

    @ApiOperation(value = "获取所有文件信息")
    @GetMapping("/getAll")
    public Result getAll() {
        return Result.ok().data("file", backOssService.list());
    }
}

