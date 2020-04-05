package com.lgmn.adminapi.dto.viewLabelRecord;

import com.lgmn.common.domain.LgmnDto;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class QueryViewLabelRecord extends LgmnDto {
    private String labelNum;
    private String orderNo;
    private List<Timestamp> sectionDay;
}