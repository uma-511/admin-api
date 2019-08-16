package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.ViewYjStock.UpdateViewYjStockDto;
import com.lgmn.adminapi.dto.yjProduct.YjProductSaveDto;
import com.lgmn.adminapi.dto.yjProduct.YjProductSelectDto;
import com.lgmn.adminapi.dto.yjProduct.YjProductUpdateDto;
import com.lgmn.adminapi.service.ViewYjStockApiService;
import com.lgmn.adminapi.service.YjProductApiService;
import com.lgmn.adminapi.vo.YJProductVo;
import com.lgmn.common.domain.LgmnPage;
import com.lgmn.common.domain.LgmnUserInfo;
import com.lgmn.common.result.Result;
import com.lgmn.common.utils.ObjectTransfer;
import com.lgmn.umaservices.basic.dto.ViewYjStockDto;
import com.lgmn.umaservices.basic.dto.YjProductDto;
import com.lgmn.umaservices.basic.entity.ViewYjStockEntity;
import com.lgmn.umaservices.basic.entity.YjProductEntity;
import com.lgmn.userservices.basic.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;


@RestController
@RequestMapping("/viewYjStockApi")
public class ViewYjStockController {

    @Autowired
    ViewYjStockApiService service;

    @PostMapping("/page")
    public Result page (@RequestBody ViewYjStockDto dto) {
        try {
            LgmnPage<ViewYjStockEntity> page = service.page(dto);
            return Result.success(page);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }
}