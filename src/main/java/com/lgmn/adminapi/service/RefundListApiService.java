package com.lgmn.adminapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lgmn.common.service.LgmnAbstractApiService;
import com.lgmn.umaservices.basic.dto.RefundListDto;
import com.lgmn.umaservices.basic.entity.RefundListEntity;
import com.lgmn.umaservices.basic.service.RefundListService;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class RefundListApiService extends LgmnAbstractApiService<RefundListEntity, RefundListDto, Integer, RefundListService> {

    @Reference(version = "${demo.service.version}")
    private RefundListService service;

    @Override
    public void initService() {
        setService(service);
    }

    public RefundListEntity getByDto(RefundListDto dto){
        RefundListEntity refundListEntity = null;
        try {
            List<RefundListEntity> list = list(dto);
            if(list.size()>0){
                refundListEntity=list.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return refundListEntity;
        }
    }
}