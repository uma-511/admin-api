package com.lgmn.adminapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lgmn.common.service.LgmnAbstractApiService;
import com.lgmn.umaservices.basic.dto.LabelRecordDto;
import com.lgmn.umaservices.basic.entity.LabelRecordEntity;
import com.lgmn.umaservices.basic.service.LabelRecordService;
import org.springframework.stereotype.Component;


@Component
public class LabelRecordApiService extends LgmnAbstractApiService<LabelRecordEntity, LabelRecordDto, Integer, LabelRecordService> {

    @Reference(version = "${demo.service.version}")
    private LabelRecordService service;

    @Override
    public void initService() {
        setService(service);
    }
}