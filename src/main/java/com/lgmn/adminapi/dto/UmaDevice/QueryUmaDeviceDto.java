package com.lgmn.adminapi.dto.UmaDevice;

import com.lgmn.common.annotation.Condition;
import com.lgmn.common.domain.LgmnDto;
import lombok.Data;

@Data
public class QueryUmaDeviceDto extends LgmnDto {

        
	@Condition
    private Integer id;

    //设备ip    
	@Condition
    private String ip;

    //设备名称    
	@Condition
    private String name;

    //设备类型：0：打印机 1：触摸屏 2：扫描抢 3：秤 4：标签打印机 5：手持机    
	@Condition
    private Integer type;

    //使用状态：1：正常，0：停用    
	@Condition
    private Integer status;

    //设备分组id    
	@Condition
    private Integer groupId;

    //设备分组名称    
	@Condition
    private String groupName;

}