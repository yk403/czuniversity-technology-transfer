package com.itts.userservice.dto;

import com.itts.userservice.model.cd.Cd;
import lombok.Data;

import java.util.List;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/4/13
 */
@Data
public class GetCdAndCzDTO extends Cd {

    /**
    *操作集合
    */
    private List<GetCdCzGlDTO> czs;
}