package com.itts.userservice.service.yh;

import com.itts.userservice.model.yh.YhJsGl;

import java.util.List;

/**
 * <p>
 * 用户角色关联表 服务类
 * </p>
 *
 * @author fl
 * @since 2021-03-24
 */
public interface YhJsGlService {

    List<Long> fingByYhid(Long id);


}
