package com.lgmn.adminapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lgmn.common.service.LgmnAbstractApiService;
import com.lgmn.umaservices.basic.dto.ViewYjStockMiappDto;
import com.lgmn.umaservices.basic.entity.ViewYjStockEntity;
import com.lgmn.umaservices.basic.service.ViewYjStockMiappService;
import org.springframework.stereotype.Component;


@Component
public class ViewYjStockMiappApiService extends LgmnAbstractApiService<ViewYjStockEntity, ViewYjStockMiappDto, Integer, ViewYjStockMiappService> {

    @Reference(version = "${demo.service.version}")
    private ViewYjStockMiappService service;

    @Override
    public void initService() {
        setService(service);
    }
}