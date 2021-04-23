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
public class YhExcelDTO implements Serializable {

    private static final long serialVersionUID = -419447521905233476L;
    /**
     * 用户编号
     */
    @ExcelProperty(value = "用户编号",index = 0)
    private String yhbh;
    /**
     * 真实姓名
     */
    @ExcelProperty(value = "真实姓名",index = 1)
    private String zsxm;
    /**
     * 联系电话
     */
    @ExcelProperty(value = "联系电话",index = 2)
    private String lxdh;
    /**
     * 用户类型
     */
    @ExcelProperty(value = "用户类型",index = 3)
    private String yhlx;

    /**
     * 机构ID
     */
    @ExcelProperty(value = "机构ID",index = 4)
    private Long jgid;
    /**
     * 用户邮箱
     */
    @ExcelProperty(value = "用户邮箱",index = 5)
    private String yhyx;
}
