package com.itts.userservice.request;

import com.itts.userservice.model.yh.Yh;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AddYhRequest extends Yh implements Serializable {
    private static final long serialVersionUID = -1499514464900906989L;

    private List<Long> jsidlist;
}
