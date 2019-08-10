package com.lgmn.adminapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lgmn.common.service.LgmnAbstractApiService;
import com.lgmn.umaservices.basic.dto.YjProductDto;
import com.lgmn.umaservices.basic.entity.YjProductEntity;
import com.lgmn.umaservices.basic.service.YjProductService;
import org.springframework.stereotype.Component;


@Component
public class YjProductApiService extends LgmnAbstractApiService<YjProductEntity, YjProductDto, Integer, YjProductService> {

    @Reference(version = "${demo.service.version}")
    private YjProductService service;

    @Override
    public void initService() {
        setService(service);
    }
}