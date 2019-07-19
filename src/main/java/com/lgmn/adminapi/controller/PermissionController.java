package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.permission.AddPermissionDto;
import com.lgmn.adminapi.dto.permission.DeletePermissionDto;
import com.lgmn.adminapi.dto.permission.EditPermissionDto;
import com.lgmn.adminapi.dto.permission.PermissionPageDto;
import com.lgmn.adminapi.service.PermissionService;
import com.lgmn.common.domain.LgmnPage;
import com.lgmn.common.result.Result;
import com.lgmn.common.result.ResultEnum;
import com.lgmn.common.utils.ObjectTransfer;
import com.lgmn.userservices.basic.dto.LgmnPermissionDto;
import com.lgmn.userservices.basic.entity.LgmnPermissionEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "权限管理")
@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    PermissionService permissionService;

    @ApiOperation(value = "获取权限")
    @PostMapping("/permissionpage")
    public Result permissionPage(@RequestBody PermissionPageDto pageDto){
        Result result = Result.success("获取权限数据成功");
        LgmnPermissionDto dto=new LgmnPermissionDto();
        try {
            ObjectTransfer.transValue(pageDto,dto);
            LgmnPage<LgmnPermissionEntity> page=permissionService.page(dto);
            result.setData(page);
        } catch (Exception e) {
            e.printStackTrace();
            result=Result.error(ResultEnum.SERVER_ERROR);
        }finally {
            return result;
        }
    }

    @ApiOperation(value = "添加权限")
    @PostMapping("/addpermission")
    public Result addPermission(@RequestBody AddPermissionDto addPermissionDto){
        Result result = Result.success("添加权限数据成功");
        LgmnPermissionEntity entity=new LgmnPermissionEntity();
        try {
            ObjectTransfer.transValue(addPermissionDto,entity);
            LgmnPermissionEntity res=permissionService.add(entity);
        } catch (Exception e) {
            e.printStackTrace();
            result=Result.error(ResultEnum.SERVER_ERROR);
        }finally {
            return result;
        }
    }

    @ApiOperation(value = "修改权限")
    @PostMapping("/updatepermission")
    public Result updatePermission(@RequestBody EditPermissionDto editPermissionDto){
        Result result = Result.success("修改权限数据成功");
        LgmnPermissionEntity entity=new LgmnPermissionEntity();
        try {
            ObjectTransfer.transValue(editPermissionDto,entity);
            LgmnPermissionEntity res=permissionService.update(entity);
        } catch (Exception e) {
            e.printStackTrace();
            result=Result.error(ResultEnum.SERVER_ERROR);
        }finally {
            return result;
        }
    }

    @ApiOperation(value = "删除权限")
    @PostMapping("/deletepermission")
    public Result deletePermission(@RequestBody DeletePermissionDto deletePermissionDto){
        Result result = Result.success("修改权限数据成功");
        try {
            permissionService.deleteById(deletePermissionDto.getId());
        } catch (Exception e) {
            e.printStackTrace();
            result=Result.error(ResultEnum.SERVER_ERROR);
        }finally {
            return result;
        }
    }
}