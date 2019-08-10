package com.lgmn.adminapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.lgmn.adminapi.dto.LgmnUser.*;
import com.lgmn.adminapi.service.*;
import com.lgmn.adminapi.vo.UserInfoVo;
import com.lgmn.adminapi.vo.UserVo;
import com.lgmn.common.domain.LgmnPage;
import com.lgmn.common.domain.LgmnUserInfo;
import com.lgmn.common.result.Result;
import com.lgmn.common.result.ResultEnum;
import com.lgmn.common.utils.ObjectTransfer;
import com.lgmn.userservices.basic.dto.LgmnUserDto;
import com.lgmn.userservices.basic.entity.LgmnPermissionEntity;
import com.lgmn.userservices.basic.entity.LgmnUserEntity;
import com.lgmn.userservices.basic.util.UserUtil;
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


@RestController
@RequestMapping("/user")
public class LgmnUserController {

    private final static Logger logger = LoggerFactory.getLogger(LgmnUserController.class);

    @Value("${lgmn.token-url}")
    String tokenUrl;

    @Value("${lgmn.exitLogin-url}")
    String exitLoginUrl;

    @Autowired
    LgmnUserApiService service;

    @Autowired
    RoleService roleService;
    @Autowired
    RolePermissionService rolePermissionService;
    @Autowired
    LgmnPermissionApiService permissionService;
    @Autowired
    UserRoleService userRoleService;

    @PostMapping("/page")
    public Result page (@RequestBody LgmnUserDto dto) {
        try {
            LgmnPage<LgmnUserEntity> page = service.page(dto);
            return Result.success(page);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/update")
    public Result update(@RequestHeader String Authorization, @RequestBody UpdateLgmnUserDto updateDto, Principal principal){
        String id = updateDto.getId();
        LgmnUserEntity lgmnUserEntity = null;
        try {
            lgmnUserEntity = service.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (lgmnUserEntity==null) {
            return Result.error(ResultEnum.DATA_NOT_EXISTS);
        }
        // LgmnUser

        lgmnUserEntity.setNikeName(updateDto.getNikeName());
        lgmnUserEntity.setAccount(updateDto.getAccount());
        LgmnUserEntity saveUser = service.update(lgmnUserEntity);

        return Result.success("添加成功");
    }

    @PostMapping("/add")
    public Result add(@RequestHeader String Authorization, @RequestBody SaveLgmnUserDto saveDto, Principal principal){
        String account = saveDto.getAccount();
        List<LgmnUserEntity> lgmnUserEntities = null;
        try {
            lgmnUserEntities = service.getUserByAccount(account);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (lgmnUserEntities.size() > 0) {
            return Result.error(ResultEnum.DATA_EXISTS);
        }
        // LgmnUser
        LgmnUserEntity lgmnUserEntity = new LgmnUserEntity();
        lgmnUserEntity.setAvatar("");
        lgmnUserEntity.setNikeName(saveDto.getNikeName());
        lgmnUserEntity.setAccount(account);
        lgmnUserEntity.setUserType(1);
        lgmnUserEntity.setSalt("123456");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        lgmnUserEntity.setPassword(bCryptPasswordEncoder.encode(saveDto.getPassword()));
        LgmnUserEntity saveUser = service.add(lgmnUserEntity);

        return Result.success("添加成功");
    }

    @PostMapping("/delete/{id}")
    public Result delete (@PathVariable("id") String id) {
        service.deleteById(id);
        return Result.success("删除成功");
    }

    @PostMapping("/detail/{id}")
    public Result detail (@PathVariable("id") String id) {
        LgmnUserEntity entity = service.getById(id);
        UserVo vo=new UserVo();
        try {
            List<String> ignoreFields=new ArrayList<>();
            ignoreFields.add("avatar");
            ObjectTransfer.transValue(entity,vo,ignoreFields);
            vo.getAvatar().add(entity.getAvatar());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success(vo);
    }

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public Result login(@RequestBody LoginDto dto){
        try {
            LgmnUserDto lgmnUserDto = new LgmnUserDto();
            lgmnUserDto.setAccount(dto.getUserName());
            List<LgmnUserEntity> lgmnUserEntities = service.list(lgmnUserDto);
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
            String userId = lgmnUserEntity.getId();
            UserInfoVo sysUserInfoVo = new UserInfoVo().getVo(lgmnUserEntity, UserInfoVo.class);
            sysUserInfoVo.setAccess(getPermissionByUser(userId));
            return Result.success(sysUserInfoVo);
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
                user = service.getById(userId);
            } catch (Exception e) {
                logger.error("PersonalController:resetPassword:" + e.getMessage());
                return Result.serverError(e.getMessage());
            }
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
            service.update(user);
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
        postParameters.add("username", loginDto.getUserName());
        postParameters.add("grant_type", "password");
        postParameters.add("password", loginDto.getPassword());
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity(postParameters, httpHeaders);
        JSONObject responseResult = restTemplate.exchange(tokenUrl, HttpMethod.POST, httpEntity, JSONObject.class).getBody();
        return responseResult;
    }

}