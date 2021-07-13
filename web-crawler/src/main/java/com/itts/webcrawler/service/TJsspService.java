package com.itts.webcrawler.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.webcrawler.model.TJssp;

import java.util.Map;

/*
 *@Auther: yukai
 *@Date: 2021/07/12/16:53
 *@Desription:
 */
public interface TJsspService extends IService<TJssp> {
    PageInfo findTJssp(Map<String, Object> params);
}
