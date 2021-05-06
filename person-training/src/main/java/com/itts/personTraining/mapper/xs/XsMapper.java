package com.itts.personTraining.mapper.xs;

import com.itts.personTraining.dto.JwglDTO;
import com.itts.personTraining.model.xs.Xs;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 学生表 Mapper 接口
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
public interface XsMapper extends BaseMapper<Xs> {

    List<JwglDTO> findJwglList(String string,String yx,Long pcId);
}
