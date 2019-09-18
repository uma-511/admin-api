package com.lgmn.adminapi.dto.viewOrderReminder;

import com.lgmn.common.annotation.Condition;
import com.lgmn.common.domain.LgmnDto;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ViewOrderReminderQueryDto extends LgmnDto {

    private Integer id;

    private String name;

    private String phone;

    private Timestamp createTime;

}