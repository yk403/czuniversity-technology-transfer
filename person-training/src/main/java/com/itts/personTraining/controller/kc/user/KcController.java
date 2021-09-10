package com.itts.personTraining.controller.kc.user;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.service.kc.KcService;
import com.itts.personTraining.service.kcsl.KcsjService;
import com.itts.personTraining.service.pc.PcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(SystemConstant.BASE_URL + "/v1/kc")
@Api(tags = "课程-门户")
public class KcController {

    @Autowired
    private KcService kcService;

    @Autowired
    private PcService pcService;
    /**
     * 根据用户id查询列表
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "根据用户id查询列表")
    public ResponseUtil findByYh(@RequestParam(value = "pcId",required = false) Long pcId) {
        return ResponseUtil.success(kcService.findByYh(pcId));
    }
    /**
     * 根据用户id查询课程表
     * @return
     */
    @GetMapping("/kc/list")
    @ApiOperation(value = "根据用户id查询列表")
    public ResponseUtil findKcByYh(@RequestParam(value = "pcId",required = false) Long pcId) {
        return ResponseUtil.success(kcService.findByPcId(pcId));
    }

    /**
     * 根据用户查询批次列表
     * @return
     */
    @GetMapping("/findByYh")
    @ApiOperation(value = "根据用户查询批次列表")
    public ResponseUtil findByYh(){
        List<Pc> pcList = pcService.findByYh();
        return ResponseUtil.success(pcList);

    }
    /**
     * 根据学员类型查询课程列表
     *
     * @param xylx
     * @return
     */
    @GetMapping("/findByXylx")
    @ApiOperation(value = "根据学员类型查询课程列表")
    public ResponseUtil findByPage(@RequestParam(value = "xylx", required = false) String xylx,
                                   @RequestParam(value = "jylx", required = false) String jylx) {
        return ResponseUtil.success(kcService.findByXylx(xylx,jylx));
    }
}
