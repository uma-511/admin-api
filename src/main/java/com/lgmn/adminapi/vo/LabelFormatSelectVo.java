package com.lgmn.adminapi.vo;

import com.lgmn.common.domain.LgmnVo;
import com.lgmn.umaservices.basic.entity.LabelFormatEntity;
import lombok.Data;

@Data
public class LabelFormatSelectVo extends LgmnVo<LabelFormatEntity,LabelFormatSelectVo> {
    private int id;
    private String name;
}