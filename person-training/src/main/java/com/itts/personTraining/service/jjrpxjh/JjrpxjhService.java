package com.itts.personTraining.service.jjrpxjh;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.personTraining.dto.JjrbmInfo;
import com.itts.personTraining.dto.JjrpxjhDTO;
import com.itts.personTraining.dto.StuDTO;
import com.itts.personTraining.model.jjrpxjh.Jjrpxjh;
import com.itts.personTraining.request.jjrpxjh.AddJjrpxjhRequest;
import com.itts.personTraining.request.jjrpxjh.UpdateJjrpxjhRequest;
import com.itts.personTraining.vo.jjrpxjh.GetJjrpxjhVO;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 经纪人培训计划表 服务类
 * </p>
 *
 * @author liuyingming
 * @since 2021-06-01
 */
public interface JjrpxjhService extends IService<Jjrpxjh> {

    /**
     * 获取详情
     */
    GetJjrpxjhVO get(Jjrpxjh old);

    /**
     * 新增
     */
    Jjrpxjh add(AddJjrpxjhRequest addJjrpxjhRequest);

    /**
     * 更新
     */
    Jjrpxjh update(Jjrpxjh old, UpdateJjrpxjhRequest updateJjrpxjhRequest);

    /**
     * 更新状态
     */
    Jjrpxjh updateStatus(Jjrpxjh old, Boolean sfsj);

    /**
     * 删除
     */
    void delete(Jjrpxjh old);

    /**
     *获取经纪人培训计划
     * @return
     */
    JjrpxjhDTO getJjrpxjh();

    /**
     * 培训报名
     * @param jjrbmInfo
     * @return
     */
    boolean saveJjrInfo(JjrbmInfo jjrbmInfo);
}
