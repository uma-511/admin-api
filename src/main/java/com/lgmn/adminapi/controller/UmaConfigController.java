package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.umaConfig.UmaConfigSaveDto;
import com.lgmn.adminapi.dto.umaConfig.UmaConfigUpdateDto;
import com.lgmn.adminapi.service.UmaConfigApiService;
import com.lgmn.common.domain.LgmnPage;
import com.lgmn.common.result.Result;
import com.lgmn.common.utils.ObjectTransfer;
import com.lgmn.umaservices.basic.dto.UmaConfigDto;
import com.lgmn.umaservices.basic.entity.UmaConfigEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/umaConfigApi")
public class UmaConfigController {

    @Autowired
    UmaConfigApiService service;

    @PostMapping("/page")
    public Result page (@RequestBody UmaConfigDto dto) {
        try {
            LgmnPage<UmaConfigEntity> page = service.page(dto);
            return Result.success(page);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/update")
    public Result update (@RequestBody UmaConfigUpdateDto updateDto) {
        try {
            UmaConfigEntity entity = new UmaConfigEntity();
            ObjectTransfer.transValue(updateDto, entity);
            service.update(entity);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/add")
    public Result add (@RequestBody UmaConfigSaveDto saveDto) {
        try {
            UmaConfigEntity entity = new UmaConfigEntity();
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
        UmaConfigEntity entity = service.getById(id);
        return Result.success(entity);
    }

    @PostMapping("/group")
    public Result detail(){
        Result result = service.configGroup();

        return result;
    }
}