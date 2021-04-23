package com.itts.userservice.vo;

import com.itts.userservice.model.jggl.Jggl;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 机构管理树
 */
@Data
public class JgglVO extends Jggl implements Serializable {

    private static final long serialVersionUID = 2696809217049123961L;

    /**
     * 下属机构
     */
    private List<JgglVO> jgglVOList;
}
