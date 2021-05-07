package com.itts.personTraining.request;

import com.itts.personTraining.model.kc.Kc;
import com.itts.personTraining.model.xs.Xs;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class AddJwglRequset  implements Serializable {

    private static final long serialVersionUID = -3590330602135828190L;
    private Long id;
    private List<Long> kcIdList;
}
