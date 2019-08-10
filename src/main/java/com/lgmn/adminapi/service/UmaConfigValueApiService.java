package com.lgmn.adminapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lgmn.common.service.LgmnAbstractApiService;
import com.lgmn.umaservices.basic.dto.UmaConfigValueDto;
import com.lgmn.umaservices.basic.entity.UmaConfigValueEntity;
import com.lgmn.umaservices.basic.service.UmaConfigValueService;
import org.springframework.stereotype.Component;


@Component
public class UmaConfigValueApiService extends LgmnAbstractApiService<UmaConfigValueEntity, UmaConfigValueDto, Integer, UmaConfigValueService> {

    @Reference(version = "${demo.service.version}")
    private UmaConfigValueService service;

    @Override
    public void initService() {
        setService(service);
    }
}