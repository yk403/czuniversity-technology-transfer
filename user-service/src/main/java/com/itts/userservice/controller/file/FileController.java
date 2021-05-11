package com.itts.userservice.controller.file;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.model.PutObjectResult;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.utils.HuaweiyunOss;
import com.itts.common.utils.common.ResponseUtil;
import com.obs.services.exception.ObsException;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
@Api(tags = "文件")
@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/v1/file")
public class FileController {
    @Resource
    private HuaweiyunOss huaweiyunOss;

    /**
     * 视频上传
     * @param multipartFile
     * @return
     */
    @PostMapping(value = "/upload/")
    public ResponseUtil fileUpload(@RequestParam(value = "file", required = false) MultipartFile multipartFile) {
        try {
            String objectKey = multipartFile.getOriginalFilename();
            InputStream inputStream = multipartFile.getInputStream();
            LocalDate localDate = LocalDate.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
            String concat = HuaweiyunOss.FileType.SPZB.getType().concat("/").concat(dtf.format(localDate));
            boolean b = huaweiyunOss.putFilebyInstreamAndMeta(objectKey, concat, inputStream);
            inputStream.close();
            return ResponseUtil.success(0, "success","https://"+concat.concat("/").concat(objectKey));
        } catch (Exception e) {
            log.error("{}文件上传失败！", multipartFile.getOriginalFilename());
            return ResponseUtil.error(ErrorCodeEnum.UPLOAD_FAIL_ERROR);
        }
    }

    /**
     * 视频下载
     * @param request
     * @param response
     * @param jsonString
     * @return
     */
    @PostMapping(value = "/download/")
    public ResponseUtil fileDownload(HttpServletRequest request, HttpServletResponse response, @RequestBody String jsonString) {
        JSONObject jsonObject = JSON.parseObject(jsonString);
        String fileName = JSON.parseObject((String) jsonObject.get("fileName"), String.class);
        try {
            String downloadUrl = huaweiyunOss.getDownloadUrl(fileName, HuaweiyunOss.FileType.SPZB);


            // 为防止 文件名出现乱码
            final String userAgent = request.getHeader("USER-AGENT");
            // IE浏览器
            if (StringUtils.contains(userAgent, "MSIE")) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                // google,火狐浏览器
                if (StringUtils.contains(userAgent, "Mozilla")) {
                    fileName = new String(fileName.getBytes(), "ISO8859-1");
                } else {
                    // 其他浏览器
                    fileName = URLEncoder.encode(fileName, "UTF-8");
                }
            }
            response.setContentType("application/x-download");
            // 设置让浏览器弹出下载提示框，而不是直接在浏览器中打开
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);

            return ResponseUtil.success(response);
        } catch (IOException | ObsException e) {
            log.error("文件下载失败：{}", e.getMessage());
            return ResponseUtil.error(000,"文件下载失败！");
        }
    }


}
