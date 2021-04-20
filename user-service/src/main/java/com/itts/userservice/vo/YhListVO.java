package com.itts.userservice.vo;

import com.itts.userservice.model.yh.Yh;
import lombok.Data;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/4/9
 */

@Data
public class YhListVO extends Yh {

    /**
    *用户角色名称
    */
    private String yhjsmc;
}