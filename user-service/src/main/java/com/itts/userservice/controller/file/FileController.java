package com.itts.userservice.controller.file;

import cn.hutool.json.JSONObject;
import com.aliyun.oss.model.PutObjectResult;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.utils.HuaweiyunOss;
import com.itts.common.utils.common.ResponseUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
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
            return ResponseUtil.success();
        } catch (Exception e) {
            log.error("{}文件上传失败！", multipartFile.getOriginalFilename());
            return ResponseUtil.error(ErrorCodeEnum.UPLOAD_FAIL_ERROR);
        }
    }

}
