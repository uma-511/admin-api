package com.lgmn.adminapi.dto.refundList;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RefundListUpdateDto {
    private int id;
    private Integer refundId;
    private Integer clientId;
    private String clientName;
    private String number;
    private String name;
    private String specs;
    private String width;
    private String color;
    private String requirement;
    private Integer scanQuantity;
    private Integer residualQuantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private Integer quantity;
    private Integer packageQuantity;
}