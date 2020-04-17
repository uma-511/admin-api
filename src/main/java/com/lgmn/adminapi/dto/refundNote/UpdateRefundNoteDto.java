package com.lgmn.adminapi.dto.refundNote;

import com.lgmn.common.domain.LgmnDto;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UpdateRefundNoteDto extends LgmnDto {

    private Integer id;
    private Integer customerId;
    private String customer;
    private Integer printed;
    private Timestamp paymentTime;

}