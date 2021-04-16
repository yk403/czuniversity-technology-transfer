package com.itts.userservice.model.sjb;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/4/16
 */
@Data
@ApiModel("表结构对象")
public class Bjg implements Serializable {

    private static final long serialVersionUID = 4358261521333357650L;

    @ApiModelProperty("表字段信息")
    private List<Bzd> bzds;
}