package com.lgmn.adminapi.dto.umaConfig;

import lombok.Data;

@Data
public class UmaConfigSaveDto {
    //配置类别名称    
    private String name;
    //状态 0：禁用 1：启用    
    private Integer status;

}