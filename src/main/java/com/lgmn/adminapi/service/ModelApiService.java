package com.lgmn.adminapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lgmn.common.service.LgmnAbstractApiService;
import com.lgmn.umaservices.basic.dto.ModelDto;
import com.lgmn.umaservices.basic.entity.ModelEntity;
import com.lgmn.umaservices.basic.service.ModelService;
import org.springframework.stereotype.Component;


@Component
public class ModelApiService extends LgmnAbstractApiService<ModelEntity, ModelDto, Integer, ModelService> {

    @Reference(version = "${demo.service.version}")
    private ModelService service;

    @Override
    public void initService() {
        setService(service);
    }
}