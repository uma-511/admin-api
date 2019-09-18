package com.lgmn.adminapi.dto.DeliveryNote;

import com.lgmn.common.domain.LgmnDto;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UpdateDeliveryNoteDto extends LgmnDto {

    private Integer id;
    private Integer customerId;
    private String customer;
    private Integer printed;
    private Timestamp paymentTime;

}