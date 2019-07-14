package com.lgmn.adminapi.dto.DeliveryNote;

import java.util.Date;
import com.lgmn.common.annotation.Condition;
import com.lgmn.common.domain.LgmnDto;
import lombok.Data;

@Data
public class SaveDeliveryNoteDto extends LgmnDto {

    	
	@Condition
    private Integer id;

    //客户名称	
	@Condition
    private String customer;

    //联系人	
	@Condition
    private String contact;

    //地址	
	@Condition
    private String address;

    //联系电话	
	@Condition
    private String phone;

    //送货单号	
	@Condition
    private String deliveryNum;

    //司机	
	@Condition
    private String driver;

    //车牌号	
	@Condition
    private String carNum;

    //开单时间	
//	@Condition
//    private Date createTime;

    //开单人	
//	@Condition
//    private String createUser;

    //备注	
	@Condition
    private String remark;

    //仓管	
	@Condition
    private String store;

    //收货人	
	@Condition
    private String revicer;

}