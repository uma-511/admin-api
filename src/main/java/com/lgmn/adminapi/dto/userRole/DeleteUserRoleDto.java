package com.lgmn.adminapi.dto.userRole;

import lombok.Data;

import java.util.List;

@Data
public class DeleteUserRoleDto {
    private String userId;
    private List<String> roleId;
}