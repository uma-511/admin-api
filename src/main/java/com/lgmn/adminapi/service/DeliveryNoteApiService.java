package com.lgmn.adminapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lgmn.common.service.LgmnAbstractApiService;
import com.lgmn.umaservices.basic.dto.DeliveryNoteDto;
import com.lgmn.umaservices.basic.entity.DeliveryNoteEntity;
import com.lgmn.umaservices.basic.service.DeliveryNoteService;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class DeliveryNoteApiService extends LgmnAbstractApiService<DeliveryNoteEntity, DeliveryNoteDto, Integer, DeliveryNoteService> {

    @Reference(version = "${demo.service.version}")
    private DeliveryNoteService service;

    @Override
    public void initService() {
        setService(service);
    }

    public DeliveryNoteEntity getDeliverByNum(String num){
        DeliveryNoteEntity deliveryNoteEntity=null;
        DeliveryNoteDto dto = new DeliveryNoteDto();
        dto.setDeliveryNum(num);
        try {
            List<DeliveryNoteEntity> list = list(dto);
            if(list.size()>0){
                deliveryNoteEntity = list.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return deliveryNoteEntity;
        }
    }
}