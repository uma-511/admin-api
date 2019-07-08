package com.lgmn.adminapi.vo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserVo implements Serializable {
    private String id;

    public List<String> getAvatar() {
        if(avatar==null){
            avatar=new ArrayList<>();
        }
        return avatar;
    }

    private List<String> avatar;
    private String account;
    private String nikeName;
    private Timestamp regTime;
    private Integer userType;
}