package com.lgmn.adminapi.dto.permission;

import lombok.Data;

@Data
public class AddPermissionDto {
    private String name;
    private String chineseName;
    private String remark;
}