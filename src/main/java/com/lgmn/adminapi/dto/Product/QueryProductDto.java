package com.lgmn.adminapi.dto.Product;

import com.lgmn.common.annotation.Condition;
import com.lgmn.common.domain.LgmnDto;
import lombok.Data;

@Data
public class QueryProductDto extends LgmnDto {

        
	@Condition
    private Integer id;

    //产品名称    
	@Condition
    private String name;

}