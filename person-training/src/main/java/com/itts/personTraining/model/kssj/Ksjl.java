package com.itts.personTraining.model.kssj;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 考试记录
 * </p>
 *
 * @author liuyingming
 * @since 2021-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_ksjl")
public class Ksjl implements Serializable {

    private static final long serialVersionUID = 3228492672125835499L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 试卷ID
     */
    private Long sjId;

    /**
     * 学生ID
     */
    private Long xsId;

    /**
     * 学生编号
     */
    private String xsbm;

    /**
     * 学生姓名
     */
    private String xsmc;

    /**
     * 试卷名称
     */
    private String sjmc;

    /**
     * 试卷总分
     */
    private Integer sjzf;

    /**
     * 最终成绩
     */
    private Integer zzcj;

    /**
     * 题目总数
     */
    private Integer tmzs;

    /**
     * 考试类型：single_subject - 单科；comprehensive - 综合
     */
    private String sjlx;

    /**
     * 开始答题时间
     */
    private Date ksdtsj;

    /**
     * 结束答题时间
     */
    private Date jsdtsj;

    /**
     * 判断总分
     */
    private Integer pdzf;

    /**
     * 判断题最终得分
     */
    private Integer pddf;

    /**
     * 单选题总分
     */
    private Integer danxzf;

    /**
     * 单选最终得分
     */
    private Integer danxdf;

    /**
     * 多选总分
     */
    private Integer duoxzf;

    /**
     * 多选最终得分
     */
    private Integer duoxdf;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cjsj;
}
