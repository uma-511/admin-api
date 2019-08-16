package com.lgmn.adminapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lgmn.common.service.LgmnAbstractApiService;
import com.lgmn.umaservices.basic.dto.ViewYjStockDto;
import com.lgmn.umaservices.basic.entity.ViewYjStockEntity;
import com.lgmn.umaservices.basic.service.ViewYjStockService;
import org.springframework.stereotype.Component;


@Component
public class ViewYjStockApiService extends LgmnAbstractApiService<ViewYjStockEntity, ViewYjStockDto, Integer, ViewYjStockService> {

    @Reference(version = "${demo.service.version}")
    private ViewYjStockService service;

    @Override
    public void initService() {
        setService(service);
    }
}