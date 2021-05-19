package com.itts.userservice.service.yh;

import com.github.pagehelper.PageInfo;
import com.itts.userservice.dto.YhDTO;
import com.itts.userservice.model.jggl.Jggl;
import com.itts.userservice.model.yh.Yh;
import com.itts.userservice.vo.yh.GetYhVO;
import com.itts.userservice.vo.yh.YhListVO;
import com.itts.userservice.vo.yh.YhVO;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author fl
 * @since 2021-03-18
 */
public interface YhService {

    /**
     * 查询列表
     */
    PageInfo<YhListVO> findByPage(Integer pageNum, Integer pageSize, String type, Jggl group, String condition);

    /**
     * 查询列表
     */
    PageInfo<YhDTO> selectByString(Integer pageNum, Integer pageSize, String type, String string);

    /**
     * 获取详情
     */
    Yh get(Long id);

    /**
     * 通过编号获取用户信息
     */
    GetYhVO getByCode(String code);

    /**
     * 通过手机号获取用户信息
     */
    GetYhVO getByphone(String phone);

    /**
     * 通过用户名获取用户信息
     */
    Yh getByUserName(String userName);

    /**
     * 新增
     */
    Boolean addYhAndJsmc(Yh Yh, Long jsid);

    /**
     * 更新
     */
    Yh update(Yh Yh);

    /**
     * 级联更新
     */
    Yh updateByYhAndJsmc(Yh Yh, Long jsid);

    /**
     * 查询角色菜单目录
     *
     * @param
     * @return
     */
    YhVO findMenusByUserID(Long userId, String systemType);
}
