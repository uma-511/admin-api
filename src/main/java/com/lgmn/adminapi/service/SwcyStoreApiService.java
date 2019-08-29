package com.lgmn.adminapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lgmn.common.service.LgmnAbstractApiService;
import com.lgmn.swcy.basic.dto.SwcyStoreDto;
import com.lgmn.swcy.basic.entity.SwcyStoreEntity;
import com.lgmn.swcy.basic.service.SwcyStoreService;
import org.springframework.stereotype.Component;


@Component
public class SwcyStoreApiService extends LgmnAbstractApiService<SwcyStoreEntity, SwcyStoreDto, Integer, SwcyStoreService> {

    @Reference(version = "${demo.service.version}")
    private SwcyStoreService service;

    @Override
    public void initService() {
        setService(service);
    }
}