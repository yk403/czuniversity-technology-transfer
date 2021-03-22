package com.itts.technologytransactionservice.service;

import com.itts.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

public interface ExcelService {
    R importXq(MultipartFile file, Integer headRowNumber, Integer importType);
    R importCg(MultipartFile file, Integer headRowNumber, Integer importType);
}
