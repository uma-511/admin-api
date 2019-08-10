package com.lgmn.adminapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lgmn.common.service.LgmnAbstractApiService;
import com.lgmn.umaservices.basic.dto.YjOrderDto;
import com.lgmn.umaservices.basic.entity.YjOrderEntity;
import com.lgmn.umaservices.basic.service.YjOrderService;
import org.springframework.stereotype.Component;


@Component
public class YjOrderApiService extends LgmnAbstractApiService<YjOrderEntity, YjOrderDto, Integer, YjOrderService> {

    @Reference(version = "${demo.service.version}")
    private YjOrderService service;

    @Override
    public void initService() {
        setService(service);
    }
}