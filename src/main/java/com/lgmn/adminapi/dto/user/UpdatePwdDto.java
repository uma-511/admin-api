package com.lgmn.adminapi.dto.user;

import lombok.Data;

@Data
public class UpdatePwdDto {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}