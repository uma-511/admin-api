package com.lgmn.adminapi.dto.yjOrder;

import com.lgmn.common.domain.LgmnDto;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class YjOrderSearchDto extends LgmnDto {
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
    //便签格式名称
    private String labelName;
    //交货日期
    private Timestamp deliveryDate;
    //客户名
    private String clientName;
    //楼层(uma_config)
    private String floor;
    private Integer delFlag;
}