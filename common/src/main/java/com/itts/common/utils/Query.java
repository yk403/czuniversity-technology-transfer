package com.itts.common.utils;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 */
@Data
public class Query extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    //
    private int offset = 0;
    // 每页条数
    private int pageSize = 10;

    private int pageNum = 1;

    public Query(Map<String, Object> params) {
        this.putAll(params);
        if (params.get("offset") != null) {
            this.offset = Integer.parseInt(params.get("offset").toString());
        }
        if (params.get("pageSize") != null) {
            this.pageSize = Integer.parseInt(params.get("pageSize").toString());
        }
        if (params.get("pageNum") != null) {
            this.pageNum = Integer.parseInt(params.get("pageNum").toString());
        } else {
            this.put("pageNum", offset / pageSize + 1);
        }
        this.put("offset", offset);
        this.put("pageSize", pageSize);
    }

}
