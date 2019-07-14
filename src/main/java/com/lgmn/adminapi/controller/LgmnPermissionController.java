package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.LgmnPermission.SaveLgmnPermissionDto;
import com.lgmn.adminapi.dto.LgmnPermission.UpdateLgmnPermissionDto;
import com.lgmn.adminapi.service.LgmnPermissionApiService;
import com.lgmn.common.domain.LgmnPage;
import com.lgmn.common.result.Result;
import com.lgmn.common.utils.ObjectTransfer;
import com.lgmn.userservices.basic.dto.LgmnPermissionDto;
import com.lgmn.userservices.basic.entity.LgmnPermissionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;


@RestController
@RequestMapping("/lgmnPermissionApi")
public class LgmnPermissionController {

    @Autowired
    LgmnPermissionApiService service;

    @PostMapping("/page")
    public Result page (@RequestBody LgmnPermissionDto dto) {
        try {
            LgmnPage<LgmnPermissionEntity> page = service.page(dto);
            return Result.success(page);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/update")
    public Result update (@RequestBody UpdateLgmnPermissionDto updateDto) {
        try {
            LgmnPermissionEntity entity = service.getById(updateDto.getId());
            ObjectTransfer.transValue(updateDto, entity);
            service.update(entity);
            return Result.success("修改成功");
       } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/add")
    public Result add (@RequestBody SaveLgmnPermissionDto saveDto) {
        try {
            LgmnPermissionEntity entity = new LgmnPermissionEntity();
            ObjectTransfer.transValue(saveDto, entity);
            service.add(entity);
            return Result.success("添加成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/delete")
    public Result delete (@PathParam("id") String id) {
        service.deleteById(id);
        return Result.success("删除成功");
    }

    @PostMapping("/detail")
    public Result detail (@PathParam("id") String id) {
        LgmnPermissionEntity entity = service.getById(id);
        return Result.success(entity);
    }

}