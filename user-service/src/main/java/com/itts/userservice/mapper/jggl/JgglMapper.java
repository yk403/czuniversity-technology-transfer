package com.itts.userservice.mapper.jggl;

import com.itts.userservice.dto.JgglDTO;
import com.itts.userservice.model.jggl.Jggl;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itts.userservice.vo.JgglVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fl
 * @since 2021-03-30
 */
public interface JgglMapper extends BaseMapper<Jggl> {


    Jggl selectByName(String jgmc);


    Jggl selectByCode(String jgbm);

    /**
     * 查询机构
     */
    List<JgglVO> selectJggl();

    /**
     * 查询机构
     */
    List<JgglVO> selectStringJggl(@Param("name") String string);

}
