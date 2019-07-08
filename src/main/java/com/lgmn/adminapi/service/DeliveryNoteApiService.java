package com.lgmn.adminapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lgmn.common.service.LgmnAbstractApiService;
import com.lgmn.umaservices.basic.dto.DeliveryNoteDto;
import com.lgmn.umaservices.basic.entity.DeliveryNoteEntity;
import com.lgmn.umaservices.basic.service.DeliveryNoteService;
import org.springframework.stereotype.Component;


@Component
public class DeliveryNoteApiService extends LgmnAbstractApiService<DeliveryNoteEntity, DeliveryNoteDto, Integer, DeliveryNoteService> {

    @Reference(version = "${demo.service.version}")
    private DeliveryNoteService service;

    @Override
    public void initService() {
        setService(service);
    }
}