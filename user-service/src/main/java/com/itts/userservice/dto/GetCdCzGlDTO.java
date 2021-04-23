package com.itts.userservice.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/4/13
 */
@Data
public class GetCdCzGlDTO implements Serializable {

    private static final long serialVersionUID = -3058683755541115022L;

    /**
     * 操作ID
     */
    private Long id;

    /**
     * 操作名称
     */
    private String czmc;

    /**
     * 操作编码
     */
    private String czbm;
}