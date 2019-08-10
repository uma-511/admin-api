package com.lgmn.adminapi.vo;

import com.lgmn.umaservices.basic.entity.CustomerEntity;
import com.lgmn.umaservices.basic.entity.DeliveryListEntity;
import com.lgmn.umaservices.basic.entity.DeliveryNoteEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class DeliveryNoteDetailVo implements Serializable {
    DeliveryNoteEntity deliveryNote;
    List<DeliveryListEntity> deliveryList;
    CustomerEntity customer;
    String createDate;
    BigDecimal totalPrice;
    String totalPriceChinese;
    int totalQuantity;
    String kong="";
}