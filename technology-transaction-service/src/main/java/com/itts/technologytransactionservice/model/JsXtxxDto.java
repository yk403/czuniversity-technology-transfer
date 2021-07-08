package com.itts.technologytransactionservice.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/*
 *@Auther: yukai
 *@Date: 2021/07/06/8:35
 *@Desription:
 */
@Data
public class JsXtxxDto extends JsXtxx implements Serializable {
    /**
    * @Description: 操作员用户名
    * @Param: 
    * @return: 
    * @Author: yukai
    * @Date: 2021/7/8
    */
    private String yhm;
    /**
     * @Description: 当前用户名
     * @Param:
     * @return:
     * @Author: yukai
     * @Date: 2021/7/8
     */
    private String dqyh;
}
