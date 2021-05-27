package com.itts.personTraining.service.pk.impl;


import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.DateUtils;
import com.itts.personTraining.dto.KcDTO;
import com.itts.personTraining.dto.PkDTO;
import com.itts.personTraining.model.pk.Pk;
import com.itts.personTraining.mapper.pk.PkMapper;
import com.itts.personTraining.model.pkKc.PkKc;
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
import static com.itts.common.utils.DateUtils.getDateAfterNDays;

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
@Transactional
public class PkServiceImpl extends ServiceImpl<PkMapper, Pk> implements PkService {

    @Resource
    private PkMapper pkMapper;
    @Autowired
    private PkService pkService;
    @Autowired
    private PkKcService pkKcService;

    /**
     * 查询排课信息
     *
     * @param skqsnyr
     * @param pcId
     * @return
     */
    @Override
    public Map<String, List<PkDTO>> findPkInfo(String skqsnyr, Long pcId) {
        log.info("【人才培养 - 查询排课信息,上课开始时间:{},批次id:{}】", skqsnyr, pcId);
        List<PkDTO> pkDTOs = pkMapper.findPkInfo(skqsnyr, getDateAfterNDays(skqsnyr, 7), pcId);
        Map<String, List<PkDTO>> groupByXqs = pkDTOs.stream().collect(Collectors.groupingBy(PkDTO::getXqs));
        //遍历分组
        List<String> xqsList = new ArrayList<>();
        for (Map.Entry<String, List<PkDTO>> entryPkDTO : groupByXqs.entrySet()) {
            xqsList.add(entryPkDTO.getKey());
        }
        for (int i = 1; i < 8; i++) {
            if (xqsList.contains(String.valueOf(i))) {
                continue;
            } else {
                groupByXqs.put(String.valueOf(i), null);
            }
        }
        return groupByXqs;
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
        return pkMapper.getById(id);
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
        Integer jsz = pkDTO.getJsz();
        if (jsz != null) {
            String skjsnyr = null;
            try {
                skjsnyr = DateUtils.getBeforeOrAfterDate(pkDTO.getSkqsnyr(), jsz*7);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            pk.setSkjsnyr(skjsnyr);
        }
        if (pkService.save(pk)) {
            return savePkKc(pkDTO, pk);
        }
        return false;
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
        if (pkService.updateById(pk)) {
            List<KcDTO> kcDTOs = pkDTO.getKcDTOs();
            if (kcDTOs != null && kcDTOs.size() > 0) {
                if (removePkKc(pkDTO)) {
                    return savePkKc(pkDTO, pk);
                }
                return false;
            }
            return true;
        }
        return false;
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
        if (pkService.updateById(pk)) {
            return removePkKc(pkDTO);
        }
        return false;
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
            pks.add(pk);
        }
        return pkService.saveBatch(pks);
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
     * 新增排课课程关系
     * @param pkDTO
     * @param pk
     * @return
     */
    private boolean savePkKc(PkDTO pkDTO, Pk pk) {
        List<KcDTO> kcDTOs = pkDTO.getKcDTOs();
        List<PkKc> pkKcList = new ArrayList<>();
        for (KcDTO kcDTO : kcDTOs) {
            PkKc pkKc = new PkKc();
            pkKc.setKcId(kcDTO.getId());
            pkKc.setPkId(pk.getId());
            pkKcList.add(pkKc);
        }
        return pkKcService.saveBatch(pkKcList);
    }

    /**
     * 删除排课课程关系
     * @param pkDTO
     * @return
     */
    private boolean removePkKc(PkDTO pkDTO) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pk_id", pkDTO.getId());
        return pkKcService.removeByMap(map);
    }

}
