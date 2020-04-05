package com.lgmn.adminapi.dto.LgmnUser;

import lombok.Data;

@Data
public class UpdatePwdByAdminDto {
    private String id;
    private String password;
}