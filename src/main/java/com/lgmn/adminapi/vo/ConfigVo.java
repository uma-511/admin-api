package com.lgmn.adminapi.vo;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ConfigVo {
    Map<String, List<ConfigValueVo>> configs;

    public ConfigVo(){
        this.configs = new HashMap<>();
    }
}