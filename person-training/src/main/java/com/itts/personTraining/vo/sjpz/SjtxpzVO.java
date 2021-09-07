package com.itts.personTraining.vo.sjpz;

import com.itts.personTraining.model.sjtxndpz.Sjtxndpz;
import com.itts.personTraining.model.sjtxpz.Sjtxpz;
import lombok.Data;

import java.io.Serializable;

@Data
public class SjtxpzVO extends Sjtxpz implements Serializable {
    private static final long serialVersionUID = 153223344063284065L;
    private Sjtxndpz easy;
    private Sjtxndpz commonly;
    private Sjtxndpz difficulty;
}
