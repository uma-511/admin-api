package com.lgmn.adminapi.dto.swcysupplierorderdetail;

import com.lgmn.common.annotation.Condition;
import com.lgmn.common.domain.LgmnDto;
import lombok.Data;

@Data
public class SwcySupplierOrderDetailQueryDto extends LgmnDto {

    private Integer id;

    private String orderId;

    private Integer commodityId;

    private String commodityName;

    private String commodityType;

    private String cover;

    private Double price;

    private Integer num;

}