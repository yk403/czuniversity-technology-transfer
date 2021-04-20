package com.itts.userservice.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/4/15
 */
@Data
@ApiModel("添加角色菜单关联对象")
public class AddJsCdRequest implements Serializable {

    /**
     * 菜单ID
     */
    @ApiModelProperty(value = "菜单ID")
    private Long cdIds;

    /**
     * 操作ID集合
     */
    @ApiModelProperty(value = "操作ID集合")
    private List<Long> czIds;
}