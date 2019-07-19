package com.lgmn.adminapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.lgmn.adminapi.dto.user.LoginDto;
import com.lgmn.adminapi.dto.user.LogoutDto;
import com.lgmn.adminapi.dto.user.UpdatePwdDto;
import com.lgmn.adminapi.service.*;
import com.lgmn.common.domain.LgmnUserInfo;
import com.lgmn.common.result.Result;
import com.lgmn.common.result.ResultEnum;
import com.lgmn.common.utils.ObjectTransfer;
import com.lgmn.userservices.basic.dto.LgmnUserDto;
import com.lgmn.userservices.basic.entity.LgmnPermissionEntity;
import com.lgmn.userservices.basic.entity.LgmnUserEntity;
import com.lgmn.userservices.basic.util.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Api(description = "用户信息api")
@RestController
@RequestMapping("/user")
public class UserController {
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${lgmn.token-url}")
    String tokenUrl;

    @Value("${lgmn.exitLogin-url}")
    String exitLoginUrl;

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    RolePermissionService rolePermissionService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    UserRoleService userRoleService;

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public Result login(@RequestBody LoginDto dto){
        try {
            LgmnUserDto lgmnUserDto = new LgmnUserDto();
            lgmnUserDto.setAccount(dto.getUserName());
            List<LgmnUserEntity> lgmnUserEntities = userService.list(lgmnUserDto);
            if (lgmnUserEntities.size() <= 0) {
                return Result.error(ResultEnum.DATA_NOT_EXISTS);
            }
        } catch (Exception e) {
            logger.error("PersonalController.login:" + e.getMessage());
            return Result.serverError(e.getMessage());
        }
        return Result.success(loginUser(dto));
    }

    @ApiOperation(value = "注销登录")
    @PostMapping("/logout")
    public Result exitLogin(@RequestBody LogoutDto accessTokenDto) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            // Ignore 400
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401 && response.getRawStatusCode() != 402 && response.getRawStatusCode() != 403 && response.getRawStatusCode() != 405 && response.getRawStatusCode() != 500 ) {
                    super.handleError(response);
                }
            }
        });
        ResponseEntity<JSONObject> responseEntity = restTemplate.getForEntity(exitLoginUrl + "?access_token=" + accessTokenDto.getAccessToken(), JSONObject.class);
        JSONObject responseResult = responseEntity.getBody();
        String msg="";
        if(responseResult.containsKey("message")){
            msg=responseResult.getString("message");
        } else if(responseResult.containsKey("error")){
            msg="token无效";
        }
        Result result=new Result();
        result.setMessage(msg);
        result.setCode(String.valueOf(responseEntity.getStatusCodeValue()));
        return result;
    }

    @ApiOperation(value = "获取用户基本信息", notes = "token 传输方式：Bearer + token; 注意 Bearer 与token 之间要有空格; name 字段不用理会")
    @PostMapping("/getuserinfo")
    public Result getSysUserInfo(@RequestHeader String Authorization, Principal principal) {
        LgmnUserInfo lgmnUserEntity = UserUtil.getCurrUser(principal);
        try {
//            String userId = lgmnUserEntity.getId();
//            List<SdSysUserInfoEntity> sysUserInfos = sysUserInfoService.getSysUserInfoByUid(userId);
//            SysUserInfoVo sysUserInfoVo = new SysUserInfoVo().getVo(sysUserInfos.get(0), SysUserInfoVo.class);
//            ObjectTransfer.transValue(lgmnUserEntity, sysUserInfoVo);
//            sysUserInfoVo.setAccess(getPermissionByUser(userId));
            return Result.success(lgmnUserEntity);
        } catch (Exception e) {
            logger.error("PersonalController.getSysUserInfo:" + e.getMessage());
            return Result.serverError(e.getMessage());
        }
    }

    @ApiOperation(value = "更新用户密码", notes = "token 传输方式：Bearer + token; 注意 Bearer 与token 之间要有空格; name 字段不用理会")
    @PostMapping("/updatepassword")
    public Result updatepassword(@RequestHeader String Authorization, @RequestBody UpdatePwdDto dto, Principal principal) {
        LgmnUserInfo lgmnUserEntity = UserUtil.getCurrUser(principal);
        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            Result result=Result.error(ResultEnum.NOT_SCHEDULED_ERROR);
            result.setMessage("请确保新密码和确认新密码一致");
            return result;
        }
        LoginDto loginDto = new LoginDto();
        LgmnUserInfo lgmnUserInfo = UserUtil.getCurrUser(principal);
        loginDto.setUserName(lgmnUserInfo.getAccount().substring(1));
        loginDto.setPassword(dto.getOldPassword());

        return tryLogin(dto.getNewPassword(), loginDto, lgmnUserInfo.getId());
    }

    private Result tryLogin(String newPassword, LoginDto loginDto, String userId) {
        JSONObject loginResult = loginUser(loginDto);

        if(loginResult.containsKey("access_token")) {
            LgmnUserEntity user = null;
            try {
                user = userService.getById(userId);
            } catch (Exception e) {
                logger.error("PersonalController:resetPassword:" + e.getMessage());
                return Result.serverError(e.getMessage());
            }
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
            userService.update(user);
            return Result.success("修改成功");
        }else{
            Result result=Result.error(ResultEnum.NOT_SCHEDULED_ERROR);
            result.setMessage("旧密码错误");
            return result;
        }
    }

    private List<String> getPermissionByUser(String userId){
        List<String> res=new ArrayList<>();
        try {
            List<LgmnPermissionEntity> permissionList = permissionService.queryUserPermission(userId);
            if(null!=permissionList && permissionList.size()>0) {
                for (LgmnPermissionEntity permission : permissionList) {
                    res.add(permission.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return res;
        }
    }

    private JSONObject loginUser(LoginDto loginDto) {
        // 不能用Map,
        MultiValueMap<String, String> postParameters = new LinkedMultiValueMap<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth("android", "android", StandardCharsets.UTF_8);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            // Ignore 400
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401 && response.getRawStatusCode() != 402 && response.getRawStatusCode() != 403 && response.getRawStatusCode() != 405 && response.getRawStatusCode() != 500 ) {
                    super.handleError(response);
                }
            }
        });
        postParameters.add("username", "B" + loginDto.getUserName());
        postParameters.add("grant_type", "password");
        postParameters.add("password", loginDto.getPassword());
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity(postParameters, httpHeaders);
        JSONObject responseResult = restTemplate.exchange(tokenUrl, HttpMethod.POST, httpEntity, JSONObject.class).getBody();
        return responseResult;
    }
}