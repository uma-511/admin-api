package com.lgmn.adminapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lgmn.common.service.LgmnAbstractApiService;
import com.lgmn.umaservices.basic.dto.RefundNoteDto;
import com.lgmn.umaservices.basic.entity.RefundNoteEntity;
import com.lgmn.umaservices.basic.service.RefundNoteService;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class RefundNoteApiService extends LgmnAbstractApiService<RefundNoteEntity, RefundNoteDto, Integer, RefundNoteService> {

    @Reference(version = "${demo.service.version}")
    private RefundNoteService service;

    @Override
    public void initService() {
        setService(service);
    }

    public RefundNoteEntity getRefundByNum(String num){
        RefundNoteEntity refundNoteEntity=null;
        RefundNoteDto dto = new RefundNoteDto();
        dto.setRefundNum(num);
        try {
            List<RefundNoteEntity> list = list(dto);
            if(list.size()>0){
                refundNoteEntity = list.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return refundNoteEntity;
        }
    }
}