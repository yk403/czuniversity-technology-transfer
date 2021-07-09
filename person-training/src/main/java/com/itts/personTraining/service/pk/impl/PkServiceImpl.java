package com.itts.personTraining.service.pk.impl;

import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.DateUtils;
import com.itts.personTraining.dto.PkDTO;
import com.itts.personTraining.mapper.sz.SzMapper;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.model.pk.Pk;
import com.itts.personTraining.mapper.pk.PkMapper;
import com.itts.personTraining.model.sz.Sz;
import com.itts.personTraining.service.pk.PkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itts.personTraining.service.pkKc.PkKcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;

/**
 * <p>
 * 排课表 服务实现类
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class PkServiceImpl extends ServiceImpl<PkMapper, Pk> implements PkService {

    @Resource
    private PkMapper pkMapper;
    @Autowired
    private PkService pkService;
    @Autowired
    private PkKcService pkKcService;
    @Resource
    private SzMapper szMapper;

    /**
     * 查询排课信息
     *
     * @param pcId
     * @return
     */
    @Override
    public List<PkDTO> findPkInfo(Long pcId) {
        log.info("【人才培养 - 查询排课信息,上课开始时间:{},批次id:{}】", pcId);
        List<PkDTO> pkDTOs = pkMapper.findPkInfo(pcId);
        /*Map<String, List<PkDTO>> groupByXqs = pkDTOs.stream().collect(Collectors.groupingBy(PkDTO::getXqs));
        //遍历分组
        List<String> xqsList = new ArrayList<>();
        for (Map.Entry<String, List<PkDTO>> entryPkDTO : groupByXqs.entrySet()) {
            xqsList.add(entryPkDTO.getKey());
        }
        for (int i = 1; i < 8; i++) {
            if (xqsList.contains(String.valueOf(i))) {
                continue;
            } else {
                groupByXqs.put(String.valueOf(i), Collections.EMPTY_LIST);
            }
        }*/
        return pkDTOs;
    }

    /**
     * 根据id查询排课详情
     *
     * @param id
     * @return
     */
    @Override
    public PkDTO get(Long id) {
        log.info("【人才培养 - 根据id:{}查询排课信息】", id);
        return pkMapper.getPkById(id);
    }

    /**
     * 新增排课
     *
     * @param pkDTO
     * @return
     */
    @Override
    public boolean add(PkDTO pkDTO) {
        log.info("【人才培养 - 新增排课:{}】", pkDTO);
        Long userId = getUserId();
        pkDTO.setCjr(userId);
        pkDTO.setGxr(userId);
        Pk pk = new Pk();
        BeanUtils.copyProperties(pkDTO, pk);
        Pk pk1 = changeSknyr(pkDTO, pk);
        return pkService.save(pk1);
    }

    /**
     * 更新排课
     *
     * @param pkDTO
     * @return
     */
    @Override
    public boolean update(PkDTO pkDTO) {
        log.info("【人才培养 - 更新排课:{}】", pkDTO);
        Long userId = getUserId();
        pkDTO.setGxr(userId);
        Pk pk = new Pk();
        BeanUtils.copyProperties(pkDTO, pk);
        Pk pk1 = changeSknyr(pkDTO, pk);
        return pkService.updateById(pk1);
    }

    /**
     * 删除排课
     *
     * @param pkDTO
     * @return
     */
    @Override
    public boolean delete(PkDTO pkDTO) {
        log.info("【人才培养 - 删除排课:{}】", pkDTO);
        //设置删除状态
        pkDTO.setSfsc(true);
        pkDTO.setGxr(getUserId());
        Pk pk = new Pk();
        BeanUtils.copyProperties(pkDTO, pk);
        return pkService.updateById(pk);
    }

    /**
     * 批量新增排课
     *
     * @param pkDTOs
     * @return
     */
    @Override
    public boolean addList(List<PkDTO> pkDTOs) {
        log.info("【人才培养 - 批量新增排课:{}】", pkDTOs);
        Long userId = getUserId();
        List<Pk> pks = new ArrayList<>();
        for (PkDTO pkDTO : pkDTOs) {
            Pk pk = new Pk();
            pkDTO.setCjr(userId);
            pkDTO.setGxr(userId);
            BeanUtils.copyProperties(pkDTO, pk);
            Pk pk1 = changeSknyr(pkDTO, pk);
            pks.add(pk1);
        }
        return pkService.saveBatch(pks);
    }

    /**
     * 根据开学日期查询所有排课信息
     * @param kxrq
     * @return
     */
    @Override
    public List<PkDTO> findPkByKxrq(Date kxrq) {
        log.info("【人才培养 - 根据开学日期:{}查询所有排课信息】", kxrq);
        List<PkDTO> pkDTOs = pkMapper.findPkByCondition(kxrq);
        return pkDTOs;
    }

    /**
     * 根据用户id查询批次ids(前)
     * @return
     */
    @Override
    public List<Pc> getPcsByYhId() {
        Long userId = getUserId();
        log.info("【人才培养 - 根据用户id:{}查询批次ids(前)】", userId);
        String userCategory = getUserCategory();
        List<Pc> pcs = null;
        switch (userCategory) {
            case "teacher":
                Sz sz = szMapper.getSzByYhId(userId);
                if (sz != null) {
                    pcs = pkMapper.findPcsBySzId(sz.getId());
                }
                break;
            default:
                break;
        }
        return pcs;
    }

    /**
     * 获取当前用户id
     *
     * @return
     */
    public Long getUserId() {
        LoginUser loginUser = threadLocal.get();
        Long userId;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        } else {
            throw new ServiceException(GET_THREADLOCAL_ERROR);
        }
        return userId;
    }

    /**
     * 获取当前用户id所属类别
     * @return
     */
    private String getUserCategory() {
        LoginUser loginUser = threadLocal.get();
        String userCategory;
        if (loginUser != null) {
            userCategory = loginUser.getUserCategory();
        } else {
            throw new ServiceException(GET_THREADLOCAL_ERROR);
        }
        return userCategory;
    }

    /**
     * 将起始周结束周转换成上课起始年月日和上课结束年月日
     * @param pkDTO
     * @param pk
     * @return
     */
    private Pk changeSknyr(PkDTO pkDTO, Pk pk) {
        Integer qsz = pkDTO.getQsz();
        if (qsz != null) {
            Date skqsnyr = null;
            try {
                skqsnyr = DateUtils.getBeforeOrAfterDate(pkDTO.getKxrq(), (qsz-1)*7);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            pk.setSkqsnyr(skqsnyr);
        }
        Integer jsz = pkDTO.getJsz();
        if (jsz != null) {
            Date skjsnyr = null;
            try {
                skjsnyr = DateUtils.getBeforeOrAfterDate(pk.getSkqsnyr(), jsz*7);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            pk.setSkjsnyr(skjsnyr);
        }
        return pk;
    }

    /**
     * 新增排课课程关系
     * @param pkDTO
     * @param pk
     * @return
     */
    /*private boolean savePkKc(PkDTO pkDTO, Pk pk) {
        List<KcDTO> kcDTOs = pkDTO.getKcDTOs();
        List<PkKc> pkKcList = new ArrayList<>();
        for (KcDTO kcDTO : kcDTOs) {
            PkKc pkKc = new PkKc();
            pkKc.setKcId(kcDTO.getId());
            pkKc.setPkId(pk.getId());
            pkKcList.add(pkKc);
        }
        return pkKcService.saveBatch(pkKcList);
    }*/

    /**
     * 删除排课课程关系
     * @param pkDTO
     * @return
     */
    /*private boolean removePkKc(PkDTO pkDTO) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pk_id", pkDTO.getId());
        return pkKcService.removeByMap(map);
    }*/

}
