package com.lgmn.adminapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lgmn.common.service.LgmnAbstractApiService;
import com.lgmn.umaservices.basic.dto.OrderDto;
import com.lgmn.umaservices.basic.entity.OrderEntity;
import com.lgmn.umaservices.basic.service.OrderService;
import org.springframework.stereotype.Component;


@Component
public class OrderApiService extends LgmnAbstractApiService<OrderEntity, OrderDto, Integer, OrderService> {

    @Reference(version = "${demo.service.version}")
    private OrderService service;

    @Override
    public void initService() {
        setService(service);
    }
}