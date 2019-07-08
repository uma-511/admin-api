package com.lgmn.adminapi.dto.LgmnPermission;

import java.util.Date;
import com.lgmn.common.annotation.Condition;
import com.lgmn.common.domain.LgmnDto;
import lombok.Data;

@Data
public class SaveLgmnPermissionDto extends LgmnDto {

    	
	@Condition
    private String id;

    //权限名称	
	@Condition
    private String name;

    //简体中文名称	
	@Condition
    private String chineseName;

    //备注	
	@Condition
    private String remark;

    //创建时间	
	@Condition
    private Date createTime;

    //更新时间	
	@Condition
    private Date updateTime;

    //权限类别 1：模块 2：功能	
	@Condition
    private Integer type;

    //是否设置多语言	
	@Condition
    private Integer multilang;

    //多语言id	
	@Condition
    private Integer langId;

}