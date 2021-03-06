package com.itts.technologytransactionservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.mapper.JsBmMapper;
import com.itts.technologytransactionservice.mapper.JsCjRcMapper;
import com.itts.technologytransactionservice.mapper.JsLcKzMapper;
import com.itts.technologytransactionservice.model.TJsBm;
import com.itts.technologytransactionservice.model.TJsCjRc;
import com.itts.technologytransactionservice.model.TJsCjRcDto;
import com.itts.technologytransactionservice.model.TJsLcKz;
import com.itts.technologytransactionservice.service.JsBmService;
import com.itts.technologytransactionservice.service.JsCjRcService;
import com.itts.technologytransactionservice.service.JsLcKzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.*;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.MAX_BIDHISTORY_ERROR;
import static com.itts.common.enums.ErrorCodeEnum.MIN_BIDHISTORY_ERROR;


@Service
@Primary
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class JsCjRcServiceImpl extends ServiceImpl<JsCjRcMapper, TJsCjRc> implements JsCjRcService {
	@Autowired
	private JsCjRcMapper jsCjRcMapper;
    @Autowired
    private JsBmMapper jsBmMapper;
    @Autowired
    private JsLcKzMapper jsLcKzMapper;
	@Override
	public PageInfo page(Query query) {
		PageHelper.startPage(query.getPageNum(), query.getPageSize());
		List<TJsCjRcDto> list = jsCjRcMapper.listRcJy(query);
		PageInfo<TJsCjRcDto> page = new PageInfo<>(list);
		return page;
	}

    /**
     * ???????????????
     * @param id
     * @return
     */
    @Override
    public boolean removeByIdCjRc(String id) {
        log.info("??????????????? - ??????id:{}??????????????????",id);
        TJsCjRc tJsCjRc = new TJsCjRc();
        tJsCjRc.setId(new BigInteger(id));
        tJsCjRc.setIsDelete(1);
        tJsCjRc.setGxsj(new Date());
        jsCjRcMapper.updateById(tJsCjRc);
        return true;
    }
    //????????????
    @Override
    public boolean saveCjRc(TJsCjRcDto tJsCjRcDto,TJsBm tJsBm) {
/*        Map<String,Object> map=new HashMap<>();
        map.put("hdId",tJsCjRcDto.getHdId());
        map.put("userId",tJsCjRcDto.getUserId());
        List<TJsBm> list = jsBmMapper.list(map);*/
        TJsCjRc tJsCjRc=new TJsCjRc();
        BeanUtils.copyProperties(tJsCjRcDto,tJsCjRc);
        //???????????????????????????????????????????????????????????????????????????????????????????????????????????????
        if(tJsCjRcDto.getCgId()!=null){
            //??????????????????????????????????????????????????????????????????????????????????????????
            Map<String,Object> cjrcmap=new HashMap<>();
            cjrcmap.put("cgId",tJsCjRcDto.getCgId());
            List<TJsCjRc> tJsCjRcs = jsCjRcMapper.listMax(cjrcmap);
            Map<String,Object> cjmap=new HashMap<String,Object>();
            cjmap.put("type",1);
            cjmap.put("cgId",tJsCjRcDto.getCgId());
            List<TJsLcKz> listLckz = jsLcKzMapper.list(cjmap);
            listLckz.get(0).setDqzgjg(tJsCjRcDto.getJjje());
            //???????????????????????????????????????????????????0
            listLckz.get(0).setJjjgzt(0);
                listLckz.get(0).setBmId(tJsBm.getId());
            tJsCjRc.setCjsj(new Date());
            tJsCjRc.setGxsj(new Date());
            if(tJsCjRcs.size()>0){
                if(tJsCjRcs.get(0)==null){

                }else{
                    if(tJsCjRc.getJjje().compareTo(tJsCjRcs.get(0).getJjje())== 1){

                    }else{
                        throw new ServiceException(MAX_BIDHISTORY_ERROR);
                    }
                }
            }
            jsCjRcMapper.saveTJsCjRcCg(tJsCjRc);
            jsLcKzMapper.updateTJsLcKzCg(listLckz.get(0));
            //save(tJsCjRc);
            //jsLcKzMapper.updateById(listLckz.get(0));
            return true;
        }
        if(tJsCjRcDto.getXqId()!=null){
            //??????????????????????????????????????????????????????????????????????????????????????????
            Map<String,Object> cjrcmap=new HashMap<>();
            cjrcmap.put("xqId",tJsCjRcDto.getXqId());
            List<TJsCjRc> tJsCjRcs = jsCjRcMapper.listMax(cjrcmap);
            Map<String,Object> xqmap=new HashMap<String,Object>();
            xqmap.put("type",0);
            xqmap.put("xqId",tJsCjRcDto.getXqId());
            List<TJsLcKz> listLckz = jsLcKzMapper.list(xqmap);
            listLckz.get(0).setDqzgjg(tJsCjRcDto.getJjje());
            //???????????????????????????????????????????????????0
            listLckz.get(0).setJjjgzt(0);
                listLckz.get(0).setBmId(tJsBm.getId());
            tJsCjRc.setCjsj(new Date());
            tJsCjRc.setGxsj(new Date());
            if(tJsCjRcs.size()>0){
                if(tJsCjRcs.get(0)==null){

                }else{
                    if(tJsCjRc.getJjje().compareTo(tJsCjRcs.get(0).getJjje())== -1){

                    }else{
                        throw new ServiceException(MIN_BIDHISTORY_ERROR);
                    }
                }
            }
            jsCjRcMapper.saveTJsCjRcXq(tJsCjRc);
            jsLcKzMapper.updateTJsLcKzXq(listLckz.get(0));
            //save(tJsCjRc);
            //jsLcKzMapper.updateById(listLckz.get(0));
            return true;
        }
        return false;
    }
    /**
     * ??????????????????id
     * @return
     */
    public Long getUserId() {
        LoginUser loginUser = threadLocal.get();
        Long userId;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        } else {
            //throw new ServiceException(GET_THREADLOCAL_ERROR);
            userId=null;
        }
        return userId;
    }

}
