package com.lgmn.adminapi.vo;

import com.lgmn.common.domain.LgmnVo;
import com.lgmn.umaservices.basic.entity.ModelEntity;
import lombok.Data;

@Data
public class ModelListVo extends LgmnVo<ModelEntity, ModelListVo> {
    private int id;
    private String name;
}
