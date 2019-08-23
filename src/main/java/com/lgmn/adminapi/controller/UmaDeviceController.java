package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.UmaDevice.SaveUmaDeviceDto;
import com.lgmn.adminapi.dto.UmaDevice.UpdateUmaDeviceDto;
import com.lgmn.adminapi.service.UmaDeviceApiService;
import com.lgmn.common.domain.LgmnPage;
import com.lgmn.common.result.Result;
import com.lgmn.common.utils.ObjectTransfer;
import com.lgmn.umaservices.basic.dto.UmaDeviceDto;
import com.lgmn.umaservices.basic.entity.UmaDeviceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/umaDeviceApi")
public class UmaDeviceController {

    @Autowired
    UmaDeviceApiService service;

    @PostMapping("/page")
    public Result page (@RequestBody UmaDeviceDto dto) {
        try {
            LgmnPage<UmaDeviceEntity> page = service.page(dto);
            return Result.success(page);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/update")
    public Result update (@RequestBody UpdateUmaDeviceDto updateDto) {
        try {
            UmaDeviceEntity entity = new UmaDeviceEntity();
            ObjectTransfer.transValue(updateDto, entity);
            service.update(entity);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/add")
    public Result add (@RequestBody SaveUmaDeviceDto saveDto) {
        try {
            UmaDeviceEntity entity = new UmaDeviceEntity();
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
        UmaDeviceEntity entity = service.getById(id);
        return Result.success(entity);
    }
}