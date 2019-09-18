package com.lgmn.adminapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lgmn.common.service.LgmnAbstractApiService;
import com.lgmn.umaservices.basic.dto.ViewOrderReminderDto;
import com.lgmn.umaservices.basic.entity.ViewOrderReminderEntity;
import com.lgmn.umaservices.basic.service.ViewOrderReminderService;
import org.springframework.stereotype.Component;


@Component
public class ViewOrderReminderApiService extends LgmnAbstractApiService<ViewOrderReminderEntity, ViewOrderReminderDto, Integer, ViewOrderReminderService> {

    @Reference(version = "${demo.service.version}")
    private ViewOrderReminderService service;

    @Override
    public void initService() {
        setService(service);
    }
}