package com.itts.userservice.request.yh;

import com.itts.userservice.model.yh.Yh;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AddYhRequest extends Yh implements Serializable {

    private static final long serialVersionUID = -1499514464900906989L;

    private Long pcId;
    @ApiModelProperty(value = "角色ID集合",required = true)
    private List<Long> jsidlist;
}
