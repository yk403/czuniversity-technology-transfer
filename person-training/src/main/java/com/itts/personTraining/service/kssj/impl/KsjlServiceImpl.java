package com.itts.personTraining.service.kssj.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.itts.common.bean.LoginUser;
import com.itts.personTraining.enums.KsjlXxbhEnum;
import com.itts.personTraining.enums.TkzyTypeEnum;
import com.itts.personTraining.mapper.kssj.KsjlMapper;
import com.itts.personTraining.mapper.kssj.KsjlxxMapper;
import com.itts.personTraining.mapper.tkzy.TkzyMapper;
import com.itts.personTraining.mapper.tkzy.TmxxMapper;
import com.itts.personTraining.model.kssj.Ksjl;
import com.itts.personTraining.model.kssj.Ksjlxx;
import com.itts.personTraining.model.kssj.Kssj;
import com.itts.personTraining.model.tkzy.Tkzy;
import com.itts.personTraining.model.tkzy.Tmxx;
import com.itts.personTraining.service.kssj.KsjlService;
import com.itts.personTraining.vo.kssj.GetKsjlTmVO;
import com.itts.personTraining.vo.kssj.GetKsjlTmXxVO;
import com.itts.personTraining.vo.kssj.GetKsjlVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 考试记录 服务实现类
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-27
 */
@Service
public class KsjlServiceImpl extends ServiceImpl<KsjlMapper, Ksjl> implements KsjlService {

    @Autowired
    private KsjlMapper ksjlMapper;

    @Autowired
    private TkzyMapper tkzyMapper;

    @Autowired
    private TmxxMapper tmxxMapper;

    @Autowired
    private KsjlxxMapper ksjlxxMapper;

    /**
     * 生成试卷
     */
    @Override
    public GetKsjlVO add(Kssj kssj, LoginUser loginUser) {

        Ksjl ksjl = new Ksjl();
        BeanUtils.copyProperties(kssj, ksjl);

        ksjl.setSjId(kssj.getId());
        ksjl.setXsId(loginUser.getUserId());

        ksjlMapper.insert(ksjl);

        //返回生成的试卷
        GetKsjlVO getKsjlVO = new GetKsjlVO();
        BeanUtils.copyProperties(ksjl, getKsjlVO);

        //获取当前试卷的题目
        List<Tkzy> tms = tkzyMapper.findBySjId(kssj.getId());
        //获取题目ID
        List<Long> tmIds = tms.stream().map(Tkzy::getId).collect(Collectors.toList());

        //查询所有题目选项
        List<Tmxx> allTmxxs = tmxxMapper.selectList(new QueryWrapper<Tmxx>().in("tm_id", tmIds));
        //根据题目ID分组成map
        Map<Long, List<Tmxx>> allTmxxMap = allTmxxs.stream().collect(Collectors.groupingBy(Tmxx::getTmId));

        List<GetKsjlTmVO> ksjlTms = Lists.newArrayList();

        //便利所有题目，生成试卷记录选项
        Iterator<Tkzy> tmIterator = tms.iterator();
        while (tmIterator.hasNext()) {

            Tkzy tm = tmIterator.next();

            GetKsjlTmVO getKsjlTmVO = new GetKsjlTmVO();
            BeanUtils.copyProperties(tm, getKsjlTmVO);

            if (tm != null) {

                //判断题目是否为判断题
                if (Objects.equals(tm.getTmlx(), TkzyTypeEnum.JUDGMENT.getKey())) {

                    setJudgmentOptions(tm, ksjl, getKsjlTmVO);
                } else {

                    setSelectOptions(tm, allTmxxMap, ksjl, getKsjlTmVO);
                }

                tmIterator.remove();
            }

            ksjlTms.add(getKsjlTmVO);
        }


        return getKsjlVO;
    }

    /**
     * 设置判断题选项
     */
    private void setJudgmentOptions(Tkzy tm, Ksjl ksjl, GetKsjlTmVO getKsjlTmVO) {

        List<GetKsjlTmXxVO> ksjlTmXxs = Lists.newArrayList();

        GetKsjlTmXxVO zqVo = new GetKsjlTmXxVO();
        GetKsjlTmXxVO cwVo = new GetKsjlTmXxVO();

        //正确
        Ksjlxx zqxx = new Ksjlxx();
        zqxx.setTmId(tm.getId());
        zqxx.setKsjlId(ksjl.getId());
        zqxx.setXxnr("正确");

        //错误
        Ksjlxx cwxx = new Ksjlxx();
        cwxx.setTmId(tm.getId());
        cwxx.setKsjlId(ksjl.getId());
        cwxx.setXxnr("错误");

        if (tm.getSfzqda()) {
            zqxx.setSfzqda(true);
            cwxx.setSfzqda(false);
        } else {
            zqxx.setSfzqda(false);
            cwxx.setSfzqda(true);
        }

        ksjlxxMapper.insert(zqxx);
        ksjlxxMapper.insert(cwxx);

        BeanUtils.copyProperties(zqxx, zqVo);
        BeanUtils.copyProperties(cwxx, cwVo);
        ksjlTmXxs.add(zqVo);
        ksjlTmXxs.add(cwVo);

        getKsjlTmVO.setKsjlTmXxs(ksjlTmXxs);
    }

    /**
     * 设置选择题选项
     */
    private void setSelectOptions(Tkzy tm, Map<Long, List<Tmxx>> allTmxxMap, Ksjl ksjl, GetKsjlTmVO getKsjlTmVO) {

        List<Tmxx> tmxxs = allTmxxMap.get(tm.getId());
        List<GetKsjlTmXxVO> ksjlTmXxs = Lists.newArrayList();

        for (int i = 0; i < tmxxs.size(); i++) {

            Ksjlxx ksjlxx = new Ksjlxx();
            ksjlxx.setKsjlId(ksjl.getId());
            ksjlxx.setTmId(tm.getId());
            ksjlxx.setXxbh(KsjlXxbhEnum.getByKey(i + 1).getValue());
            ksjlxx.setXxnr(tmxxs.get(i).getXxnr());
            ksjlxx.setSfzqda(tmxxs.get(i).getSfzqda());

            ksjlxxMapper.insert(ksjlxx);

            GetKsjlTmXxVO getKsjlTmXxVO = new GetKsjlTmXxVO();
            BeanUtils.copyProperties(ksjlxx, getKsjlTmXxVO);

            ksjlTmXxs.add(getKsjlTmXxVO);
        }

        getKsjlTmVO.setKsjlTmXxs(ksjlTmXxs);
    }
}
