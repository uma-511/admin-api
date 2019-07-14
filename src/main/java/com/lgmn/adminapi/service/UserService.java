package com.lgmn.adminapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lgmn.common.service.LgmnAbstractApiService;
import com.lgmn.userservices.basic.dto.LgmnUserDto;
import com.lgmn.userservices.basic.entity.LgmnUserEntity;
import com.lgmn.userservices.basic.service.LgmnUserService;
import org.springframework.stereotype.Component;

@Component
public class UserService extends LgmnAbstractApiService<LgmnUserEntity, LgmnUserDto, String, LgmnUserService> {

    @Reference(version = "${demo.service.version}")
    LgmnUserService lgmnUserService;

    @Override
    public void initService() {
        setService(lgmnUserService);
    }
}
