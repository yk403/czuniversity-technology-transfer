package com.itts.personTraining.vo.jggl;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/7/27
 */
@Data
public class JgglVO implements Serializable {

    private static final long serialVersionUID = 7724417164069632149L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 机构名称
     */
    @ApiModelProperty(value = "父级字段code", required = true)
    private String jgmc;

    /**
     * 机构编码
     */
    @ApiModelProperty(value = "父级字段code", required = true)
    private String jgbm;

    /**
     * 机构类别code
     */
    @ApiModelProperty(value = "父级字段code", required = true)
    private String jglbbm;

    /**
     * 机构类别
     */
    @ApiModelProperty(value = "父级字段code", required = true)
    private String jglb;

    /**
     * 父机构code
     */
    private String fjbm;

    /**
     * 父机构名称
     */
    private String fjmc;
    /**
     * 机构地址
     */
    private String jgdz;

    /**
     * 负责人
     */
    private String fzr;
    /**
     * 联系电话
     */
    private String lxdh;

    /**
     * 菜单层级, 以“-”分隔
     */
    private String cj;

    /**
     * 机构类型：headquarters - 总部；branch - 分基地；other - 其他
     */
    private String lx;

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