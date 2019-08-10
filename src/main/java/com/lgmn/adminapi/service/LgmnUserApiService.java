package com.lgmn.adminapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lgmn.common.service.LgmnAbstractApiService;
import com.lgmn.userservices.basic.dto.LgmnUserDto;
import com.lgmn.userservices.basic.entity.LgmnUserEntity;
import com.lgmn.userservices.basic.service.LgmnUserService;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class LgmnUserApiService extends LgmnAbstractApiService<LgmnUserEntity, LgmnUserDto, String, LgmnUserService> {

    @Reference(version = "${demo.service.version}")
    private LgmnUserService service;

    @Override
    public void initService() {
        setService(service);
    }

    public List<LgmnUserEntity> getUserByAccount(String account) throws Exception {
        LgmnUserDto lgmnUserDto = new LgmnUserDto();
        lgmnUserDto.setAccount(account);
        return service.getListByDto(lgmnUserDto);
    }

    public List<LgmnUserEntity> getDataByNikeName(String nikeName) throws Exception {
        LgmnUserDto lgmnUserDto = new LgmnUserDto();
        lgmnUserDto.setNikeName(nikeName);
        return service.getListByDto(lgmnUserDto);
    }
}