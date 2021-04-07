package com.itts.userservice.mapper.shzd;

import com.itts.userservice.model.shzd.Shzd;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.userservice.vo.ShzdVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fl
 * @since 2021-03-31
 */
public interface ShzdMapper extends BaseMapper<Shzd> {

    Shzd selectByName(String zdmc);
    Shzd selectByCode(String zdbm);

    List<ShzdVO> selectShzd();
}
