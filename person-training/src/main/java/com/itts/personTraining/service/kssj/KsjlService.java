package com.itts.personTraining.service.kssj;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itts.common.bean.LoginUser;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.kssj.Ksjl;
import com.itts.personTraining.model.kssj.Kssj;
import com.itts.personTraining.request.kssj.CommitKsjlRequest;
import com.itts.personTraining.vo.kssj.GetKsjlVO;

/**
 * <p>
 * 考试记录 服务类
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-27
 */
public interface KsjlService extends IService<Ksjl> {

    /**
     * 获取详情
     */
    GetKsjlVO get(Long id);

    /**
     * 生成试卷
     */
    GetKsjlVO add(Kssj kssj, LoginUser loginUser);

    /**
     * 提交试卷
     */
    ResponseUtil commit(CommitKsjlRequest commitKsjlRequest, LoginUser loginUser);
}
