package com.lgmn.adminapi.dto.Model;

import com.lgmn.common.annotation.Condition;
import com.lgmn.common.domain.LgmnDto;
import lombok.Data;

@Data
public class DeleteModelDto extends LgmnDto {

        
	@Condition
    private Integer id;

    //产品id（当启用产品分类时必填）    
	@Condition
    private Integer pid;

    //产品名称    
	@Condition
    private String name;

    //长度    
	@Condition
    private Double longs;

    //宽度    
	@Condition
    private Double width;

    //高度    
	@Condition
    private Double height;

    //重量    
	@Condition
    private Double weight;

    //长度单位    
	@Condition
    private String longUnit;

    //宽度单位    
	@Condition
    private String widthUnit;

    //高度单位    
	@Condition
    private String heightUnit;

    //重量单位    
	@Condition
    private String weightUnit;

    //颜色    
	@Condition
    private String color;

    //备注    
	@Condition
    private String remark;

}