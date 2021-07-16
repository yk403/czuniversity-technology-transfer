package com.itts.userservice.service.spzb.impl.thirdparty;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.userservice.config.HuaWeiLiveConfig;
import com.itts.userservice.request.spzb.thirdparty.DealVideoRequest;
import com.itts.userservice.request.spzb.thirdparty.GetTokenRequest;
import com.itts.userservice.response.thirdparty.GetAssetInfoResponse;
import com.itts.userservice.service.spzb.thirdparty.HuaWeiLiveService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/7/15
 */
@Slf4j
@Service
public class HuaWeiLiveServiceImpl implements HuaWeiLiveService {

    @Autowired
    private HuaWeiLiveConfig huaWeiLiveConfig;

    @Resource
    private RestTemplate restTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取华为云Token
     */
    @Override
    public String getToken() {

        GetTokenRequest request = new GetTokenRequest();
        GetTokenRequest.Auth auth = new GetTokenRequest.Auth();
        GetTokenRequest.Identity identity = new GetTokenRequest.Identity();
        GetTokenRequest.Scope scope = new GetTokenRequest.Scope();
        GetTokenRequest.Password password = new GetTokenRequest.Password();
        GetTokenRequest.User user = new GetTokenRequest.User();
        GetTokenRequest.Domain domain = new GetTokenRequest.Domain();
        GetTokenRequest.ScopeParams scopeParams = new GetTokenRequest.ScopeParams();

        domain.setName(huaWeiLiveConfig.getAccount());

        user.setDomain(domain);
        user.setName(huaWeiLiveConfig.getUserName());
        user.setPassword(huaWeiLiveConfig.getPassword());

        password.setUser(user);

        identity.setPassword(password);
        identity.setMethods(Lists.newArrayList("password"));

        scopeParams.setId(huaWeiLiveConfig.getProjectId());

        scope.setProject(scopeParams);

        auth.setIdentity(identity);
        auth.setScope(scope);

        request.setAuth(auth);

        log.info("【华为云第三方接口】获取token， 请求参数：{}", JSONUtil.toJsonStr(request));

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());


        log.info("【华为云第三方接口】获取token， 请求地址：{}", huaWeiLiveConfig.getTokenUrl() + HuaWeiLiveConfig.GET_TOKEN_URL);
        String json = JSONUtil.toJsonStr(request);
        HttpEntity<String> formEntity = new HttpEntity<String>(json, headers);

        ResponseEntity<String> response = restTemplate.exchange(huaWeiLiveConfig.getTokenUrl() + HuaWeiLiveConfig.GET_TOKEN_URL, HttpMethod.POST, formEntity, String.class);

        log.info("【华为云第三方接口】获取token， 响应结果：{}", response.getBody());

        HttpHeaders head = response.getHeaders();
        if (head == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        List<String> token = head.get("X-Subject-Token");
        log.info("【华为云第三方接口】获取token， token：{}", JSONUtil.toJsonStr(token));
        if (CollectionUtils.isEmpty(token)) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        //缓存token， 过期时间23小时
        redisTemplate.opsForValue().set(HuaWeiLiveConfig.CACHE_TOKEN_KEY, token.get(0), 23, TimeUnit.HOURS);

        return token.get(0);
    }

    /**
     * 处理视频
     */
    @Override
    public String dealLive(String assetId) {

        String token = "";
        Object tokenObj = redisTemplate.opsForValue().get(HuaWeiLiveConfig.CACHE_TOKEN_KEY);

        if (tokenObj == null) {
            token = this.getToken();
        } else {
            token = tokenObj.toString();
        }


        DealVideoRequest request = new DealVideoRequest();
        request.setAssetId(assetId);
        request.setTemplateGroupName(huaWeiLiveConfig.getTemplateGroupName());

        log.info("【华为云第三方接口】处理视频， 请求参数：{}", JSONUtil.toJsonStr(request));

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", token);
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());

        String json = JSONUtil.toJsonStr(request);
        HttpEntity<String> formEntity = new HttpEntity<String>(json, headers);

        log.info("【华为云第三方接口】处理视频， 请求地址：{}", huaWeiLiveConfig.getVideoUrl() + String.format(HuaWeiLiveConfig.DEAL_VIDEO_URL, huaWeiLiveConfig.getProjectId()));
        ResponseEntity<String> response = restTemplate.exchange(huaWeiLiveConfig.getVideoUrl() + String.format(HuaWeiLiveConfig.DEAL_VIDEO_URL, huaWeiLiveConfig.getProjectId()), HttpMethod.POST, formEntity, String.class);

        log.info("【华为云第三方接口】处理视频， 响应结果：{}", response.getBody());

        JSONObject jsonObj = JSONUtil.parseObj(response.getBody());
        if (jsonObj == null) {
            throw new WebException(ErrorCodeEnum.HUA_WEI_YUN_DEAL_VIDEO_ERROR);
        }

        if (jsonObj.containsKey("asset_id")) {

            String newAssetId = jsonObj.get("asset_id").toString();

            if (StringUtils.isNotBlank(newAssetId)) {
                return newAssetId;
            }
        }

        return null;
    }

    /**
     * 通过ID获取媒资信息
     */
    @Override
    public GetAssetInfoResponse getVideoInfo(String assetId, String category) {

        String token = "";
        Object tokenObj = redisTemplate.opsForValue().get(HuaWeiLiveConfig.CACHE_TOKEN_KEY);

        if (tokenObj == null) {
            token = this.getToken();
        } else {
            token = tokenObj.toString();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", token);

        HttpEntity<String> formEntity = new HttpEntity<String>(null, headers);

        String params = "";
        if (StringUtils.isNotBlank(category)) {
            params = "?asset_id=" + assetId + "&categories = " + category;
        } else {
            params = "?asset_id=" + assetId;
        }

        log.info("【华为云第三方接口】获取媒资详情， 请求地址：{}", huaWeiLiveConfig.getVideoUrl() + String.format(HuaWeiLiveConfig.GET_ASSET_DETAIL_URL, huaWeiLiveConfig.getProjectId()));
        ResponseEntity<String> response = restTemplate.exchange(huaWeiLiveConfig.getVideoUrl() + String.format(HuaWeiLiveConfig.GET_ASSET_DETAIL_URL, huaWeiLiveConfig.getProjectId()) + params, HttpMethod.GET, formEntity, String.class);

        log.info("【华为云第三方接口】获取媒资详情， 响应结果：{}", response.getBody());

        if (response.getBody() == null) {
            throw new WebException(ErrorCodeEnum.HUA_WEI_YUN_DEAL_VIDEO_ERROR);
        }

        String responseStr = response.getBody();

        if (StringUtils.isNotBlank(responseStr)) {

            GetAssetInfoResponse getAssetInfoResponse = JSONUtil.toBean(JSONUtil.parseObj(responseStr), GetAssetInfoResponse.class);

            return getAssetInfoResponse;
        }

        return null;

    }
}