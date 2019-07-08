package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.DeliveryNote.SaveDeliveryNoteDto;
import com.lgmn.adminapi.dto.DeliveryNote.UpdateDeliveryNoteDto;
import com.lgmn.adminapi.service.DeliveryNoteApiService;
import com.lgmn.common.domain.LgmnPage;
import com.lgmn.common.result.Result;
import com.lgmn.common.utils.ObjectTransfer;
import com.lgmn.umaservices.basic.dto.DeliveryNoteDto;
import com.lgmn.umaservices.basic.entity.DeliveryNoteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/deliveryNoteApi")
public class DeliveryNoteController {

    @Autowired
    DeliveryNoteApiService service;

    @PostMapping("/page")
    public Result page (@RequestBody DeliveryNoteDto dto) {
        try {
            LgmnPage<DeliveryNoteEntity> page = service.page(dto);
            return Result.success(page);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/update")
    public Result update (@RequestBody UpdateDeliveryNoteDto updateDto) {
        try {
            DeliveryNoteEntity entity = new DeliveryNoteEntity();
            ObjectTransfer.transValue(updateDto, entity);
            service.update(entity);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/add")
    public Result add (@RequestBody SaveDeliveryNoteDto saveDto) {
        try {
            DeliveryNoteEntity entity = new DeliveryNoteEntity();
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
        DeliveryNoteEntity entity = service.getById(id);
        return Result.success(entity);
    }


//    @PostMapping("/excel")
//    public void excel(@RequestBody SwcyAdDto swcyAdDto, HttpServletResponse response) throws Exception {
//        List<List<Object>> rows = new ArrayList();
//        LgmnPage<DeliveryNoteEntity> page = service.page(swcyAdDto);
//        for (DeliveryNoteEntity entity : page.getList()) {
//            List<Object> row = new ArrayList();
//                        row.add(entity.getId());
//                        row.add(entity.getCustomer());
//                        row.add(entity.getContact());
//                        row.add(entity.getAddress());
//                        row.add(entity.getPhone());
//                        row.add(entity.getDeliveryNum());
//                        row.add(entity.getDriver());
//                        row.add(entity.getCarNum());
//                        row.add(entity.getCreateTime());
//                        row.add(entity.getCreateUser());
//                        row.add(entity.getRemark());
//                        row.add(entity.getStore());
//                        row.add(entity.getRevicer());
//                    }
//        ExcelData data = new ExcelData();
//        data.setName("APP用户数据导出");
//        List<String> titles = new ArrayList();
//                            titles.add("id");
//                                titles.add("客户名称");
//                                titles.add("联系人");
//                                titles.add("地址");
//                                titles.add("联系电话");
//                                titles.add("送货单号");
//                                titles.add("司机");
//                                titles.add("车牌号");
//                                titles.add("开单时间");
//                                titles.add("开单人");
//                                titles.add("备注");
//                                titles.add("仓管");
//                                titles.add("收货人");
//
//        data.setTitles(titles);
//        data.setRows(rows);
//        ExportExcelUtils.exportExcel(response, "数据导出.xlsx", data);
//    }

}