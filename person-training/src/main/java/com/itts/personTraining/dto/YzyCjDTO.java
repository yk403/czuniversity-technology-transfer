package com.itts.personTraining.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: fuli
 * @Data: 2021/6/4
 * @Version: 1.0.0
 * @Description: 原专业成绩导入实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ContentRowHeight(12)
@HeadRowHeight(12)
@ColumnWidth(20)
public class YzyCjDTO implements Serializable {

    private static final long serialVersionUID = -6567576480726951805L;

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
     * 课程
     */
    @ExcelProperty(value = "课程名称", index = 3)
    private String kcmc;

    /**
     * 是否必修(0:否;1:是)
     */
    @ExcelProperty(value = "是否必修", index = 4)
    private Boolean sfbx;

    /**
     * 学位课(0:否;1:是)
     */
    @ExcelProperty(value = "学位课", index = 5)
    private Boolean xwk;

    /**
     * 原专业学分
     */
    @ExcelProperty(value = "原专业学分", index = 6)
    private Integer yzyxf;


    /**
     * 当前学分
     */
    @ExcelProperty(value = "当前学分", index = 7)
    private Integer dqxf;

    /**
     * 成绩
     */
    @ExcelProperty(value = "成绩", index = 8)
    private String cj;


    @ExcelProperty(value = "选修学期", index = 9)
    private String xxxq;
    /**
     * 成绩属性:(正常,旷考,免考,作弊 重修,缓考,补考, 取消资格)
     */
    @ExcelProperty(value = "成绩属性", index = 10)
    private String cjsx;

    /**
     * 补考成绩
     */
    @ExcelProperty(value = "补考成绩", index = 11)
    private String bkcj;
}
