package com.itts.userservice.request;

import com.itts.userservice.model.cd.Cd;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description：新增菜单请求参数
 * @Author：lym
 * @Date: 2021/4/13
 */
@Data
@ApiModel("新增菜单请求参数")
public class AddCdRequest extends Cd implements Serializable {

    private static final long serialVersionUID = 568750986172789204L;

    /**
     * 菜单ID集合
     */
    @ApiModelProperty("操作ID集合")
    private List<Long> czIds;
}