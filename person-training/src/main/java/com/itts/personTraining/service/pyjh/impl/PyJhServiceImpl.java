package com.itts.personTraining.service.pyjh.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.common.CommonUtils;
import com.itts.personTraining.dto.PyJhDTO;
import com.itts.personTraining.dto.XsMsgDTO;
import com.itts.personTraining.mapper.fjzy.FjzyMapper;
import com.itts.personTraining.mapper.jhKc.JhKcMapper;
import com.itts.personTraining.mapper.pc.PcMapper;
import com.itts.personTraining.mapper.pcXs.PcXsMapper;
import com.itts.personTraining.mapper.pk.PkMapper;
import com.itts.personTraining.mapper.sz.SzMapper;
import com.itts.personTraining.mapper.xs.XsMapper;
import com.itts.personTraining.model.fjzy.Fjzy;
import com.itts.personTraining.model.jhKc.JhKc;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.model.pyjh.PyJh;
import com.itts.personTraining.mapper.pyjh.PyJhMapper;
import com.itts.personTraining.model.sz.Sz;
import com.itts.personTraining.request.fjzy.AddFjzyRequest;
import com.itts.personTraining.service.jhKc.JhKcService;
import com.itts.personTraining.service.pc.PcService;
import com.itts.personTraining.service.pyjh.PyJhService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.*;

/**
 * <p>
 * ??????????????? ???????????????
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class PyJhServiceImpl extends ServiceImpl<PyJhMapper, PyJh> implements PyJhService {

    @Resource
    private PyJhMapper pyJhMapper;
    @Autowired
    private PyJhService pyJhService;
    @Autowired
    private PcService pcService;
    @Resource
    private SzMapper szMapper;
    @Resource
    private FjzyMapper fjzyMapper;
    @Resource
    private XsMapper xsMapper;
    @Resource
    private PcXsMapper pcXsMapper;
    @Resource
    private PkMapper pkMapper;

    /**
     * ????????????????????????
     * @param pageNum
     * @param pageSize
     * @param pch
     * @param jylx
     * @param jhmc
     * @param fjjgId
     * @return
     */
    @Override
    public PageInfo<PyJhDTO> findByPage(Integer pageNum, Integer pageSize, String pch, String jylx, String jhmc, Long fjjgId) {
        log.info("??????????????? - ????????????????????????????????????,?????????:{},????????????:{},????????????:{},????????????ID:{}???",pch,jylx,jhmc,fjjgId);
        PageHelper.startPage(pageNum, pageSize);
        List<PyJhDTO> pyJhDTOList = pyJhMapper.findByPage(pch,jylx,jhmc,fjjgId);
        return new PageInfo<>(pyJhDTOList);
    }

    /**
     * ??????id??????????????????
     * @param id
     * @return
     */
    @Override
    public PyJhDTO get(Long id) {
        log.info("??????????????? - ??????id:{}?????????????????????",id);
        QueryWrapper<PyJh> pyJhQueryWrapper = new QueryWrapper<>();
        pyJhQueryWrapper.eq("sfsc",false)
                .eq("id",id);
        PyJhDTO pyJhDTO = new PyJhDTO();
        PyJh pyJh = pyJhMapper.selectOne(pyJhQueryWrapper);
        BeanUtils.copyProperties(pyJh,pyJhDTO);
        Pc pc = pcService.get(pyJhDTO.getPcId());
        pyJhDTO.setPch(pc.getPch());
        pyJhDTO.setJylx(pc.getJylx());
        if (StringUtils.isNotBlank(pyJh.getFjzyId())) {

            List<AddFjzyRequest> fjzys = fjzyMapper.selectList(new QueryWrapper<Fjzy>().eq("fjzy_id", pyJh.getFjzyId()))
                    .stream().map(obj -> {

                        AddFjzyRequest request = new AddFjzyRequest();
                        BeanUtils.copyProperties(obj, request);
                        return request;

                    }).collect(Collectors.toList());

            pyJhDTO.setFjzys(fjzys);
        }
        return pyJhDTO;
    }

    /**
     * ??????????????????
     * @param pyJhDTO
     * @return
     */
    @Override
    public boolean add(PyJhDTO pyJhDTO) {
        log.info("??????????????? - ??????????????????:{}???",pyJhDTO);
        PyJh pyJh = new PyJh();
        Long userId = getUserId();
        pyJhDTO.setCjr(userId);
        pyJhDTO.setGxr(userId);
        BeanUtils.copyProperties(pyJhDTO,pyJh);
        if (!CollectionUtils.isEmpty(pyJhDTO.getFjzys())) {

            String fjzyId = CommonUtils.generateUUID();
            pyJh.setFjzyId(fjzyId);

            List<AddFjzyRequest> fjzys = pyJhDTO.getFjzys();

            for (AddFjzyRequest addFjzy : fjzys) {

                Fjzy fjzy = new Fjzy();
                BeanUtils.copyProperties(addFjzy, fjzy);

                fjzy.setFjzyId(fjzyId);
                fjzy.setCjr(userId);
                fjzy.setCjsj(new Date());

                fjzyMapper.insert(fjzy);
            }
        }
        return pyJhService.save(pyJh);
    }

    /**
     * ??????????????????
     * @param pyJhDTO
     * @return
     */
    @Override
    public boolean update(PyJhDTO pyJhDTO) {
        log.info("??????????????? - ??????????????????:{}???",pyJhDTO);
        pyJhDTO.setGxr(getUserId());
        PyJh pyJh = new PyJh();
        BeanUtils.copyProperties(pyJhDTO,pyJh);
        if (!CollectionUtils.isEmpty(pyJhDTO.getFjzys())) {

            List<AddFjzyRequest> fjzys = pyJhDTO.getFjzys();

            String fjzyId = pyJh.getFjzyId();

            //?????????????????????????????????????????????
            if (StringUtils.isNotBlank(fjzyId)) {

                //????????????????????????????????????????????????????????????
                fjzyMapper.delete(new QueryWrapper<Fjzy>().eq("fjzy_id", pyJh.getFjzyId()));

            } else {

                fjzyId = CommonUtils.generateUUID();
            }

            for (AddFjzyRequest addFjzy : fjzys) {

                Fjzy fjzy = new Fjzy();
                BeanUtils.copyProperties(addFjzy, fjzy);

                fjzy.setFjzyId(fjzyId);
                fjzy.setCjr(getUserId());
                fjzy.setCjsj(new Date());

                fjzyMapper.insert(fjzy);
            }
        }
        return pyJhService.updateById(pyJh);
    }

    /**
     * ??????????????????
     * @param pyJhDTO
     * @return
     */
    @Override
    public boolean delete(PyJhDTO pyJhDTO) {
        log.info("??????????????? - ??????????????????:{}???",pyJhDTO);
        //??????????????????
        pyJhDTO.setSfsc(true);
        pyJhDTO.setGxr(getUserId());
        PyJh pyJh = new PyJh();
        BeanUtils.copyProperties(pyJhDTO,pyJh);
        return pyJhService.updateById(pyJh);
    }

    /**
     * ????????????????????????
     * @param ids
     * @return
     */
    @Override
    public boolean issueBatch(List<Long> ids) {
        log.info("??????????????? - ????????????????????????,ids:{}???",ids);
        List<PyJh> pyJhs = pyJhMapper.selectBatchIds(ids);
        Long userId = getUserId();
        for (PyJh pyJh : pyJhs) {
            pyJh.setSfxf(true);
            pyJh.setXfsj(new Date());
            pyJh.setGxr(userId);
        }
        return pyJhService.updateBatchById(pyJhs);
    }

    /**
     * ????????????id????????????????????????(???)
     * @return
     */
    @Override
    public List<PyJhDTO> findByYh(Long pcId) {
        Long userId = getUserId();
        log.info("??????????????? - ????????????id:{}????????????????????????(???)???",userId);
        String userCategory = getUserCategory();
        List<PyJhDTO> pyJhDTOList = null;
        switch(userCategory) {
            case "postgraduate":
            case "broker":
                XsMsgDTO xsMsg = xsMapper.getByYhId(userId);
                if (xsMsg == null) {
                    throw new ServiceException(STUDENT_MSG_NOT_EXISTS_ERROR);
                }
                if (pcId == null) {
                   List<Pc>  pcList = pcXsMapper.findPcByXsId(xsMsg.getId());
                   if (CollectionUtils.isEmpty(pcList)) {
                       throw new ServiceException(BATCH_NUMBER_ISEMPTY_NO_MSG_ERROR);
                   }
                   pyJhDTOList = pyJhMapper.findByPcId(pcList.get(0).getId());
                } else {
                    pyJhDTOList = pyJhMapper.findByPcId(pcId);
                }
                break;
            case "tutor":
                Sz yzyds = szMapper.getSzByYhId(userId);
                if (yzyds == null) {
                    throw new ServiceException(TEACHER_MSG_NOT_EXISTS_ERROR);
                }
                if (pcId == null) {
                    List<Pc> pcList = pcXsMapper.findByYzydsIdOrQydsId(yzyds.getId(),null);
                    if (CollectionUtils.isEmpty(pcList)) {
                        throw new ServiceException(BATCH_NUMBER_ISEMPTY_NO_MSG_ERROR);
                    }
                    pyJhDTOList = pyJhMapper.findByPcId(pcList.get(0).getId());
                } else {
                    pyJhDTOList = pyJhMapper.findByPcId(pcId);
                }
                break;
            //????????????
            case "corporate_mentor":
                Sz qyds = szMapper.getSzByYhId(userId);
                if (qyds == null) {
                    throw new ServiceException(TEACHER_MSG_NOT_EXISTS_ERROR);
                }
                if (pcId == null) {
                    List<Pc> pcList = pcXsMapper.findByYzydsIdOrQydsId(null,qyds.getId());
                    if (CollectionUtils.isEmpty(pcList)) {
                        throw new ServiceException(BATCH_NUMBER_ISEMPTY_NO_MSG_ERROR);
                    }
                    pyJhDTOList = pyJhMapper.findByPcId(pcList.get(0).getId());
                } else {
                    pyJhDTOList = pyJhMapper.findByPcId(pcId);
                }
                break;
            case "teacher":
                Sz skjs = szMapper.getSzByYhId(userId);
                if (skjs == null) {
                    throw new ServiceException(TEACHER_MSG_NOT_EXISTS_ERROR);
                }
                if (pcId == null) {
                    List<Pc> pcList = pkMapper.findPcsBySzId(skjs.getId());
                    if (CollectionUtils.isEmpty(pcList)) {
                        throw new ServiceException(BATCH_NUMBER_ISEMPTY_NO_MSG_ERROR);
                    }
                    pyJhDTOList = pyJhMapper.findByPcId(pcList.get(0).getId());
                } else {
                    pyJhDTOList = pyJhMapper.findByPcId(pcId);
                }
                break;
            case "school_leader":
            case "administrator":
                if (pcId == null) {
                    List<Pc> pcList = pyJhMapper.findAllPc(getFjjgId());
                    if (CollectionUtils.isEmpty(pcList)) {
                        throw new ServiceException(BATCH_NUMBER_ISEMPTY_NO_MSG_ERROR);
                    }
                    pyJhDTOList = pyJhMapper.findByPcId(pcList.get(0).getId());
                } else {
                    pyJhDTOList = pyJhMapper.findByPcId(pcId);
                }
                break;
            default:
                break;
        }
        return pyJhDTOList;
    }

    /**
     * ??????????????????id
     * @return
     */
    private Long getUserId() {
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
     * ??????????????????id????????????
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
     * ??????????????????ID
     */
    public Long getFjjgId() {
        LoginUser loginUser = threadLocal.get();
        Long fjjgId;
        if (loginUser != null) {
            fjjgId = loginUser.getFjjgId();
        } else {
            throw new ServiceException(GET_THREADLOCAL_ERROR);
        }
        return fjjgId;
    }
}
