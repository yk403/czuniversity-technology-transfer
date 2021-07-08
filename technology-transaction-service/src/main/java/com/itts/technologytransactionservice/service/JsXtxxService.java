package com.itts.technologytransactionservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.technologytransactionservice.model.JsXtxx;

import java.util.Map;

/*
 *@Auther: yukai
 *@Date: 2021/07/06/9:46
 *@Desription:
 */
public interface JsXtxxService extends IService<JsXtxx> {
    PageInfo findJsXtxx(Map<String, Object> params);
    /**
    * @Description: 发送人员id,接收人员id，模块类型(0:技术交易,1:双创路演)，所属模块(信息采集，报名，招标发布，挂牌发布等)，载体类型(如技术成果，技术需求，技术展品)，操作内容（如发布成功，发布失败，审核成功等）
    * @Param: [sendId, receiveId, mklx, ssmk, ztlx]
    * @return: void
    * @Author: yukai
    * @Date: 2021/7/6
    */
    void addXtxx(Long sendId,Long receiveId,Integer mklx,String ssmk,String ztlx,String cznr,String describe);
    Long getUserId();
}
