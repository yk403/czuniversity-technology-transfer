package com.itts.userservice.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 机构管理树
 */
@Data
public class JgglVO implements Serializable {
    private static final long serialVersionUID = 2696809217049123961L;
    private String jgmc;
    private String jgbm;
    private String fatherCode;
    private String cj;
    private List<JgglVO> jgglVOList;
}
