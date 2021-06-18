package com.itts.personTraining.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Austin
 * @Data: 2021/6/18
 * @Version: 1.0.0
 * @Description: 学分DTO
 */
@Data
@ApiModel("学分对象")
public class XfDTO {

    @ApiModelProperty(value = "总学分")
    private Integer zxf;

    @ApiModelProperty(value = "主修专业学分(原专业)")
    private Integer zxzyxf;

    @ApiModelProperty(value = "互认学分")
    private Integer hrxf;

    @ApiModelProperty(value = "实训学分")
    private Integer sxxf;

    @ApiModelProperty(value = "实践学分")
    private Integer sjxf;

    @ApiModelProperty(value = "技术转移领域学分")
    private Integer jszylyxf;

}
