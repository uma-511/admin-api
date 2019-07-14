package com.lgmn.adminapi.vo;

import com.lgmn.common.domain.LgmnVo;
import com.lgmn.umaservices.basic.entity.ProductEntity;
import lombok.Data;

@Data
public class ProductListVo extends LgmnVo<ProductEntity, ProductListVo> {
    private int id;
    private String name;
}
