package com.lgmn.adminapi.dto.SwcyStore;

import com.lgmn.common.domain.LgmnDto;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class SwcyStoreUpdateDto extends LgmnDto {

    private Integer id;

    private String uid;

    private Integer brandId;

    private String name;

    private String address;

    private String lat;

    private String lng;

    private Integer industryId;

    private Integer provinceId;

    private Integer cityId;

    private Integer areaId;

    private Integer likeVolume;

    private Timestamp createTime;

    private String photo;

    private String desc;

}