package com.itts.userservice.request.sjzd;

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
public class AddSjzdRequest implements Serializable {

    private static final long serialVersionUID = 5115408970251359824L;

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
    private Long fjBm;

    /**
     * 数据字典子项
     */
    @ApiModelProperty(value = "数据字典子项", required = true)
    private List<AddSjzdItemRequest> sjzdItems;
}