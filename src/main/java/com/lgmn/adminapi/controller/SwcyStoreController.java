package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.SwcyStore.SwcyStoreQueryDto;
import com.lgmn.adminapi.dto.SwcyStore.SwcyStoreSaveDto;
import com.lgmn.adminapi.dto.SwcyStore.SwcyStoreUpdateDto;
import com.lgmn.adminapi.service.SwcyStoreApiService;
import com.lgmn.common.domain.LgmnPage;
import com.lgmn.common.result.Result;
import com.lgmn.common.utils.ObjectTransfer;
import com.lgmn.swcy.basic.dto.SwcyStoreDto;
import com.lgmn.swcy.basic.entity.SwcyStoreEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/swcyStoreApi")
public class SwcyStoreController {

    @Autowired
    SwcyStoreApiService service;

    @PostMapping("/page")
    public Result page (@RequestBody SwcyStoreQueryDto queryDto) {
        try {
            SwcyStoreDto dto = new SwcyStoreDto();
            ObjectTransfer.transValue(queryDto, dto);
            LgmnPage<SwcyStoreEntity> page = service.page(dto);
            return Result.success(page);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/update")
    public Result update (@RequestBody SwcyStoreUpdateDto updateDto) {
        try {
            SwcyStoreEntity entity = new SwcyStoreEntity();
            ObjectTransfer.transValue(updateDto, entity);
            service.update(entity);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/add")
    public Result add (@RequestBody SwcyStoreSaveDto saveDto) {
        try {
            SwcyStoreEntity entity = new SwcyStoreEntity();
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
        SwcyStoreEntity entity = service.getById(id);
        return Result.success(entity);
    }
}