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
public class JgglDTO implements Serializable {
    private static final long serialVersionUID = -1111753174067243903L;

    /**
     * 机构名称
     */
    @ExcelProperty(value = "机构名称",index = 0)
    private String jgmc;

    /**
     * 机构编码
     */
    @ExcelProperty(value = "机构编码",index = 1)
    private String jgbm;

    /**
     * 机构类别code
     */
    @ExcelProperty(value = "机构类别code",index = 2)
    private String jglbbm;

    /**
     * 机构类别
     */
    @ExcelProperty(value = "机构类别",index = 3)
    private String jglb;

    /**
     * 父机构code
     */
    @ExcelProperty(value = "父机构code",index = 4)
    private String fjbm;

    /**
     * 菜单code层级, 以“-”分隔
     */
    @ExcelProperty(value = "菜单code层级, 以“-”分隔",index = 5)
    private String cj;

}
