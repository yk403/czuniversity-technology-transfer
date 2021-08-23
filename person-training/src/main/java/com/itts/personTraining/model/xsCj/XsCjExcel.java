package com.itts.personTraining.model.xsCj;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class XsCjExcel implements Serializable {
    private static final long serialVersionUID = -3894552257798489435L;

    /**
     * 批次号
     */
    private String pch;

    /**
     * 批次名称
     */
    private String pcmc;
    /**
     * 学号
     */
    private String xh;
    /**
     * 姓名
     */
    private String xm;
    /**
     * 综合成绩
     */
    private String zhcj;

}
