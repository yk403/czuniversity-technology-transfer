package com.itts.userservice.request.sjzd;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/5/11
 */
@Data
@ApiModel(value = "更新数据字典子项")
public class UpdateSjzdItemRequest {

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
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
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer px;
}