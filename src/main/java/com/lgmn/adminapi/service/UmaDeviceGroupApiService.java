package com.lgmn.adminapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lgmn.common.service.LgmnAbstractApiService;
import com.lgmn.umaservices.basic.dto.UmaDeviceGroupDto;
import com.lgmn.umaservices.basic.entity.UmaDeviceGroupEntity;
import com.lgmn.umaservices.basic.service.UmaDeviceGroupService;
import org.springframework.stereotype.Component;


@Component
public class UmaDeviceGroupApiService extends LgmnAbstractApiService<UmaDeviceGroupEntity, UmaDeviceGroupDto, Integer, UmaDeviceGroupService> {

    @Reference(version = "${demo.service.version}")
    private UmaDeviceGroupService service;

    @Override
    public void initService() {
        setService(service);
    }
}