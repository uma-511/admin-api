package com.lgmn.adminapi.vo;

import com.lgmn.common.domain.LgmnVo;
import com.lgmn.umaservices.basic.entity.YjProductEntity;
import lombok.Data;

@Data
public class YJProductVo  extends LgmnVo<YjProductEntity, YJProductVo> {
    private String num;
    private String name;
    private String specs;
    private String width;
    private String str1;
}