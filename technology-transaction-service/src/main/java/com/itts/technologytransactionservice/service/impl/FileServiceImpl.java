package com.itts.technologytransactionservice.service.impl;

import com.itts.common.exception.ServiceException;
import com.itts.common.utils.FastDFSClient;
import com.itts.common.utils.FastDFSFile;
import com.itts.common.utils.FileUtil;
import com.itts.technologytransactionservice.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static com.itts.common.constant.UrlConstant.PROXY_PASS;
import static com.itts.common.constant.UrlConstant.SERVER_NAME;
import static com.itts.common.enums.ErrorCodeEnum.UPLOAD_FAIL_ERROR;

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
