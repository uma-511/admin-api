package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.umaConfigValue.UmaConfigValueSaveDto;
import com.lgmn.adminapi.dto.umaConfigValue.UmaConfigValueUpdateDto;
import com.lgmn.adminapi.service.UmaConfigValueApiService;
import com.lgmn.common.domain.LgmnPage;
import com.lgmn.common.result.Result;
import com.lgmn.common.utils.ObjectTransfer;
import com.lgmn.umaservices.basic.dto.UmaConfigValueDto;
import com.lgmn.umaservices.basic.entity.UmaConfigValueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/umaConfigValueApi")
public class UmaConfigValueController {

    @Autowired
    UmaConfigValueApiService service;

    @PostMapping("/page")
    public Result page (@RequestBody UmaConfigValueDto dto) {
        try {
            LgmnPage<UmaConfigValueEntity> page = service.page(dto);
            return Result.success(page);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/update")
    public Result update (@RequestBody UmaConfigValueUpdateDto updateDto) {
        try {
            UmaConfigValueEntity entity = new UmaConfigValueEntity();
            ObjectTransfer.transValue(updateDto, entity);
            service.update(entity);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/add")
    public Result add (@RequestBody UmaConfigValueSaveDto saveDto) {
        try {
            UmaConfigValueEntity entity = new UmaConfigValueEntity();
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
        UmaConfigValueEntity entity = service.getById(id);
        return Result.success(entity);
    }
}