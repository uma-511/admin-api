package com.lgmn.adminapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lgmn.common.service.LgmnAbstractApiService;
import com.lgmn.userservices.basic.dto.LgmnRoleDto;
import com.lgmn.userservices.basic.entity.LgmnRoleEntity;
import com.lgmn.userservices.basic.service.LgmnRoleService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleService extends LgmnAbstractApiService<LgmnRoleEntity, LgmnRoleDto, String, LgmnRoleService> {
    @Reference(version = "${demo.service.version}")
    private LgmnRoleService lgmnRoleEntityService;

    @Override
    public void initService() {
        setService(lgmnRoleEntityService);
    }

    public List<LgmnRoleEntity> querySelctedRole(String userId) {
        return lgmnRoleEntityService.querySelctedRole(userId);
    }

    public List<LgmnRoleEntity> queryCanSelctRole(String userId) {
        return lgmnRoleEntityService.queryCanSelctRole(userId);
    }
}