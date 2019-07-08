package com.lgmn.adminapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lgmn.common.service.LgmnAbstractApiService;
import com.lgmn.umaservices.basic.dto.CustomerContactDto;
import com.lgmn.umaservices.basic.entity.CustomerContactEntity;
import com.lgmn.umaservices.basic.service.CustomerContactService;
import org.springframework.stereotype.Component;


@Component
public class CustomerContactApiService extends LgmnAbstractApiService<CustomerContactEntity, CustomerContactDto, Integer, CustomerContactService> {

    @Reference(version = "${demo.service.version}")
    private CustomerContactService service;

    @Override
    public void initService() {
        setService(service);
    }
}