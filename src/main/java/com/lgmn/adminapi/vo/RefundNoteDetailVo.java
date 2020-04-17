package com.lgmn.adminapi.vo;

import com.lgmn.umaservices.basic.entity.CustomerEntity;
import com.lgmn.umaservices.basic.entity.RefundListEntity;
import com.lgmn.umaservices.basic.entity.RefundNoteEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class RefundNoteDetailVo implements Serializable {
    RefundNoteEntity refundNote;
    List<RefundListEntity> refundList;
    CustomerEntity customer;
    String createDate;
    BigDecimal totalPrice;
    String totalPriceChinese;
    int totalQuantity;
    String kong="";
}