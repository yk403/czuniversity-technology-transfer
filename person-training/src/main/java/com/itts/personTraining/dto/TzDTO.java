package com.itts.personTraining.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Austin
 * @Data: 2021/6/23
 * @Version: 1.0.0
 * @Description: 通知DTO
 */
@Data
@ApiModel("通知对象")
public class TzDTO {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 考试试卷id
     */
    private Long kssjId;

    /**
     * 通知名称
     */
    private String tzmc;

    /**
     * 通知类型
     */
    private String tzlx;

    /**
     * 内容
     */
    private String nr;

    /**
     * 是否读取
     */
    private boolean sfdq;

    /**
     * 处理状态
     */
    private boolean clzt;

    /**
     * 考试时长
     */
    private String kssc;

    /**
     * 考试开始年月日
     */
    private Date ksksnyr;

    /**
     * 考试结束年月日
     */
    private Date ksjsnyr;

    /**
     * 是否删除
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
