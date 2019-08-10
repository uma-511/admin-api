package com.lgmn.adminapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lgmn.common.service.LgmnAbstractApiService;
import com.lgmn.umaservices.basic.dto.ViewOrderDto;
import com.lgmn.umaservices.basic.entity.ViewOrderEntity;
import com.lgmn.umaservices.basic.service.ViewOrderService;
import org.springframework.stereotype.Component;

@Component
public class ViewOrderApiService extends LgmnAbstractApiService<ViewOrderEntity, ViewOrderDto, Integer, ViewOrderService> {

    @Reference(version = "${demo.service.version}")
    ViewOrderService viewOrderService;

    @Override
    public void initService() {
        setService(viewOrderService);
    }
}
