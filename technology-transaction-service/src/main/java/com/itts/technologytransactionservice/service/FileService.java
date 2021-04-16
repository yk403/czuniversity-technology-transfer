package com.itts.technologytransactionservice.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 文件服务接口
 */
public interface FileService {

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    String fileUpload(MultipartFile file);
}
