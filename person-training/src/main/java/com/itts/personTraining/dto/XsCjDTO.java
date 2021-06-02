package com.itts.personTraining.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Austin
 * @Data: 2021/6/1
 * @Version: 1.0.0
 * @Description: 学生成绩对象
 */
@Data
@ApiModel("学生成绩对象")
public class XsCjDTO {

    /**
     * 学生成绩主键
     */
    private Long id;

    /**
     * 学生ID
     */
    private Long xsId;

    /**
     * 学号
     */
    private String xh;

    /**
     * 姓名
     */
    private String xm;

    /**
     * 学院id
     */
    private Long xyId;

    /**
     * 学院名称
     */
    private String xymc;

    /**
     * 原专业
     */
    private String yzy;

    /**
     * 综合成绩
     */
    private String zhcj;

    /**
     * 总学分
     */
    private String zxf;

    /**
     * 论文成绩
     */
    private String lwcj;

    /**
     * 是否删除（0：否；1：是）
     */
    private Boolean sfsc;

    /**
     * 创建时间
     */
    @TableField(value = "cjsj", fill = FieldFill.INSERT) // 新增执行
    private Date cjsj;

    /**
     * 创建人
     */
    private Long cjr;

    /**
     * 更新时间
     */
    @TableField(value = "gxsj", fill = FieldFill.INSERT_UPDATE) // 新增和更新执行
    private Date gxsj;

    /**
     * 更新人
     */
    private Long gxr;
}
