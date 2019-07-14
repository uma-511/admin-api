package com.lgmn.adminapi.vo;

import com.lgmn.common.domain.LgmnVo;
import com.lgmn.umaservices.basic.entity.UOrderEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class UOrderPageVo extends LgmnVo<UOrderEntity, UOrderPageVo> {
    private int id;
    private String orderNo;
    private int prodId;
    private String prodName;
    private Integer modelId;
    private String modelName;
    private Integer clientId;
    private String clientName;
    private String requirement;
    private String remark;
    private Integer lableId;
    private String lableName;
    private Timestamp createTime;
    private String createUser;
    private Timestamp deliveryDate;
    private Integer quantity;
    private Integer int01;
    private Integer int02;
    private Integer int03;
    private Integer int04;
    private Integer int5;
    private String var501;
    private String var502;
    private String var503;
    private String var504;
    private String var505;
    private String var1001;
    private String var1002;
    private String var1003;
    private String var1004;
    private String var1005;
    private String var2001;
    private String var2002;
    private String var2003;
    private String var2004;
    private String var2005;
    private Timestamp datetime1;
    private Timestamp datetime2;
    private BigDecimal decimal1021;
    private BigDecimal decimal1022;
    private BigDecimal decimal1023;
    private BigDecimal decimal1024;
    private BigDecimal decimal1131;
    private BigDecimal decimal1132;
    private BigDecimal decimal1133;
    private BigDecimal decimal1134;
}
