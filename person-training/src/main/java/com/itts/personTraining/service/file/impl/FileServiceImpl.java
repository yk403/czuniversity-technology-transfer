package com.itts.personTraining.service.file.impl;

import com.itts.common.utils.FileUtil;
import com.itts.personTraining.service.file.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author: Austin
 * @Data: 2021/4/22
 * @Version: 1.0.0
 * @Description: 文件业务逻辑
 */
@Service
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
