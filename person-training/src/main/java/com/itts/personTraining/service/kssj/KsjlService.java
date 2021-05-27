package com.itts.personTraining.service.kssj;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itts.common.bean.LoginUser;
import com.itts.personTraining.model.kssj.Ksjl;
import com.itts.personTraining.model.kssj.Kssj;

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
     * 生成试卷
     */
    Ksjl add(Kssj kssj, LoginUser loginUser);

}
