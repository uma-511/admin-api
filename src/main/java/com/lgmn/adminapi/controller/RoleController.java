package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.role.AddRoleDto;
import com.lgmn.adminapi.dto.role.DeleteRoleDto;
import com.lgmn.adminapi.dto.role.EditRoleDto;
import com.lgmn.adminapi.dto.role.RolePageDto;
import com.lgmn.adminapi.service.RoleService;
import com.lgmn.adminapi.service.UserRoleService;
import com.lgmn.common.domain.LgmnPage;
import com.lgmn.common.result.Result;
import com.lgmn.common.result.ResultEnum;
import com.lgmn.common.utils.ObjectTransfer;
import com.lgmn.userservices.basic.dto.LgmnRoleDto;
import com.lgmn.userservices.basic.entity.LgmnRoleEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@Api(description = "角色管理")
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleService roleService;
    @Autowired
    UserRoleService userRoleService;

    @ApiOperation(value = "获取角色")
    @PostMapping("/rolepage")
    public Result rolepage(@RequestBody RolePageDto pageDto){
        Result result = Result.success("获取角色数据成功");
        LgmnRoleDto dto=new LgmnRoleDto();
        try {
            ObjectTransfer.transValue(pageDto,dto);
            LgmnPage<LgmnRoleEntity> page=roleService.page(dto);
            result.setData(page);
        } catch (Exception e) {
            e.printStackTrace();
            result=Result.error(ResultEnum.SERVER_ERROR);
        }finally {
            return result;
        }
    }

    @ApiOperation(value = "添加角色")
    @PostMapping("/addrole")
    public Result addrole(@RequestBody AddRoleDto addRoleDto){
        Result result = Result.success("添加角色数据成功");
        LgmnRoleEntity entity=new LgmnRoleEntity();
        try {
            ObjectTransfer.transValue(addRoleDto,entity);
//            entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
            LgmnRoleEntity res=roleService.add(entity);
        } catch (Exception e) {
            e.printStackTrace();
            result=Result.error(ResultEnum.SERVER_ERROR);
        }finally {
            return result;
        }
    }

    @ApiOperation(value = "修改角色")
    @PostMapping("/updaterole")
    public Result updaterole(@RequestBody EditRoleDto editRoleDto){
        Result result = Result.success("修改角色数据成功");
        LgmnRoleEntity entity=new LgmnRoleEntity();
        try {
            ObjectTransfer.transValue(editRoleDto,entity);
            LgmnRoleEntity res=roleService.update(entity);
        } catch (Exception e) {
            e.printStackTrace();
            result=Result.error(ResultEnum.SERVER_ERROR);
        }finally {
            return result;
        }
    }

    @ApiOperation(value = "删除角色")
    @PostMapping("/deleterole")
    public Result deleterole(@RequestBody DeleteRoleDto deleteRoleDtoRoleDto){
        Result result = Result.success("删除角色数据成功");
        try {
            roleService.deleteById(deleteRoleDtoRoleDto.getId());
        } catch (Exception e) {
            e.printStackTrace();
            result=Result.error(ResultEnum.SERVER_ERROR);
        }finally {
            return result;
        }
    }
}