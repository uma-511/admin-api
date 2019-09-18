package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.viewOrderReminder.ViewOrderReminderQueryDto;
import com.lgmn.adminapi.dto.viewOrderReminder.ViewOrderReminderSaveDto;
import com.lgmn.adminapi.dto.viewOrderReminder.ViewOrderReminderUpdateDto;
import com.lgmn.adminapi.service.ViewOrderReminderApiService;
import com.lgmn.common.domain.LgmnPage;
import com.lgmn.common.result.Result;
import com.lgmn.common.utils.ObjectTransfer;
import com.lgmn.umaservices.basic.dto.ViewOrderReminderDto;
import com.lgmn.umaservices.basic.entity.ViewOrderReminderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/viewOrderReminderApi")
public class ViewOrderReminderController {

    @Autowired
    ViewOrderReminderApiService service;

    @PostMapping("/page")
    public Result page (@RequestBody ViewOrderReminderQueryDto queryDto) {
        try {
            ViewOrderReminderDto dto = new ViewOrderReminderDto();
            ObjectTransfer.transValue(queryDto, dto);
            LgmnPage<ViewOrderReminderEntity> page = service.page(dto);
            return Result.success(page);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/update")
    public Result update (@RequestBody ViewOrderReminderUpdateDto updateDto) {
        try {
            ViewOrderReminderEntity entity = new ViewOrderReminderEntity();
            ObjectTransfer.transValue(updateDto, entity);
            service.update(entity);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/add")
    public Result add (@RequestBody ViewOrderReminderSaveDto saveDto) {
        try {
            ViewOrderReminderEntity entity = new ViewOrderReminderEntity();
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
        ViewOrderReminderEntity entity = service.getById(id);
        return Result.success(entity);
    }
}