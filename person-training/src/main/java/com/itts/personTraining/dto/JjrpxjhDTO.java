package com.itts.personTraining.dto;

import com.itts.personTraining.model.jjrpxjh.Jjrpxjh;
import com.itts.personTraining.model.sz.Sz;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Austin
 * @Data: 2021/7/13
 * @Version: 1.0.0
 * @Description: 经纪人培养计划报名对象
 */
@Data
public class JjrpxjhDTO extends Jjrpxjh implements Serializable {

    private static final long serialVersionUID = -5371888292499289349L;

    private List<KcXsXfDTO> kcDTOList;

    private List<Sz> szList;

}
