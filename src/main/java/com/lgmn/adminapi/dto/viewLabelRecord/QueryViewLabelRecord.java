package com.lgmn.adminapi.dto.viewLabelRecord;

import com.lgmn.common.domain.LgmnDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class QueryViewLabelRecord extends LgmnDto {
    private String labelNum;
    private String orderNo;
    private List<String> prodTimes;
    private Integer status;
    private String modelName;
    private BigDecimal width;
    private String specs;
    private String color;
    private List<String> deliveryDates;
    private String customerName;
    private String productName;
}