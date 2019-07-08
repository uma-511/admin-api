package com.lgmn.adminapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lgmn.common.service.LgmnAbstractApiService;
import com.lgmn.umaservices.basic.dto.CustomerDto;
import com.lgmn.umaservices.basic.entity.CustomerEntity;
import com.lgmn.umaservices.basic.service.CustomerService;
import org.springframework.stereotype.Component;


@Component
public class CustomerApiService extends LgmnAbstractApiService<CustomerEntity, CustomerDto, Integer, CustomerService> {

    @Reference(version = "${demo.service.version}")
    private CustomerService service;

    @Override
    public void initService() {
        setService(service);
    }
}