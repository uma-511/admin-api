package com.lgmn.adminapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lgmn.common.service.LgmnAbstractApiService;
import com.lgmn.umaservices.basic.dto.ProductDto;
import com.lgmn.umaservices.basic.entity.ProductEntity;
import com.lgmn.umaservices.basic.service.ProductService;
import org.springframework.stereotype.Component;


@Component
public class ProductApiService extends LgmnAbstractApiService<ProductEntity, ProductDto, Integer, ProductService> {

    @Reference(version = "${demo.service.version}")
    private ProductService service;

    @Override
    public void initService() {
        setService(service);
    }
}