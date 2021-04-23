package com.itts.technologytransactionservice.service;

import com.itts.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

public interface ExcelService {
    R importXq(MultipartFile file, Integer headRowNumber);

    R importCg(MultipartFile file, Integer headRowNumber);
}
