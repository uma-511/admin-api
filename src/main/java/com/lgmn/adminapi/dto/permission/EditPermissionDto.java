package com.lgmn.adminapi.dto.permission;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class EditPermissionDto {
    private String id;
    private String name;
    private String chineseName;
    private String remark;
    private Timestamp createTime;
}