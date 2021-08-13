package com.itts.personTraining.vo.rmdt;

import com.itts.personTraining.model.rmdt.Rmdt;
import lombok.Data;

import java.io.Serializable;
@Data
public class RmVO extends Rmdt implements Serializable {

    private static final long serialVersionUID = -1375424500406874356L;

    private String componentName;
}
