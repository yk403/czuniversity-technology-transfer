package com.itts.userservice.dto;

import com.itts.userservice.model.yh.Yh;
import lombok.Data;

import java.util.List;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/4/9
 */
@Data
public class YhDTO extends Yh {

    private List<String> yhjsmc;
}