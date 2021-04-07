package com.itts.userservice.service.Excel;

import com.itts.common.utils.common.ResponseUtil;
import org.springframework.web.multipart.MultipartFile;

public interface ExcelService {

    ResponseUtil importJggl(MultipartFile file, Integer headRowNumber);
    ResponseUtil importShzd(MultipartFile file, Integer headRowNumber);
}
