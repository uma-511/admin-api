package com.lgmn.adminapi.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lgmn.adminapi.vo.ConfigValueVo;
import com.lgmn.adminapi.vo.ConfigVo;
import com.lgmn.common.result.Result;
import com.lgmn.common.service.LgmnAbstractApiService;
import com.lgmn.umaservices.basic.dto.UmaConfigDto;
import com.lgmn.umaservices.basic.entity.UmaConfigEntity;
import com.lgmn.umaservices.basic.entity.UmaConfigValueEntity;
import com.lgmn.umaservices.basic.service.UmaConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class UmaConfigApiService extends LgmnAbstractApiService<UmaConfigEntity, UmaConfigDto, Integer, UmaConfigService> {

    @Reference(version = "${demo.service.version}")
    private UmaConfigService service;

    @Autowired
    private UmaConfigValueApiService umaConfigValueApiService;

    @Override
    public void initService() {
        setService(service);
    }

    public Result configGroup(){
        Result result = Result.success("获取配置成功");
        try {
            List<UmaConfigEntity> configs = this.all();
            List<UmaConfigValueEntity> configValues = umaConfigValueApiService.all();

            ConfigVo configVo = new ConfigVo();

            for (UmaConfigEntity config: configs) {
                List<ConfigValueVo>  values = new ArrayList<>();
                configVo.getConfigs().put(config.getNumber(),values);
                for (UmaConfigValueEntity value: configValues) {
                    if(config.getId()==value.getConfigId()){
                        ConfigValueVo configValueVo = new ConfigValueVo();
                        configValueVo.setId(value.getId());
                        configValueVo.setValue(value.getValue());
                        values.add(configValueVo);
                    }
                }
            }
            result.setData(configVo);
        } catch (Exception e) {
            result = Result.success("获取配置失败");
            e.printStackTrace();
        }finally {
            return result;
        }
    }
}