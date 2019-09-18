package com.lgmn.adminapi.dto.Customer;

import com.lgmn.common.annotation.Condition;
import com.lgmn.common.domain.LgmnDto;
import lombok.Data;

@Data
public class SaveCustomerDto extends LgmnDto {

    	
	@Condition
    private Integer id;

    //客户名称	
	@Condition
    private String name;

    //联系电话	
	@Condition
    private String phone;

    //传真	
	@Condition
    private String fax;

    //地址	
	@Condition
    private String address;

    //备注	
	@Condition
    private String remark;

    //客户编码
	@Condition
    private String customerCode;

    @Condition
    private String contacts;

    @Condition
    private String logistics;

    @Condition
    private String logisticsPhone;

    @Condition
    private String logisticsAddress;

    @Condition
    private Integer orderReminder;

    @Condition
    private Integer hadReminded;

//    //创建用户（保存用户名）
//	@Condition
//    private String createUser;
//
//    //创建时间
//	@Condition
//    private Date createTime;

}