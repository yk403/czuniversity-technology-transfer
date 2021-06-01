package com.itts.personTraining.model.xsCj;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 学生成绩表
 * </p>
 *
 * @author Austin
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_xs_cj")
public class XsCj implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 学生ID
     */
    private Long xsId;

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
    private Date cjsj;

    /**
     * 创建人
     */
    private Long cjr;

    /**
     * 更新时间
     */
    private Date gxsj;

    /**
     * 更新人
     */
    private Long gxr;


}
