package com.itts.personTraining.model.kssj;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 考试记录选项
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_ksjlxx")
public class Ksjlxx implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 考试记录ID
     */
    private Long ksjlId;

    /**
     * 题目ID
     */
    private Long tmId;

    /**
     * 选项编号
     */
    private String xxbh;

    /**
     * 选项内容
     */
    private String xxnr;

    /**
     * 是否选中
     */
    private Boolean sfxz;

    /**
     * 是否正确答案
     */
    private Boolean sfzqda;


}
