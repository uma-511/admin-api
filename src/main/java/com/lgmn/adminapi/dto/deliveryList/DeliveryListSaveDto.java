package com.lgmn.adminapi.dto.deliveryList;

import lombok.Data;

@Data
public class DeliveryListSaveDto {
        
    private Integer deliveryId;
        
    private Integer clientId;
        
    private String clientName;
    //编号    
    private String number;
    //名称    
    private String name;
    //规格    
    private String specs;
    //宽度    
    private String width;
    //颜色    
    private String color;
    //工艺要求    
    private String requirement;
    //扫描数量    
    private Integer scanQuantity;
    //剩余数量    
    private Integer residualQuantity;
        
    private Double unitPrice;
        
    private Double totalPrice;

}