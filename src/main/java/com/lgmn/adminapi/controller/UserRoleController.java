package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.userRole.AddUserRoleDto;
import com.lgmn.adminapi.dto.userRole.DeleteUserRoleDto;
import com.lgmn.adminapi.dto.userRole.UserRoleListDto;
import com.lgmn.adminapi.service.RoleService;
import com.lgmn.adminapi.service.UserRoleService;
import com.lgmn.common.result.Result;
import com.lgmn.common.result.ResultEnum;
import com.lgmn.userservices.basic.dto.LgmnRolePermissionDto;
import com.lgmn.userservices.basic.dto.LgmnUserRoleDto;
import com.lgmn.userservices.basic.entity.LgmnRoleEntity;
import com.lgmn.userservices.basic.entity.LgmnUserRoleEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(description = "用户角色管理")
@RestController
@RequestMapping("/userrole")
public class UserRoleController {

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    RoleService roleService;

    @ApiOperation(value = "获取可选角色")
    @PostMapping("/userrolelist")
    public Result userRoleList(@RequestBody UserRoleListDto listDto){
        Result result=Result.success("获取用户角色成功");
        try {
            List<LgmnRoleEntity> list=roleService.querySelctedRole(listDto.getUserId());
            result.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            result= Result.error(ResultEnum.SERVER_ERROR);
        }finally {
            return result;
        }
    }

    @ApiOperation(value = "获取已选角色")
    @PostMapping("/rolelist")
    public Result roleList(@RequestBody UserRoleListDto listDto){
        Result result = Result.success("获取角色数据成功");
        LgmnRolePermissionDto dto = new LgmnRolePermissionDto();
        try {
            List<LgmnRoleEntity> list=roleService.queryCanSelctRole(listDto.getUserId());
            result.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            result=Result.error(ResultEnum.SERVER_ERROR);
        }finally {
            return result;
        }
    }

    @ApiOperation(value = "添加用户角色")
    @PostMapping("/adduserrole")
    public Result addUserRole(@RequestBody AddUserRoleDto addDto){
        Result result=Result.success("添加用户角色成功");
        LgmnRolePermissionDto dto = new LgmnRolePermissionDto();
        String userId=addDto.getUserId();
        List<String> roleIds=addDto.getRoleIds();

        try {
            List<LgmnUserRoleEntity> entities=new ArrayList<>();
            if(roleIds.size()>0){
                for (String roleId:roleIds ) {
                    LgmnUserRoleEntity entity = new LgmnUserRoleEntity();
                    entity.setUserId(userId);
                    entity.setRoleId(roleId);
                    entities.add(entity);
                }
                userRoleService.addAll(entities);
            }else{
                result.setMessage("缺少角色值");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result= Result.error(ResultEnum.SERVER_ERROR);
        }finally {
            return result;
        }
    }

    @ApiOperation(value = "删除用户角色")
    @PostMapping("/deleteuserrole")
    public Result deleteUserRole(@RequestBody DeleteUserRoleDto deleteDto){
        Result result=Result.success("删除用户角色成功");
        LgmnUserRoleDto dto = new LgmnUserRoleDto();
        try {
            dto.setUserId(deleteDto.getUserId());
            dto.setRoleId(deleteDto.getRoleId());

            List<LgmnUserRoleEntity> userRoleEntityList=userRoleService.list(dto);
            userRoleService.deleteByEntitys(userRoleEntityList);
        } catch (Exception e) {
            e.printStackTrace();
            result= Result.error(ResultEnum.SERVER_ERROR);
        }finally {
            return result;
        }
    }
}