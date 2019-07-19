package com.lgmn.adminapi.dto.user;

import lombok.Data;

@Data
public class LoginDto {
    private String userName;
    private String password;
}