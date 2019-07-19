package com.lgmn.adminapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lgmn.common.service.LgmnAbstractApiService;
import com.lgmn.userservices.basic.dto.LgmnRolePermissionDto;
import com.lgmn.userservices.basic.entity.LgmnRolePermissionEntity;
import com.lgmn.userservices.basic.service.LgmnRolePermissionService;
import org.springframework.stereotype.Component;

@Component
public class RolePermissionService extends LgmnAbstractApiService<LgmnRolePermissionEntity, LgmnRolePermissionDto, Integer, LgmnRolePermissionService> {
    @Reference(version = "${demo.service.version}")
    private LgmnRolePermissionService rolePermissionEntityService;

    @Override
    public void initService() {
        setService(rolePermissionEntityService);
    }
}