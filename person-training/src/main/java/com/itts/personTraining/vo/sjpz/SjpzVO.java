package com.itts.personTraining.vo.sjpz;

import com.itts.personTraining.model.sjpz.Sjpz;
import com.itts.personTraining.model.sjtxpz.Sjtxpz;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SjpzVO extends Sjpz implements Serializable {

    private static final long serialVersionUID = 9150882990213904712L;
    private SjtxpzVO judge;
    private SjtxpzVO single;
    private SjtxpzVO multiple;
}
