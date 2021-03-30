package com.itts.technologytransactionservice.controller;


import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import static com.itts.common.constant.SystemConstant.BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.UPLOAD_FAIL_ISEMPTY_ERROR;

/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术成果管理
 */
@RestController
@RequestMapping(BASE_URL+"/v1/File")
public class FileController {
    @Autowired
    private FileService fileService;
    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("upload")
    public ResponseUtil fileUpload(@RequestParam MultipartFile file) {
        if(file.isEmpty()){
            throw new WebException(UPLOAD_FAIL_ISEMPTY_ERROR);
        }
        return ResponseUtil.success("上传成功",fileService.fileUpload(file));
    }
}
