package com.itts.userservice.mapper.sjzd;

import com.itts.userservice.model.sjzd.Sjzd;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.userservice.vo.SjzdVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fl
 * @since 2021-03-31
 */
public interface SjzdMapper extends BaseMapper<Sjzd> {

    Sjzd selectByName(String zdmc);
    Sjzd selectByCode(String zdbm);

    List<SjzdVO> selectSjzd();
}
