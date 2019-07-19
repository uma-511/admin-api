package com.lgmn.adminapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lgmn.common.service.LgmnAbstractApiService;
import com.lgmn.userservices.basic.dto.LgmnUserRoleDto;
import com.lgmn.userservices.basic.entity.LgmnUserRoleEntity;
import com.lgmn.userservices.basic.service.LgmnUserRoleService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRoleService extends LgmnAbstractApiService<LgmnUserRoleEntity, LgmnUserRoleDto, Integer, LgmnUserRoleService> {
    @Reference(version = "${demo.service.version}")
    private LgmnUserRoleService lgmnUserRoleEntityService;

    @Override
    public void initService() {
        setService(lgmnUserRoleEntityService);
    }

    public List<LgmnUserRoleEntity> getUserRoleListByUserId (String userId) throws Exception {
        LgmnUserRoleDto lgmnUserRoleDto = new LgmnUserRoleDto();
        lgmnUserRoleDto.setUserId(userId);
        return lgmnUserRoleEntityService.getListByDto(lgmnUserRoleDto);
    }
}