package com.lgmn.adminapi.dto.DeliveryNote;

import com.lgmn.common.domain.LgmnDto;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class SaveDeliveryNoteDto extends LgmnDto {
    private Integer customerId;
    private String customer;
    private Timestamp paymentTime;
}