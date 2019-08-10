package com.lgmn.adminapi.dto.LgmnUser;

import lombok.Data;

@Data
public class LoginDto {
    private String userName;
    private String password;
}