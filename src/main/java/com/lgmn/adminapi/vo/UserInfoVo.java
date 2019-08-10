package com.lgmn.adminapi.vo;

import com.lgmn.common.domain.LgmnUserInfo;
import com.lgmn.common.domain.LgmnVo;
import lombok.Data;

import java.util.List;

@Data
public class UserInfoVo extends LgmnVo<LgmnUserInfo,UserInfoVo> {
    private String nikeName;
    private String account;
    private List<String> access;
}