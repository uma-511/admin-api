package com.lgmn.adminapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lgmn.common.service.LgmnAbstractApiService;
import com.lgmn.umaservices.basic.dto.LabelFormatDto;
import com.lgmn.umaservices.basic.entity.LabelFormatEntity;
import com.lgmn.umaservices.basic.service.LabelFormatService;
import org.springframework.stereotype.Component;


@Component
public class LabelFormatApiService extends LgmnAbstractApiService<LabelFormatEntity, LabelFormatDto, Integer, LabelFormatService> {

    @Reference(version = "${demo.service.version}")
    private LabelFormatService service;

    @Override
    public void initService() {
        setService(service);
    }
}