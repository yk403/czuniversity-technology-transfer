package com.itts.personTraining.vo.kssj;

import com.itts.personTraining.model.kssj.Kssj;
import lombok.Data;

import java.io.Serializable;
@Data
public class KssjVO extends Kssj implements Serializable {
    private static final long serialVersionUID = 8812033891287520085L;

    private String sjpzmc;
    private Long jylxId;
}
