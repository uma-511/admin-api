package com.lgmn.adminapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lgmn.common.service.LgmnAbstractApiService;
import com.lgmn.umaservices.basic.dto.ViewLabelRecordDto;
import com.lgmn.umaservices.basic.entity.ViewLabelRecordEntity;
import com.lgmn.umaservices.basic.service.ViewLabelRecordService;
import org.springframework.stereotype.Component;

@Component
public class ViewLabelRecordApiService extends LgmnAbstractApiService<ViewLabelRecordEntity, ViewLabelRecordDto, Integer, ViewLabelRecordService> {

    @Reference(version = "${demo.service.version}")
    ViewLabelRecordService viewLabelRecordService;

    @Override
    public void initService() {
        setService(viewLabelRecordService);
    }
}
