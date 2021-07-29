package com.itts.personTraining.model.sjzd;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 数据字典管理
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sjzd")
@ApiModel(value = "数据字典")
public class Sjzd implements Serializable {


    private static final long serialVersionUID = 7563349664009671507L;
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 字典名称
     */
    @ApiModelProperty(value = "字典名称", required = true)
    private String zdmc;

    /**
     * 字典编码
     */
    @ApiModelProperty(value = "字典编码", required = true)
    private String zdbm;

    /**
     * 系统类型：technology_transaction - 技术交易；talent_training - 人才培养；user - 用户管理
     */
    @ApiModelProperty(value = "系统类型：technology_transaction - 技术交易；talent_training - 人才培养；user - 用户管理")
    private String xtlb;

    /**
     * 模块类型:front - 门户;admin - 后台管理
     */
    @ApiModelProperty(value = "模块类型:front - 门户;admin - 后台管理")
    private String mklx;

    /**
     * 所属模块名称
     */
    @ApiModelProperty(value = "所属模块名称", required = true)
    private String ssmkmc;

    /**
     * 所属模块编码
     */
    @ApiModelProperty(value = "所属模块编码", required = true)
    private String ssmk;

    /**
     * 父级ID
     */
    @ApiModelProperty(value = "父级ID")
    private Long fjId;

    /**
     * 父级编码
     */
    @ApiModelProperty(value = "父级编码")
    private String fjBm;


    /**
     * 父级字典名称
     */
    @ApiModelProperty(value = "父级字典名称")
    private String fjmc;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer px;

    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除")
    private Boolean sfsc;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cjsj;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Long cjr;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gxsj;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private Long gxr;
}
