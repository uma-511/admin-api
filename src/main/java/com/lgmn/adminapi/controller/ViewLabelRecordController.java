package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.viewLabelRecord.QueryViewLabelRecord;
import com.lgmn.adminapi.service.ViewLabelRecordApiService;
import com.lgmn.common.domain.LgmnPage;
import com.lgmn.common.result.Result;
import com.lgmn.umaservices.basic.dto.ViewLabelRecordDto;
import com.lgmn.umaservices.basic.entity.ViewLabelRecordEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/viewLabelRecordApi")
public class ViewLabelRecordController {

    @Autowired
    ViewLabelRecordApiService service;

    @PostMapping("/page")
    public Result page (@RequestBody QueryViewLabelRecord dto) {
        try {
//            if(dto.getDateRange().size()>1){
//                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-NN-dd");
//                try {
//                    dto.setBeforeProdTime(new Timestamp(sdf.parse(dto.getDateRange().get(0)).getTime()));
//                    dto.setEndProdTime( new Timestamp(sdf.parse(dto.getDateRange().get(1)).getTime()));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            }
            ViewLabelRecordDto viewLabelRecordDto = new ViewLabelRecordDto();
            BeanUtils.copyProperties(dto,viewLabelRecordDto);
            if(dto.getProdTimes()!=null && StringUtils.isNotEmpty(dto.getProdTimes().get(0)) && StringUtils.isNotEmpty(dto.getProdTimes().get(1))) {
                Timestamp beforeTime = getTimestamp(dto.getProdTimes().get(0)+ " 00:00:00");
                Timestamp afterTime = getTimestamp(dto.getProdTimes().get(1)+ " 23:59:59");

                viewLabelRecordDto.setBeforeProdTime(beforeTime);
                viewLabelRecordDto.setEndProdTime(afterTime);
            }
            if(dto.getDeliveryDates()!=null && StringUtils.isNotEmpty(dto.getDeliveryDates().get(0)) && StringUtils.isNotEmpty(dto.getDeliveryDates().get(1))) {
                Timestamp beforeTime = getTimestamp(dto.getDeliveryDates().get(0)+ " 00:00:00");
                Timestamp afterTime = getTimestamp(dto.getDeliveryDates().get(1)+ " 23:59:59");

                viewLabelRecordDto.setBeforeDeliveryTime(beforeTime);
                viewLabelRecordDto.setEndDeliveryTime(afterTime);
            }
            LgmnPage<ViewLabelRecordEntity> page = service.page(viewLabelRecordDto);
            return Result.success(page);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.serverError(e.getMessage());
        }
    }

    private Timestamp getTimestamp(String dateString) throws ParseException {
        String pratten = "yyyy-MM-dd hh:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(pratten);
        Date afterDate = sdf.parse(dateString);
        return new Timestamp(afterDate.getTime());
    }

    @PostMapping("/detail/{id}")
    public Result detail (@PathVariable("id") Integer id) {
        ViewLabelRecordEntity entity = service.getById(id);
        return Result.success(entity);
    }
}
