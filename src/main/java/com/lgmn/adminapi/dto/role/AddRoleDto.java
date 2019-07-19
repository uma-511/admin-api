package com.lgmn.adminapi.dto.role;

import lombok.Data;

@Data
public class AddRoleDto {
    private String name;
    private String chineseName;
    private Byte valid;
    private String remark;
}