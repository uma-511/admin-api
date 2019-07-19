package com.lgmn.adminapi.dto.userRole;

import lombok.Data;

import java.util.List;

@Data
public class AddUserRoleDto {
    private String userId;
    private List<String> roleIds;
}