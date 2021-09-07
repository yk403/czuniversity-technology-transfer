package com.itts.personTraining.service.tkzy.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.personTraining.mapper.tkzy.TkzyMapper;
import com.itts.personTraining.mapper.tkzy.TmxxMapper;
import com.itts.personTraining.model.tkzy.Tkzy;
import com.itts.personTraining.model.tkzy.Tmxx;
import com.itts.personTraining.request.tkzy.AddTkzyRequest;
import com.itts.personTraining.request.tkzy.AddTmxxRequest;
import com.itts.personTraining.request.tkzy.UpdateTkzyRequest;
import com.itts.personTraining.request.tkzy.UpdateTmxxRequest;
import com.itts.personTraining.service.tkzy.TkzyService;
import com.itts.personTraining.vo.tkzy.GetTkzyVO;
import com.itts.personTraining.vo.tkzy.GetTmxxVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.itts.common.constant.SystemConstant.threadLocal;

/**
 * <p>
 * 题库资源 服务实现类
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-13
 */
@Service
public class TkzyServiceImpl extends ServiceImpl<TkzyMapper, Tkzy> implements TkzyService {

    @Autowired
    private TkzyMapper tkzyMapper;

    @Autowired
    private TmxxMapper tmxxMapper;

    /**
     * 列表 - 有题目选项
     */
    @Override
    public PageInfo listByDetail(Integer pageNum, Integer pageSize, String firstCategory, String secondCategory, Long courseId, Integer score, String type, Boolean putOnShelf, Long fjjgId) {

        PageHelper.startPage(pageNum, pageSize);

        List<Tkzy> tkzys = tkzyMapper.selectList(new QueryWrapper<Tkzy>()
                .eq(StringUtils.isNotBlank(firstCategory), "tmyjfl", firstCategory)
                .eq(StringUtils.isNotBlank(secondCategory), "tmejfl", secondCategory)
                .eq(courseId != null, "kc_id", courseId)
                .eq(score != null, "fz", score)
                .eq(putOnShelf != null, "sfsj", putOnShelf)
                .eq(StringUtils.isNotBlank(type), "tmlx", type)
                .eq(fjjgId != null, "fjjg_id", fjjgId));

        if (CollectionUtils.isEmpty(tkzys)) {
            return null;
        }

        PageInfo page = new PageInfo(tkzys);

        List<Long> tkzyIds = tkzys.stream().map(Tkzy::getId).collect(Collectors.toList());

        //获取题库列表重的所有选项
        List<Tmxx> tmxxs = tmxxMapper.selectList(new QueryWrapper<Tmxx>().in("tm_id", tkzyIds));

        Map<Long, List<Tmxx>> tmxxMap = tmxxs.stream().collect(Collectors.groupingBy(Tmxx::getTmId));

        List<GetTkzyVO> tkzyVOs = tkzys.stream().map(obj -> {

            GetTkzyVO vo = new GetTkzyVO();
            BeanUtils.copyProperties(obj, vo);

            if (MapUtil.isNotEmpty(tmxxMap)) {

                List<Tmxx> thisTmxxs = tmxxMap.get(obj.getId());

                if (!CollectionUtils.isEmpty(thisTmxxs)) {

                    List<GetTmxxVO> thisTMxxVOs = thisTmxxs.stream().map(tmxxObj -> {

                        GetTmxxVO tmxxVo = new GetTmxxVO();
                        BeanUtils.copyProperties(tmxxObj, tmxxVo);
                        return tmxxVo;

                    }).collect(Collectors.toList());

                    vo.setTmxxs(thisTMxxVOs);
                }
            }

            return vo;
        }).collect(Collectors.toList());

        PageInfo pageInfo = new PageInfo();
        BeanUtils.copyProperties(page, pageInfo);
        pageInfo.setList(tkzyVOs);

        return pageInfo;
    }

    /**
     * 详情
     */
    @Override
    public GetTkzyVO get(Long id) {

        Tkzy tkzy = tkzyMapper.selectById(id);

        if (tkzy == null || tkzy.getSfsc()) {
            return null;
        }

        GetTkzyVO vo = new GetTkzyVO();
        BeanUtils.copyProperties(tkzy, vo);

        List<GetTmxxVO> tmxxs = tmxxMapper.findByTmId(id).stream().map(obj -> {

            GetTmxxVO tmxxVO = new GetTmxxVO();
            BeanUtils.copyProperties(obj, tmxxVO);
            return tmxxVO;
        }).collect(Collectors.toList());

        vo.setTmxxs(tmxxs);
        return vo;
    }

    /**
     * 新增
     */
    @Override
    public Tkzy add(AddTkzyRequest addTkzyRequest) {

        LoginUser loginUser = SystemConstant.threadLocal.get();
        Long userId = null;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        }

        Tkzy tkzy = new Tkzy();
        BeanUtils.copyProperties(addTkzyRequest, tkzy);

        Date now = new Date();

        tkzy.setCtr(loginUser.getRealName());
        tkzy.setCjr(userId);
        tkzy.setGxr(userId);
        tkzy.setCjsj(now);
        tkzy.setGxsj(now);
        tkzy.setFjjgId(getFjjgId());
        tkzyMapper.insert(tkzy);

        if (CollectionUtils.isEmpty(addTkzyRequest.getTmxxs())) {
            return tkzy;
        }

        for (AddTmxxRequest addTmxx : addTkzyRequest.getTmxxs()) {

            Tmxx tmxx = new Tmxx();
            BeanUtils.copyProperties(addTmxx, tmxx);

            tmxx.setCjr(userId);
            tmxx.setGxr(userId);
            tmxx.setCjsj(now);
            tmxx.setGxsj(now);
            tmxx.setTmId(tkzy.getId());

            tmxxMapper.insert(tmxx);
        }

        return tkzy;
    }

    /**
     * 更新
     */
    @Override
    public Tkzy update(UpdateTkzyRequest updateTkzyRequest, Tkzy tkzy, Long userId) {

        Date now = new Date();

        BeanUtils.copyProperties(updateTkzyRequest, tkzy);

        tkzy.setGxr(userId);
        tkzy.setGxsj(now);

        tkzyMapper.updateById(tkzy);

        if (CollectionUtils.isEmpty(updateTkzyRequest.getTmxxs())) {
            return tkzy;
        }

        //更新题目选项
        List<UpdateTmxxRequest> updateTmxxList = updateTkzyRequest.getTmxxs();

        //获取新增选项
        List<UpdateTmxxRequest> addList = updateTmxxList.stream().filter(obj -> obj.getId() == null).collect(Collectors.toList());

        //移除需要添加的选项
        updateTmxxList.removeAll(addList);

        //获取当前题目之前的所有选项
        List<Tmxx> oldTmxxs = tmxxMapper.findByTmId(updateTkzyRequest.getId());

        //之前所有选项ID
        List<Long> oldTmxxIds = oldTmxxs.stream().map(Tmxx::getId).collect(Collectors.toList());
        Map<Long, Tmxx> oldTmxxMap = oldTmxxs.stream().collect(Collectors.toMap(Tmxx::getId, obj -> obj));

        //需要更新的所有选项ID
        List<Long> updateTmxxIds = updateTmxxList.stream().map(UpdateTmxxRequest::getId).collect(Collectors.toList());
        Map<Long, UpdateTmxxRequest> updateTmxxMap = updateTmxxList.stream().collect(Collectors.toMap(UpdateTmxxRequest::getId, obj -> obj));

        for (Long updateTmxxId : updateTmxxIds) {

            if (oldTmxxIds.contains(updateTmxxId)) {

                //更新选项
                Tmxx tmxx = oldTmxxMap.get(updateTmxxId);
                UpdateTmxxRequest updateTmxx = updateTmxxMap.get(updateTmxxId);
                BeanUtils.copyProperties(updateTmxx, tmxx);

                tmxx.setGxr(userId);
                tmxx.setGxsj(now);

                tmxxMapper.updateById(tmxx);

                oldTmxxIds.remove(updateTmxxId);
            }
        }

        //添加新的选项
        if (!CollectionUtils.isEmpty(addList)) {

            for (UpdateTmxxRequest updateTmxxRequest : addList) {

                Tmxx tmxx = new Tmxx();
                BeanUtils.copyProperties(updateTmxxRequest, tmxx);

                tmxx.setTmId(updateTkzyRequest.getId());
                tmxx.setCjr(userId);
                tmxx.setGxr(userId);
                tmxx.setCjsj(now);
                tmxx.setGxsj(now);

                tmxxMapper.insert(tmxx);
            }
        }

        //删除选项
        if (CollectionUtils.isEmpty(oldTmxxIds)) {

            for (Long oldTmxxId : oldTmxxIds) {
                tmxxMapper.deleteById(oldTmxxId);
            }
        }

        return tkzy;
    }

    /**
     * 获取父级机构ID
     * @return
     */
    private Long getFjjgId(){
        LoginUser loginUser = threadLocal.get();
        Long fjjgId = null;
        if (loginUser != null) {
            fjjgId = loginUser.getFjjgId();
        }
        return fjjgId;
    }
}
