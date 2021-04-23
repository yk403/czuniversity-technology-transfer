package com.itts.userservice.mapper.sjzd;

import com.itts.userservice.model.sjzd.Sjzd;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.userservice.vo.SjzdVO;
import org.apache.ibatis.annotations.Param;

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
    Sjzd selectByCode(String zdbm);
    List<Sjzd> selectByNameOrCode(@Param("string") String string,@Param("ssmk") String ssmk);

    List<SjzdVO> selectSjzd();
}
