package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.deliveryList.DeliveryListSaveDto;
import com.lgmn.adminapi.dto.deliveryList.DeliveryListUpdateDto;
import com.lgmn.adminapi.service.DeliveryListApiService;
import com.lgmn.common.result.Result;
import com.lgmn.common.utils.ObjectTransfer;
import com.lgmn.umaservices.basic.dto.DeliveryListDto;
import com.lgmn.umaservices.basic.entity.DeliveryListEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/deliveryListApi")
public class DeliveryListController {

    @Autowired
    DeliveryListApiService service;

    @PostMapping("/page")
    public Result page (@RequestBody DeliveryListDto dto) {
        try {
            List<DeliveryListEntity> page = service.list(dto);
            return Result.success(page);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/update")
    public Result update (@RequestBody DeliveryListUpdateDto updateDto) {
        try {
            DeliveryListEntity entity = new DeliveryListEntity();
            ObjectTransfer.transValue(updateDto, entity);
            entity.setTotalPrice(entity.getUnitPrice().multiply(new BigDecimal(entity.getQuantity())));
            service.update(entity);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/add")
    public Result add (@RequestBody DeliveryListSaveDto saveDto) {
        try {
            DeliveryListEntity entity = new DeliveryListEntity();
            ObjectTransfer.transValue(saveDto, entity);
            service.add(entity);
            return Result.success("添加成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/delete/{id}")
    public Result delete (@PathVariable("id") Integer id) {
        service.deleteById(id);
        return Result.success("删除成功");
    }

    @PostMapping("/detail/{id}")
    public Result detail (@PathVariable("id") Integer id) {
        DeliveryListEntity entity = service.getById(id);
        return Result.success(entity);
    }


//    @PostMapping("/excel")
//    public void excel(@RequestBody SwcyAdDto swcyAdDto, HttpServletResponse response) throws Exception {
//        List<List<Object>> rows = new ArrayList();
//        LgmnPage<DeliveryListEntity> page = service.page(swcyAdDto);
//        for (DeliveryListEntity entity : page.getList()) {
//            List<Object> row = new ArrayList();
//                        row.add(entity.getId());
//                        row.add(entity.getDeliveryId());
//                        row.add(entity.getClientId());
//                        row.add(entity.getClientName());
//                        row.add(entity.getNumber());
//                        row.add(entity.getName());
//                        row.add(entity.getSpecs());
//                        row.add(entity.getWidth());
//                        row.add(entity.getColor());
//                        row.add(entity.getRequirement());
//                        row.add(entity.getScanQuantity());
//                        row.add(entity.getResidualQuantity());
//                        row.add(entity.getUnitPrice());
//                        row.add(entity.getTotalPrice());
//                    }
//        ExcelData data = new ExcelData();
//        data.setName("APP用户数据导出");
//        List<String> titles = new ArrayList();
//                            titles.add("id");
//                                titles.add("deliveryId");
//                                titles.add("clientId");
//                                titles.add("clientName");
//                                titles.add("编号");
//                                titles.add("名称");
//                                titles.add("规格");
//                                titles.add("宽度");
//                                titles.add("颜色");
//                                titles.add("工艺要求");
//                                titles.add("扫描数量");
//                                titles.add("剩余数量");
//                                titles.add("unitPrice");
//                                titles.add("totalPrice");
//
//        data.setTitles(titles);
//        data.setRows(rows);
//        ExportExcelUtils.exportExcel(response, "数据导出.xlsx", data);
//    }

}