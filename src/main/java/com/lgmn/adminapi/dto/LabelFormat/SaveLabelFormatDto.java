package com.lgmn.adminapi.dto.LabelFormat;

import com.lgmn.common.annotation.Condition;
import com.lgmn.common.domain.LgmnDto;
import lombok.Data;

@Data
public class SaveLabelFormatDto extends LgmnDto {

    	
	@Condition
    private Integer id;

    	
	@Condition
    private String name;

}