package com.lgmn.adminapi.dto.LabelFormat;

import com.lgmn.common.annotation.Condition;
import com.lgmn.common.domain.LgmnDto;
import lombok.Data;

@Data
public class UpdateLabelFormatDto extends LgmnDto {

        
	@Condition
    private Integer id;

        
	@Condition
    private String name;

	@Condition
    private String path;

}