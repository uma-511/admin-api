package com.lgmn.adminapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lgmn.common.service.LgmnAbstractApiService;
import com.lgmn.umaservices.basic.dto.DeliveryListDto;
import com.lgmn.umaservices.basic.entity.DeliveryListEntity;
import com.lgmn.umaservices.basic.service.DeliveryListService;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class DeliveryListApiService extends LgmnAbstractApiService<DeliveryListEntity, DeliveryListDto, Integer, DeliveryListService> {

    @Reference(version = "${demo.service.version}")
    private DeliveryListService service;

    @Override
    public void initService() {
        setService(service);
    }

    public DeliveryListEntity getByDto(DeliveryListDto dto){
        DeliveryListEntity deliveryListEntity = null;
        try {
            List<DeliveryListEntity> list = list(dto);
            if(list.size()>0){
                deliveryListEntity=list.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return deliveryListEntity;
        }
    }
}