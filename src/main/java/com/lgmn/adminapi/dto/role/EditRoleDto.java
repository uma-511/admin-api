package com.lgmn.adminapi.dto.role;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class EditRoleDto {
    private String id;
    private String name;
    private String chineseName;
    private Byte valid;
    private String remark;
    private Timestamp createTime;
}