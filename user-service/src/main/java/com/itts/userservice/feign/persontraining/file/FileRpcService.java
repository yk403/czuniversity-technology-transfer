package com.itts.userservice.feign.persontraining.file;

import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.UPLOAD_FAIL_ISEMPTY_ERROR;

/**
 * fuli
 */
@FeignClient(value = "person-training-service")
public interface FileRpcService {
    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping(ADMIN_BASE_URL + "/v1/File/upload")
    ResponseUtil fileUpload(@RequestParam MultipartFile file);
}
