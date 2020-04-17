package com.lgmn.adminapi.vo;

import com.lgmn.common.domain.LgmnVo;
import com.lgmn.umaservices.basic.entity.RefundNoteEntity;
import lombok.Data;

@Data
public class HM_RefundNoteVo extends LgmnVo<RefundNoteEntity, HM_RefundNoteVo> {
    private String refundNum;
    private String customer;
}