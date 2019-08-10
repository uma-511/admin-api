package com.lgmn.adminapi.dto.LgmnUser;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LogoutDto {
    @ApiModelProperty(value = "token")
    @NotNull(message = "token 不能为null")
    private String accessToken;

}