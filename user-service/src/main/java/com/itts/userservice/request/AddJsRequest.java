package com.itts.userservice.request;

import com.itts.userservice.model.js.Js;
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
@ApiModel("角色添加对象")
public class AddJsRequest extends Js implements Serializable {

    /**
     * 菜单、操作ID集合
     */
    @ApiModelProperty("菜单、操作ID集合")
    private List<AddJsCdRequest> cdCzIds;

}