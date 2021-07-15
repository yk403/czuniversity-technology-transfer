package com.itts.personTraining.model.xwgl;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author fuli
 * @since 2021-07-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_xwgl")
public class Xwgl implements Serializable {


    private static final long serialVersionUID = 4977245899622447382L;
    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 新闻标题
     */
    private String xwbt;

    /**
     * 新闻内容
     */
    private String xwnr;

    /**
     * 新闻图片名称
     */
    private String xwtpmc;
    /**
     * 新闻图片地址
     */
    private String xwtpdz;
    /**
     * 新闻发布人
     */
    private String xwfbr;

    /**
     * 时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date sj;

    /**
     * 类型
     */
    private String lx;

    /**
     * 机构ID
     */
    private Long jgId;

    /**
     * 状态已发布未发布已停用
     */
    private String zt;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date fbsj;

    /**
     * 推荐
     */
    private String tj;

    /**
     * 备注
     */
    private String bz;

    /**
     * 是否删除
     */
    private Boolean sfsc;

    /**
     * 创建时间
     */
    @TableField(value = "cjsj", fill = FieldFill.INSERT) // 新增执行
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cjsj;

    /**
     * 创建人
     */
    private Long cjr;

    /**
     * 更新时间
     */
    @TableField(value = "gxsj", fill = FieldFill.INSERT) // 新增执行
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gxsj;

    /**
     * 更新人
     */
    private Long gxr;


}
