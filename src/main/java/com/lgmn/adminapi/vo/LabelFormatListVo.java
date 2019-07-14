package com.lgmn.adminapi.vo;

import com.lgmn.common.domain.LgmnVo;
import com.lgmn.umaservices.basic.entity.LabelFormatEntity;
import lombok.Data;

@Data
public class LabelFormatListVo extends LgmnVo<LabelFormatEntity, LabelFormatListVo> {
    private int id;
    private String name;
}
