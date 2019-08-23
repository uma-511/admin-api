package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.UmaDeviceGroup.SaveUmaDeviceGroupDto;
import com.lgmn.adminapi.dto.UmaDeviceGroup.UpdateUmaDeviceGroupDto;
import com.lgmn.adminapi.service.UmaDeviceGroupApiService;
import com.lgmn.common.domain.LgmnPage;
import com.lgmn.common.result.Result;
import com.lgmn.common.utils.ObjectTransfer;
import com.lgmn.umaservices.basic.dto.UmaDeviceGroupDto;
import com.lgmn.umaservices.basic.entity.UmaDeviceGroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/umaDeviceGroupApi")
public class UmaDeviceGroupController {

    @Autowired
    UmaDeviceGroupApiService service;

    @PostMapping("/page")
    public Result page (@RequestBody UmaDeviceGroupDto dto) {
        try {
            LgmnPage<UmaDeviceGroupEntity> page = service.page(dto);
            return Result.success(page);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/update")
    public Result update (@RequestBody UpdateUmaDeviceGroupDto updateDto) {
        try {
            UmaDeviceGroupEntity entity = new UmaDeviceGroupEntity();
            ObjectTransfer.transValue(updateDto, entity);
            service.update(entity);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/add")
    public Result add (@RequestBody SaveUmaDeviceGroupDto saveDto) {
        try {
            UmaDeviceGroupEntity entity = new UmaDeviceGroupEntity();
            ObjectTransfer.transValue(saveDto, entity);
            service.add(entity);
            return Result.success("添加成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/delete/{id}")
    public Result delete (@PathVariable("id") Integer id) {
        service.deleteById(id);
        return Result.success("删除成功");
    }

    @PostMapping("/detail/{id}")
    public Result detail (@PathVariable("id") Integer id) {
        UmaDeviceGroupEntity entity = service.getById(id);
        return Result.success(entity);
    }
}