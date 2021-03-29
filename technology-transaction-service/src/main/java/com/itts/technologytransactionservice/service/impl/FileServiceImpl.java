package com.itts.technologytransactionservice.service.impl;

import com.itts.common.exception.ServiceException;
import com.itts.common.utils.FastDFSClient;
import com.itts.common.utils.FastDFSFile;
import com.itts.technologytransactionservice.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

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
            path = saveFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
    /**
     * 上传文件方法
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public String saveFile(MultipartFile multipartFile) throws IOException {
        String[] fileAbsolutPath = {};
        String fileName = multipartFile.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".")+1);
        byte[] file_buff = null;
        InputStream inputStream = multipartFile.getInputStream();
        if (inputStream!=null) {
            int len1 = inputStream.available();
            file_buff = new byte[len1];
            inputStream.read(file_buff);
        }
        inputStream.close();
        FastDFSFile file = new FastDFSFile(fileName,file_buff,ext);
        fileAbsolutPath = FastDFSClient.upload(file);
        if(fileAbsolutPath==null){
            log.error("【技术交易 - 文件上传失败】");
            throw new ServiceException(UPLOAD_FAIL_ERROR);
        }
        String path = FastDFSClient.getTrackerUrl()+"/"+fileAbsolutPath[0]+"/"+fileAbsolutPath[1];
        log.info("【技术交易 - 文件上传成功】,path: {}",path);
        return path;
    }
}
