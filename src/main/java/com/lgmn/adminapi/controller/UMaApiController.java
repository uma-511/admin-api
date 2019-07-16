package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.uma.ExitLoginDto;
import com.lgmn.adminapi.dto.uma.LoginDto;
import com.lgmn.adminapi.dto.uma.PostLabelRecordDto;
import com.lgmn.adminapi.service.LabelRecordApiService;
import com.lgmn.adminapi.service.LoginService;
import com.lgmn.common.domain.LgmnUserInfo;
import com.lgmn.common.result.Result;
import com.lgmn.umaservices.basic.dto.LabelRecordDto;
import com.lgmn.umaservices.basic.entity.LabelRecordEntity;
import com.lgmn.userservices.basic.util.UserUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/umaApi")
public class UMaApiController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private LabelRecordApiService labelRecordApiService;

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public com.lgmn.common.result.Result login (@RequestBody LoginDto loginDto) {
        try {
            return loginService.login(loginDto);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/exitLogin")
    public Result exitLogin(@RequestBody ExitLoginDto exitLoginDto) {
        return loginService.exitLogin(exitLoginDto);
    }

    @ApiOperation(value = "上传数据")
    @PostMapping("/postLabelRecord")
    public Result postLabelRecord (@RequestHeader String Authorization, Principal principal, @RequestBody PostLabelRecordDto postLabelRecordDto) {
        LgmnUserInfo lgmnUserInfo = UserUtil.getCurrUser(principal);
        try {
            LabelRecordDto labelRecordDto = new LabelRecordDto();
            labelRecordDto.setLabelNum(postLabelRecordDto.getOrderNo());
            List<LabelRecordEntity> labelRecordEntitys = labelRecordApiService.list(labelRecordDto);
            LabelRecordEntity labelRecord = labelRecordEntitys.get(0);
            Integer status = null;
            // 车间入库
            if ("storage_of_workshop".equals(postLabelRecordDto.getType())) {
                status = 0;
                labelRecord.setInUser(lgmnUserInfo.getId());
                labelRecord.setInTime(new Timestamp(System.currentTimeMillis()));
                // 车间出库
            } else if ("workshop_depot".equals(postLabelRecordDto.getType())) {
                status = 1;
                labelRecord.setOutUser(lgmnUserInfo.getId());
                labelRecord.setOutTime(new Timestamp(System.currentTimeMillis()));
            } else {
                status = 2;
                labelRecord.setOutUser(lgmnUserInfo.getId());
            }
            labelRecord.setStatus(status);
            labelRecordApiService.update(labelRecord);
            return Result.success("上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.serverError(e.getMessage());
        }
    }

    @ApiOperation(value = "获取基本信息")
    @PostMapping("/getPersonInfo")
    public Result getPersonInfo (@RequestHeader String Authorization, Principal principal) {
        LgmnUserInfo lgmnUserInfo = UserUtil.getCurrUser(principal);
        try {
            return Result.success(lgmnUserInfo);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

}