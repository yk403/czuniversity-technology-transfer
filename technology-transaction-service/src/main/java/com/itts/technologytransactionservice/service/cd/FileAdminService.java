package com.itts.technologytransactionservice.service.cd;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: Austin
 * @Data: 2021/4/2
 * @Description: 文件服务接口
 */
public interface FileAdminService {

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    String fileUpload(MultipartFile file);
}
