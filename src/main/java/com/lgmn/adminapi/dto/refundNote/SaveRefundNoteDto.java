package com.lgmn.adminapi.dto.refundNote;

import com.lgmn.common.domain.LgmnDto;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class SaveRefundNoteDto extends LgmnDto {
    private Integer customerId;
    private String customer;
    private Timestamp paymentTime;
}