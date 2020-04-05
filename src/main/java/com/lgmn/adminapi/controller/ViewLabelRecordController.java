package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.viewLabelRecord.QueryViewLabelRecord;
import com.lgmn.adminapi.service.ViewLabelRecordApiService;
import com.lgmn.common.domain.LgmnPage;
import com.lgmn.common.result.Result;
import com.lgmn.umaservices.basic.dto.ViewLabelRecordDto;
import com.lgmn.umaservices.basic.entity.ViewLabelRecordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
            viewLabelRecordDto.setOrderNo(dto.getOrderNo());
            viewLabelRecordDto.setLabelNum(dto.getLabelNum());
            viewLabelRecordDto.setPageNumber(dto.getPageNumber());
            viewLabelRecordDto.setPageSize(dto.getPageSize());
            if(dto.getSectionDay()!=null) {
                viewLabelRecordDto.setBeforeProdTime(dto.getSectionDay().get(0));
                viewLabelRecordDto.setEndProdTime(dto.getSectionDay().get(1));
            }
            LgmnPage<ViewLabelRecordEntity> page = service.page(viewLabelRecordDto);
            return Result.success(page);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/detail/{id}")
    public Result detail (@PathVariable("id") Integer id) {
        ViewLabelRecordEntity entity = service.getById(id);
        return Result.success(entity);
    }
}
