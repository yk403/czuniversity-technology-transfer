package com.itts.userservice.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ContentRowHeight(12)
@HeadRowHeight(12)
@ColumnWidth(20)
public class ShzdDTO implements Serializable {
    private static final long serialVersionUID = -2727743518515921646L;
    /**
     * 字典名称
     */
    @ExcelProperty(value = "字典名称",index = 0)
    private String zdmc;

    /**
     * 字典编码
     */
    @ExcelProperty(value = "字典编码",index = 1)
    private String zdbm;

    /**
     * 父级字段code, 如果是顶级则为000
     */
    @ExcelProperty(value = "父级字段code",index = 2)
    private String fjzdCode;

    /**
     * 字典层级, 以“-”分隔
     */
    @ExcelProperty(value = "字典层级",index = 3)
    private String zdcj;
}
