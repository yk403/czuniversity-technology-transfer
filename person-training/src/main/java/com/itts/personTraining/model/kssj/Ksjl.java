package com.itts.personTraining.model.kssj;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2021-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_ksjl")
public class Ksjl implements Serializable {

    private static final long serialVersionUID = 1L;

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


}
