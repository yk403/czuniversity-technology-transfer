package com.itts.userservice.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 操作
 * @author FULI
 */
@Data
public class CzDTO implements Serializable {
    private static final long serialVersionUID = -5042991752179284089L;

    //id
    private Long id;

    //名称
    private String name;

    //编码
    private String code;
}
