package com.itts.personTraining.service.xxzy.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.personTraining.mapper.xxzy.XxzyscMapper;
import com.itts.personTraining.model.xxzy.Xxzy;
import com.itts.personTraining.model.xxzy.Xxzysc;
import com.itts.personTraining.service.xxzy.XxzyscService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.itts.common.constant.SystemConstant.threadLocal;

/**
 * <p>
 * 学习资源收藏 服务实现类
 * </p>
 *
 * @author liuyingming
 * @since 2021-06-09
 */
@Service
public class XxzyscServiceImpl extends ServiceImpl<XxzyscMapper, Xxzysc> implements XxzyscService {

    @Autowired
    private XxzyscMapper xxzyscMapper;

    /**
     * 获取学习资源收藏列表 - 分页
     */
    @Override
    public PageInfo<Xxzy> findScByPage(int pageNum, int pageSize, Long userId, String firstCategory,
                                       String secondCategory, String category, String direction, Long id) {

        PageHelper.startPage(pageNum, pageSize);
        Long fjjgId = getFjjgId();
        List<Xxzy> xxzys = xxzyscMapper.findScByPage(userId, firstCategory, secondCategory, category, direction, id,fjjgId);

        PageInfo pageInfo = new PageInfo(xxzys);

        return pageInfo;
    }

    /**
     * 新增收藏
     */
    @Override
    public Xxzysc add(Xxzy xxzy, Long userId) {

        Xxzysc xxzysc = new Xxzysc();

        xxzysc.setCjsj(new Date());
        xxzysc.setYhId(userId);
        xxzysc.setXxzyId(xxzy.getId());

        xxzyscMapper.insert(xxzysc);

        return xxzysc;
    }

    /**
     * 取消收藏
     */
    @Override
    public void delete(Long id) {

        xxzyscMapper.deleteById(id);
    }
    private Long getFjjgId(){
        LoginUser loginUser = threadLocal.get();
        Long fjjgId = null;
        if (loginUser != null) {
            fjjgId = loginUser.getFjjgId();
        }
        return fjjgId;
    }
}
