package com.itts.technologytransactionservice.model;

import lombok.Data;

import java.time.LocalDateTime;

/*
 *@Auther: yukai
 *@Date: 2021/05/31/11:18
 *@Desription:
 */
@Data
public class LyLtDto {
    /*
    聊天内容
     */
    private String content;
    /*
    聊天发送时间
     */
    private LocalDateTime sendTime;
    /**
     * 用户登录账号
     */
    private String userName;
    /**
     * 用户真实姓名
     */
    private String realName;
}
