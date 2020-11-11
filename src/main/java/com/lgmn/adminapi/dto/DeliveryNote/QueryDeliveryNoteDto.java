package com.lgmn.adminapi.dto.DeliveryNote;

import com.lgmn.common.annotation.Condition;
import com.lgmn.common.domain.LgmnDto;
import com.lgmn.common.enums.ConditionExcept;
import lombok.Data;

import java.util.List;

@Data
public class QueryDeliveryNoteDto extends LgmnDto {

    @Condition(
            except = ConditionExcept.CONTAIN,
            ignoreEmpty = true
    )
    private String deliveryNum;

    private List<String> createDates;

    @Condition
    private Integer clientId;
    @Condition
    private String clientName;
    //编号
    @Condition
    private String number;
    //名称
    @Condition
    private String name;
    //规格
    @Condition
    private String specs;
    //宽度
    @Condition
    private String width;
    //颜色
    @Condition
    private String color;

}