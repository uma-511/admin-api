package com.lgmn.adminapi.dto.viewOrderReminder;

import com.lgmn.common.domain.LgmnDto;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ViewOrderReminderSaveDto extends LgmnDto {

    private Integer id;

    private String name;

    private String phone;

    private Timestamp createTime;

}