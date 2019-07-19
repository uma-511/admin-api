package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.rolePermission.AddRolePermissionDto;
import com.lgmn.adminapi.dto.rolePermission.DeleteRolePermissionDto;
import com.lgmn.adminapi.dto.rolePermission.RolePermissionListDto;
import com.lgmn.adminapi.service.PermissionService;
import com.lgmn.adminapi.service.RolePermissionService;
import com.lgmn.common.result.Result;
import com.lgmn.common.result.ResultEnum;
import com.lgmn.userservices.basic.dto.LgmnRolePermissionDto;
import com.lgmn.userservices.basic.entity.LgmnPermissionEntity;
import com.lgmn.userservices.basic.entity.LgmnRolePermissionEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(description = "角色权限管理")
@RestController
@RequestMapping("/rolepermission")
public class RolePermissionController {

    @Autowired
    RolePermissionService rolePermissionService;

    @Autowired
    PermissionService permissionService;

    @ApiOperation(value = "获取可选权限")
    @PostMapping("/rolepermissionlist")
    public Result rolePermissionList(@RequestBody RolePermissionListDto listDto){
        Result result=Result.success("获取角色权限成功");
        LgmnRolePermissionDto dto = new LgmnRolePermissionDto();
        try {
            List<LgmnPermissionEntity> list=permissionService.querySelctedPermission(listDto.getRoleId());
            result.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            result= Result.error(ResultEnum.SERVER_ERROR);
        }finally {
            return result;
        }
    }

    @ApiOperation(value = "获取已选权限")
    @PostMapping("/permissionlist")
    public Result permissionList(@RequestBody RolePermissionListDto listDto){
        Result result = Result.success("获取权限数据成功");
        LgmnRolePermissionDto dto = new LgmnRolePermissionDto();
        try {
            List<LgmnPermissionEntity> list=permissionService.queryCanSelctPermission(listDto.getRoleId());
            result.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            result=Result.error(ResultEnum.SERVER_ERROR);
        }finally {
            return result;
        }
    }

    @ApiOperation(value = "添加角色权限")
    @PostMapping("/addrolepermission")
    public Result addRolePermission(@RequestBody AddRolePermissionDto addDto){
        Result result=Result.success("添加角色权限成功");
        LgmnRolePermissionDto dto = new LgmnRolePermissionDto();
        String roleId=addDto.getRoleId();
        List<String> permissionIds=addDto.getPermissionIds();

        try {
            List<LgmnRolePermissionEntity> entities=new ArrayList<>();
            if(permissionIds.size()>0){
                for (String permissionId:permissionIds ) {
                    LgmnRolePermissionEntity entity = new LgmnRolePermissionEntity();
                    entity.setRoleId(roleId);
                    entity.setPermissionId(permissionId);
                    entities.add(entity);
                }
                rolePermissionService.addAll(entities);
            }else{
                result.setMessage("缺少权限值");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result= Result.error(ResultEnum.SERVER_ERROR);
        }finally {
            return result;
        }
    }

    @ApiOperation(value = "删除角色权限")
    @PostMapping("/deleterolepermission")
    public Result deleteRolePermission(@RequestBody DeleteRolePermissionDto deleteDto){
        Result result=Result.success("删除角色权限成功");
        LgmnRolePermissionDto dto = new LgmnRolePermissionDto();
        try {

            List<String> roleIds=new ArrayList<>();
            roleIds.add(deleteDto.getRoleId());
            dto.setRoleId(roleIds);
            dto.setPermissionId(deleteDto.getPermissionId());

            List<LgmnRolePermissionEntity> rolePermissionEntities=rolePermissionService.list(dto);
            rolePermissionService.deleteByEntitys(rolePermissionEntities);
        } catch (Exception e) {
            e.printStackTrace();
            result= Result.error(ResultEnum.SERVER_ERROR);
        }finally {
            return result;
        }
    }
}