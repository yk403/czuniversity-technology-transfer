package com.itts.personTraining.service.excel;

import com.itts.common.utils.common.ResponseUtil;
import org.springframework.web.multipart.MultipartFile;

public interface ExcelService {
    ResponseUtil importXs(MultipartFile file, Integer headRowNumber);

    ResponseUtil importSz(MultipartFile file, Integer headRowNumber);
}
