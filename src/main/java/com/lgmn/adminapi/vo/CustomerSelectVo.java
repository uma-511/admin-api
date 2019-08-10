package com.lgmn.adminapi.vo;

import com.lgmn.common.domain.LgmnVo;
import com.lgmn.umaservices.basic.entity.CustomerEntity;
import lombok.Data;

@Data
public class CustomerSelectVo extends LgmnVo<CustomerEntity, CustomerSelectVo> {
    private int id;
    private String name;
}