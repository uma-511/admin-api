package com.lgmn.adminapi.dto.uma;

import lombok.Data;

@Data
public class PostLabelRecordDto {
    private String orderNo;
    // storage_of_workshop 车间入仓， workshop_depot 车间出仓
    private String type;
}
