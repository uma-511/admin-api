package com.lgmn.adminapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lgmn.common.service.LgmnAbstractApiService;
import com.lgmn.umaservices.basic.dto.ViewLookBoardDto;
import com.lgmn.umaservices.basic.entity.ViewLookBoardEntity;
import com.lgmn.umaservices.basic.service.ViewLookBoardService;
import org.springframework.stereotype.Component;

@Component
public class ViewLookBoardApiService extends LgmnAbstractApiService<ViewLookBoardEntity, ViewLookBoardDto, Integer, ViewLookBoardService> {

    @Reference(version = "${demo.service.version}",timeout = 10000)
    ViewLookBoardService viewLookBoardService;

    @Override
    public void initService() {
        setService(viewLookBoardService);
    }
}
