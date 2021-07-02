package com.itts.personTraining.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("课程表")
public class KcbDTO {
    /**
     * 主键
     */
    private Long id;


    /**
     * 课程代码
     */
    private String kcdm;

    /**
     * 课程名称
     */
    private String kcmc;

    /**
     * 类型(1:学历学位排课;2:继续教育排课)
     */
    private Integer type;

    /**
     * 上课地点
     */
    private String skdd;


    /**
     * 开学日期
     */
    private Date kxrq;

    /**
     * 上课起始年月日
     */
    private Date skqsnyr;

    /**
     * 上课结束年月日
     */
    private Date skjsnyr;

    /**
     * 起始周
     */
    private Integer qsz;

    /**
     * 结束周
     */
    private Integer jsz;

    /**
     * 星期数
     */
    private String xqs;

    /**
     * 教学楼名称
     */
    private String jxlmc;

    /**
     * 教室编号
     */
    private String jsbh;

    /**
     * 授课老师姓名
     */
    private String dsxm;

    /**
     * 授课老师编号
     */
    private String dsbh;
    /**
     * 开始时间
     */
    private String kssj;

    /**
     * 结束时间
     */
    private String jssj;
}
