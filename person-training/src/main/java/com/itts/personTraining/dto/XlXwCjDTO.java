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
 * @Description: 学历学位成绩导入实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ContentRowHeight(12)
@HeadRowHeight(12)
@ColumnWidth(20)
public class XlXwCjDTO implements Serializable {

    private static final long serialVersionUID = -1305880881628798707L;



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
     * 课程代码
     */
    @ExcelProperty(value = "课程代码", index = 2)
    private String kcdm;


    /**
     * 是否必修(0:否;1:是)
     */
    @ExcelProperty(value = "是否必修", index = 3)
    private Boolean sfbx;




    /**
     * 当前学分
     */
    @ExcelProperty(value = "当前学分", index = 4)
    private Integer dqxf;

    /**
     * 成绩
     */
    @ExcelProperty(value = "成绩", index = 5)
    private String cj;



    /**
     * 成绩属性:(正常,旷考,免考,作弊 重修,缓考,补考, 取消资格)
     */
    @ExcelProperty(value = "成绩属性", index = 6)
    private String cjsx;

    /**
     * 补考成绩
     */
    @ExcelProperty(value = "补考成绩", index = 7)
    private String bkcj;
}
