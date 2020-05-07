package com.lgmn.adminapi.vo;

import com.lgmn.common.domain.LgmnVo;
import com.lgmn.umaservices.basic.entity.DeliveryNoteEntity;
import lombok.Data;

@Data
public class HM_DeliveryNoteVo extends LgmnVo<DeliveryNoteEntity,HM_DeliveryNoteVo> {
    private String deliveryNum;
    private String customer;
    private Integer id;
}