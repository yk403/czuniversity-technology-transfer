package com.itts.personTraining.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: Austin
 * @Data: 2021/6/3
 * @Version: 1.0.0
 * @Description: 继续教育成绩导入实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ContentRowHeight(12)
@HeadRowHeight(12)
@ColumnWidth(20)
public class JxjyCjDTO implements Serializable {

    private static final long serialVersionUID = 6452217622674529714L;

    /**
     * 学号
     */
    @ExcelProperty(value = "学号", index = 0)
    private String xh;

    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名", index = 1)
    private String xm;

    /**
     * 综合成绩
     */
    @ExcelProperty(value = "综合成绩", index = 2)
    private String zhcj;

}
