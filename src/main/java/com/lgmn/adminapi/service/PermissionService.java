package com.lgmn.adminapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lgmn.common.service.LgmnAbstractApiService;
import com.lgmn.userservices.basic.dto.LgmnPermissionDto;
import com.lgmn.userservices.basic.entity.LgmnPermissionEntity;
import com.lgmn.userservices.basic.service.LgmnPermissionService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PermissionService extends LgmnAbstractApiService<LgmnPermissionEntity, LgmnPermissionDto, String, LgmnPermissionService>{
    @Reference(version = "${demo.service.version}")
    private LgmnPermissionService lgmnPermissionEntityService;

    @Override
    public void initService() {
        setService(lgmnPermissionEntityService);
    }

    public List<LgmnPermissionEntity> querySelctedPermission(String roleId){
       return lgmnPermissionEntityService.querySelctedPermission(roleId);
    }

    public List<LgmnPermissionEntity> queryCanSelctPermission(String roleId){
        return lgmnPermissionEntityService.queryCanSelctPermission(roleId);
    }

    public List<LgmnPermissionEntity> queryUserPermission(String userId){
        return lgmnPermissionEntityService.queryUserPermission(userId);
    }
}