package com.lgmn.adminapi.service;

import java.util.Date;
import com.alibaba.dubbo.config.annotation.Reference;
import com.lgmn.common.service.LgmnAbstractApiService;
import com.lgmn.userservices.basic.dto.LgmnPermissionDto;
import com.lgmn.userservices.basic.entity.LgmnPermissionEntity;
import com.lgmn.userservices.basic.service.LgmnPermissionService;
import org.springframework.stereotype.Component;


@Component
public class LgmnPermissionApiService extends LgmnAbstractApiService<LgmnPermissionEntity, LgmnPermissionDto, String, LgmnPermissionService> {

    @Reference(version = "${demo.service.version}")
    private LgmnPermissionService service;

    @Override
    public void initService() {
        setService(service);
    }
}