package com.itts.userservice.request.sjzd;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/5/11
 */
@Data
public class AddSjzdItemRequest implements Serializable {

    private static final long serialVersionUID = 2261274044457307454L;

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
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer px;
}