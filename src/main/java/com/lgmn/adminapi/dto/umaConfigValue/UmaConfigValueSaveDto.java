package com.lgmn.adminapi.dto.umaConfigValue;

import lombok.Data;

@Data
public class UmaConfigValueSaveDto {
    //配置类别id    
    private Integer configId;
    //配置值    
    private String value;
    //状态 0：禁用 1：启用    
    private Integer status;

}