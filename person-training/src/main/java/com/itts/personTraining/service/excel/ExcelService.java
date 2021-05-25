package com.itts.personTraining.service.excel;

import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.pc.Pc;
import org.springframework.web.multipart.MultipartFile;

public interface ExcelService {

    ResponseUtil importXs(MultipartFile file, Integer headRowNumber, Pc pc, String token);

    ResponseUtil importSz(MultipartFile file, Integer headRowNumber, String token);
}
