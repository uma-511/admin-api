package com.lgmn.adminapi.dto.yjOrder;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class YjOrderSaveDto {
    //订单编号    
    private String orderNum;
    //产品编号    
    private String number;
    //名称(uma_config)    
    private String name;
    //规格(uma_config)    
    private String specs;
    //宽度(uma_config)    
    private String width;
    //颜色(uma_config)    
    private String color;
    //标签格式    
    private Integer labelId;
    //便签格式名称    
    private String labelName;
    //工艺要求    
    private String requirement;
    //计划数量    
    private Integer plannedQuantity;
    //每包饼数    
    private Integer perPackQuantity;
    //备注    
    private String remark;
    //交货日期    
    private Timestamp deliveryDate;
    //客户id    
    private Integer clientId;
    //客户名    
    private String clientName;
    //楼层(uma_config)    
    private String floor;

}