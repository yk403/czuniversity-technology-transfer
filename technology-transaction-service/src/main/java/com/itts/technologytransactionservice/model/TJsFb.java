package com.itts.technologytransactionservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 需求成果合并表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TJsFb implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 发布类型
     */
    private String releaseType;
    /**
     * 行业领域
     */
    private String lyName;
    /**
     * 预算
     */
    private String hzjg;
    /**
     * 地址
     */
    private String dz;
    /**
     * 提交时间
     */
    private String applyTime;

}
