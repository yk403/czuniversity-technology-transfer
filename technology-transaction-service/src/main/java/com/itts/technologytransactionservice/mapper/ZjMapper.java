package com.itts.technologytransactionservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itts.technologytransactionservice.model.TZj;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-20 17:05:41
 */
@Repository
public interface ZjMapper extends BaseMapper<TZj> {

    List<TZj> list(IPage<TZj> page, @Param("map") Map map);

}
