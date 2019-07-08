package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.LabelRecord.SaveLabelRecordDto;
import com.lgmn.adminapi.dto.LabelRecord.UpdateLabelRecordDto;
import com.lgmn.adminapi.service.LabelRecordApiService;
import com.lgmn.common.domain.LgmnPage;
import com.lgmn.common.result.Result;
import com.lgmn.common.utils.ObjectTransfer;
import com.lgmn.umaservices.basic.dto.LabelRecordDto;
import com.lgmn.umaservices.basic.entity.LabelRecordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/labelRecordApi")
public class LabelRecordController {

    @Autowired
    LabelRecordApiService service;

    @PostMapping("/page")
    public Result page (@RequestBody LabelRecordDto dto) {
        try {
            LgmnPage<LabelRecordEntity> page = service.page(dto);
            return Result.success(page);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/update")
    public Result update (@RequestBody UpdateLabelRecordDto updateDto) {
        try {
            LabelRecordEntity entity = new LabelRecordEntity();
            ObjectTransfer.transValue(updateDto, entity);
            service.update(entity);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/add")
    public Result add (@RequestBody SaveLabelRecordDto saveDto) {
        try {
            LabelRecordEntity entity = new LabelRecordEntity();
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
        LabelRecordEntity entity = service.getById(id);
        return Result.success(entity);
    }


//    @PostMapping("/excel")
//    public void excel(@RequestBody SwcyAdDto swcyAdDto, HttpServletResponse response) throws Exception {
//        List<List<Object>> rows = new ArrayList();
//        LgmnPage<LabelRecordEntity> page = service.page(swcyAdDto);
//        for (LabelRecordEntity entity : page.getList()) {
//            List<Object> row = new ArrayList();
//                        row.add(entity.getId());
//                        row.add(entity.getLabelNum());
//                        row.add(entity.getPackId());
//                        row.add(entity.getOrderId());
//                        row.add(entity.getProdId());
//                        row.add(entity.getModelId());
//                        row.add(entity.getStatus());
//                        row.add(entity.getProdTime());
//                        row.add(entity.getInTime());
//                        row.add(entity.getOutTime());
//                        row.add(entity.getInvalidTime());
//                        row.add(entity.getLabelType());
//                        row.add(entity.getNetWeight());
//                        row.add(entity.getGrossWeight());
//                        row.add(entity.getSkinWeight());
//                        row.add(entity.getProdUser());
//                        row.add(entity.getInUser());
//                        row.add(entity.getOutUser());
//                        row.add(entity.getInvalidUser());
//                        row.add(entity.getRecordType());
//                    }
//        ExcelData data = new ExcelData();
//        data.setName("APP用户数据导出");
//        List<String> titles = new ArrayList();
//                            titles.add("id");
//                                titles.add("存货编码");
//                                titles.add("包号");
//                                titles.add("订单号");
//                                titles.add("产品id");
//                                titles.add("型号id");
//                                titles.add("存储状态");
//                                titles.add("生产日期");
//                                titles.add("入库日期");
//                                titles.add("出库日期");
//                                titles.add("作废日期");
//                                titles.add("标签类型：0：产品标签 1：打包标签");
//                                titles.add("净重");
//                                titles.add("毛重");
//                                titles.add("皮重");
//                                titles.add("生产人id");
//                                titles.add("入库人id");
//                                titles.add("出库人id");
//                                titles.add("作废人id");
//                                titles.add("记录类型 0：生成 1：导入");
//
//        data.setTitles(titles);
//        data.setRows(rows);
//        ExportExcelUtils.exportExcel(response, "数据导出.xlsx", data);
//    }

}