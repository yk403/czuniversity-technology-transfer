package com.itts.userservice.request.sjzd;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/5/11
 */
@Data
@ApiModel(value = "更新数据字典")
public class UpdateSjzdRequest implements Serializable {

    private static final long serialVersionUID = 503179339756918959L;

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
     * 父级编码
     */
    @ApiModelProperty(value = "父级编码")
    private String fjBm;

    /**
     * 数据字典子项
     */
    @ApiModelProperty(value = "数据字典子项", required = true)
    private List<UpdateSjzdItemRequest> sjzdItems;
}