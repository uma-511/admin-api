package com.lgmn.adminapi.dto.LgmnUser;

import java.util.Date;
import com.lgmn.common.annotation.Condition;
import com.lgmn.common.domain.LgmnDto;
import lombok.Data;

@Data
public class UpdateLgmnUserDto extends LgmnDto {

        
	@Condition
    private String id;

    //头像    
	@Condition
    private String avatar;

    //登录账号    
	@Condition
    private String account;

    //昵称    
	@Condition
    private String nikeName;

    //密码    
	@Condition
    private String password;

    //密码盐    
	@Condition
    private String salt;

    //注册时间    
	@Condition
    private Date regTime;

    //最后一次登录时间    
	@Condition
    private Date lastLoginTime;

    //用户类型 0：app用户 1：销售员 3：后台用户    
	@Condition
    private Integer userType;

}