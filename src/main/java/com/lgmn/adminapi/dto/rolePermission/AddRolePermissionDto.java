package com.lgmn.adminapi.dto.rolePermission;

import lombok.Data;

import java.util.List;

@Data
public class AddRolePermissionDto {
    private String roleId;
    private List<String> permissionIds;
}