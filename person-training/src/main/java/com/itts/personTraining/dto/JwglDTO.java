package com.itts.personTraining.dto;

import com.itts.personTraining.model.kc.Kc;
import com.itts.personTraining.model.xs.Xs;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class JwglDTO extends Xs implements Serializable {

    private static final long serialVersionUID = -6829038212602051826L;

    private String pcmc;
    private List<Kc> kcList;
}
