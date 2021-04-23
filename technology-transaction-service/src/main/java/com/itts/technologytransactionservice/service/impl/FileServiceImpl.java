package com.itts.technologytransactionservice.service.impl;


import com.itts.technologytransactionservice.service.FileService;
import com.itts.technologytransactionservice.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author: Austin
 * @Data: 2021/3/29
 * @Version: 1.0.0
 * @Description: 文件服务业务逻辑
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Override
    public String fileUpload(MultipartFile file) {

        String path = null;
        try {
            path = FileUtil.uploadFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

}
