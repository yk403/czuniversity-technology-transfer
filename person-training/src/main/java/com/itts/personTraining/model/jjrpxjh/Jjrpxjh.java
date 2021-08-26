package com.itts.personTraining.model.jjrpxjh;

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
 * 经纪人培训计划表
 * </p>
 *
 * @author liuyingming
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_jjrpxjh")
public class Jjrpxjh implements Serializable {

    private static final long serialVersionUID = -4273892257845414726L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 培训计划名称
     */
    private String pxjhmc;

    /**
     * 机构ID
     */
    private Long jgId;

    /**
     * 学院名称
     */
    private String xymc;

    /**
     * 批次ID
     */
    private Long pcId;

    /**
     * 批次名称
     */
    private String pcMc;

    /**
     * 教育类型
     */
    private String jylx;

    /**
     * 学员类型
     */
    private String xylx;

    /**
     * 上课开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date skkssj;

    /**
     * 上课结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date skjssj;

    /**
     * 报名开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date bmkssj;

    /**
     * 报名结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date bmjssj;

    /**
     * 上课地点
     */
    private String dd;

    /**
     * 报名人数
     */
    private Integer bmrs;

    /**
     * 是否上架
     */
    private Boolean sfsj;

    /**
     * 上架时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sjsj;

    /**
     * 是否删除
     */
    private Boolean sfsc;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cjsj;

    /**
     * 创建人
     */
    private Long cjr;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gxsj;

    /**
     * 更新人
     */
    private Long gxr;
}
