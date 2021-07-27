package com.itts.userservice.vo.yh;

import lombok.Data;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/7/27
 */

@Data
public class GetSystemsVO {

    /**
     * 系统名称
     */
    private String mc;

    /**
     * 系统编码
     */
    private String bm;
}