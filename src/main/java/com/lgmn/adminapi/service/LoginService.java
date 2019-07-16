package com.lgmn.adminapi.service;

import com.alibaba.fastjson.JSONObject;
import com.lgmn.adminapi.dto.uma.ExitLoginDto;
import com.lgmn.adminapi.dto.uma.LoginDto;
import com.lgmn.common.result.Result;
import com.lgmn.common.result.ResultEnum;
import com.lgmn.userservices.basic.dto.LgmnUserDto;
import com.lgmn.userservices.basic.entity.LgmnUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class LoginService {

    static String regxPhone = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    static String regxPass = "^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)(?!([^(0-9a-zA-Z)])+$).{8,32}$";

    @Autowired
    UserService userService;

    @Value("${lgmn.token-url}")
    String tokenUrl;

    @Value("${lgmn.exitLogin-url}")
    String exitLoginUrl;

    public Result exitLogin (ExitLoginDto exitLoginDto) {
        RestTemplate restTemplate = getRestTemplate();
        ResponseEntity<JSONObject> responseEntity = restTemplate.getForEntity(exitLoginUrl + "?access_token=" + exitLoginDto.getAccessToken(), JSONObject.class);
        JSONObject responseResult = responseEntity.getBody();
        String msg = "";
        if (responseResult.containsKey("message")) {
            msg = responseResult.getString("message");
        } else if (responseResult.containsKey("error")) {
            msg = "token无效";
        }
        Result result = new Result();
        result.setMessage(msg);
        result.setCode(String.valueOf(responseEntity.getStatusCodeValue()));
        return result;
    }

    public Result login (LoginDto loginDto) throws Exception {
        if (!Pattern.matches(regxPhone, loginDto.getPhone())) return Result.error(ResultEnum.PHONE_ERROR);
        if (!Pattern.matches(regxPass, loginDto.getPassword())) return Result.error(ResultEnum.PASS_ERROR);
//        List<LgmnUserEntity> lgmnUserEntities = userService.getUserByPhone(loginDto.getPhone());
        LgmnUserDto lgmnUserDto = new LgmnUserDto();
        lgmnUserDto.setAccount(loginDto.getPhone());
        List<LgmnUserEntity> lgmnUserEntities = userService.list(lgmnUserDto);
        if (lgmnUserEntities.size() <= 0) return Result.error(ResultEnum.DATA_NOT_EXISTS);
        JSONObject responseResult = restTemplateLogin(loginDto);
        if (responseResult.containsKey("error_description")) {
            return Result.error(ResultEnum.PASS_ERROR);
        }
        return Result.success(responseResult);
    }

    public JSONObject restTemplateLogin (LoginDto loginDto) {
        // 不能用Map,
        MultiValueMap<String, String> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("grant_type", "password");
        postParameters.add("username", loginDto.getPhone());
        postParameters.add("password", loginDto.getPassword());

        RestTemplate restTemplate = getRestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth("android", "android", StandardCharsets.UTF_8);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity(postParameters, httpHeaders);
        return restTemplate.exchange(tokenUrl, HttpMethod.POST, httpEntity, JSONObject.class).getBody();
    }

    public RestTemplate getRestTemplate () {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            // Ignore 400
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401 && response.getRawStatusCode() != 402 && response.getRawStatusCode() != 403 && response.getRawStatusCode() != 405 && response.getRawStatusCode() != 500) {
                    super.handleError(response);
                }
            }
        });
        return restTemplate;
    }

}
