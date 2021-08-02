package com.itts.personTraining.vo.stgl;

import com.itts.personTraining.model.stgl.Stgl;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/8/2
 */
@Data
public class StVO extends Stgl implements Serializable {

  private static final long serialVersionUID = -8418726200743846728L;

  private String componentName;
}