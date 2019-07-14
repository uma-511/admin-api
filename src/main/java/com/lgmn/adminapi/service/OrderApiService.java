package com.lgmn.adminapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lgmn.common.service.LgmnAbstractApiService;
import com.lgmn.umaservices.basic.dto.OrderDto;
import com.lgmn.umaservices.basic.entity.UOrderEntity;
import com.lgmn.umaservices.basic.service.UOrderService;
import org.springframework.stereotype.Component;


@Component
public class OrderApiService extends LgmnAbstractApiService<UOrderEntity, OrderDto, Integer, UOrderService> {

    @Reference(version = "${demo.service.version}")
    private UOrderService service;

    @Override
    public void initService() {
        setService(service);
    }
}