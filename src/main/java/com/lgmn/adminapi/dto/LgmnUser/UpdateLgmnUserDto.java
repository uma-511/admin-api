package com.lgmn.adminapi.dto.LgmnUser;

import com.lgmn.common.annotation.Condition;
import com.lgmn.common.domain.LgmnDto;
import lombok.Data;

@Data
public class UpdateLgmnUserDto extends LgmnDto {
	@Condition
    private String id;
    //登录账号    
	@Condition
    private String account;
    //昵称    
	@Condition
    private String nikeName;
}