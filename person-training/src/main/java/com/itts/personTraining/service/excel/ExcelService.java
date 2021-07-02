package com.itts.personTraining.service.excel;

import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.pc.Pc;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public interface ExcelService {

    ResponseUtil importXs(MultipartFile file, Integer headRowNumber, Long jgId, Long pcId, String jylx, String pch, Date rxrq, String token);

    ResponseUtil importSz(MultipartFile file, Integer headRowNumber, Long jgId, String token);

    ResponseUtil importXlXwCj(MultipartFile file, Integer headRowNumber, Long pcId, String jylx, String token);

    ResponseUtil importJxjyCj(MultipartFile file, Integer headRowNumber, Long pcId, String jylx, String token);

    ResponseUtil importZj(MultipartFile file, Integer headRowNumber, String token);

    ResponseUtil importSj(MultipartFile file, Integer headRowNumber, String token);
}
