package com.lgmn.adminapi.dto.ViewYjStock;

import lombok.Data;

@Data
public class SaveViewYjStockDto {

    private Integer id;

    //名称(uma_config)
    private String name;

    //规格(uma_config)
    private String specs;

    //宽度(uma_config)
    private String width;

    //颜色(uma_config)
    private String color;

    private Double stock;

    private Double drk;

}