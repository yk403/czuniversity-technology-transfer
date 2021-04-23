package com.itts.personTraining.service.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    /**
     * 文件上传
     * @param file
     * @return
     */
    String fileUpload(MultipartFile file);
}
