package com.itts.technologytransactionservice.service.cd;

import com.itts.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

public interface ExcelAdminService {
    R importXq(MultipartFile file, Integer headRowNumber);
    R importCg(MultipartFile file, Integer headRowNumber);
}
