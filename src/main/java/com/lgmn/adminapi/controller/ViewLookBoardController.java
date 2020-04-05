package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.service.UmaDeviceApiService;
import com.lgmn.adminapi.service.ViewLookBoardApiService;
import com.lgmn.common.domain.LgmnOrder;
import com.lgmn.common.domain.LgmnPage;
import com.lgmn.common.result.Result;
import com.lgmn.umaservices.basic.dto.ViewLookBoardDto;
import com.lgmn.umaservices.basic.entity.UmaDeviceEntity;
import com.lgmn.umaservices.basic.entity.ViewLookBoardEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/viewLookBoardApi")
public class ViewLookBoardController {

    @Autowired
    ViewLookBoardApiService service;

    @Autowired
    UmaDeviceApiService deviceService;

    @PostMapping("/page")
    public Result page (@RequestBody ViewLookBoardDto dto, HttpServletRequest request) {
        try {
            String host = request.getRemoteHost();

            if("0:0:0:0:0:0:0:1".equals(host)){
                host = "localhost";
            }

            UmaDeviceEntity umaDeviceEntity = deviceService.findByIp(host);

            if(umaDeviceEntity != null){
                dto.setFloor(umaDeviceEntity.getName());
            }else{
                dto.setId(-1);
            }

            List<LgmnOrder> listOrder=new ArrayList<>();

            LgmnOrder overDateOrder=new LgmnOrder();

            overDateOrder.setDirection(Sort.Direction.DESC);
            overDateOrder.setProperty("overDate");
            listOrder.add(overDateOrder);

            LgmnOrder deliveryDateOrder=new LgmnOrder();
            deliveryDateOrder.setDirection(Sort.Direction.DESC);
            deliveryDateOrder.setProperty("deliveryDate");
            listOrder.add(deliveryDateOrder);

            dto.setOrders(listOrder);

            LgmnPage<ViewLookBoardEntity> page = service.page(dto);
            return Result.success(page);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.serverError(e.getMessage());
        }
    }
}