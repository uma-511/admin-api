package com.lgmn.adminapi.dto.CustomerContact;

import java.util.Date;
import com.lgmn.common.annotation.Condition;
import com.lgmn.common.domain.LgmnDto;
import lombok.Data;

@Data
public class UpdateCustomerContactDto extends LgmnDto {

        
	@Condition
    private Integer id;

    //客户id    
	@Condition
    private Integer customerId;

    //联系人姓名    
	@Condition
    private String name;

    //联系电话    
	@Condition
    private String phone;

    //职位    
	@Condition
    private String position;

    //备注    
	@Condition
    private String remark;

//    //创建人
//	@Condition
//    private String createUser;
//
//    //创建时间
//	@Condition
//    private Date createTime;

}