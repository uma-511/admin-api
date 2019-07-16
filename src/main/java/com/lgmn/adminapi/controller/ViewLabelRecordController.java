package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.service.ViewLabelRecordApiService;
import com.lgmn.common.domain.LgmnPage;
import com.lgmn.common.result.Result;
import com.lgmn.umaservices.basic.dto.ViewLabelRecordDto;
import com.lgmn.umaservices.basic.entity.ViewLabelRecordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/viewLabelRecordApi")
public class ViewLabelRecordController {

    @Autowired
    ViewLabelRecordApiService service;

    @PostMapping("/page")
    public Result page (@RequestBody ViewLabelRecordDto dto) {
        try {
            LgmnPage<ViewLabelRecordEntity> page = service.page(dto);
            return Result.success(page);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/detail/{id}")
    public Result detail (@PathVariable("id") Integer id) {
        ViewLabelRecordEntity entity = service.getById(id);
        return Result.success(entity);
    }
}