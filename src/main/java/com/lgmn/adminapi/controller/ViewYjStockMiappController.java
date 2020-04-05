package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.service.ViewYjStockMiappApiService;
import com.lgmn.common.domain.LgmnPage;
import com.lgmn.common.result.Result;
import com.lgmn.umaservices.basic.dto.ViewYjStockMiappDto;
import com.lgmn.umaservices.basic.entity.ViewYjStockEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/viewYjStockMiappApi")

public class ViewYjStockMiappController {

    @Autowired
    ViewYjStockMiappApiService service;

    @PostMapping("/page")
    public Result page (@RequestBody ViewYjStockMiappDto dto) {
        try {
            LgmnPage<ViewYjStockEntity> page = service.page(dto);
            return Result.success(page);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }
}