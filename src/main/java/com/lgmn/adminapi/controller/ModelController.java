package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.Model.SaveModelDto;
import com.lgmn.adminapi.dto.Model.UpdateModelDto;
import com.lgmn.adminapi.service.ModelApiService;
import com.lgmn.common.domain.LgmnPage;
import com.lgmn.common.result.Result;
import com.lgmn.common.utils.ObjectTransfer;
import com.lgmn.umaservices.basic.dto.ModelDto;
import com.lgmn.umaservices.basic.entity.ModelEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/modelApi")
public class ModelController {

    @Autowired
    ModelApiService service;

    @PostMapping("/page")
    public Result page (@RequestBody ModelDto dto) {
        try {
            LgmnPage<ModelEntity> page = service.page(dto);
            return Result.success(page);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/update")
    public Result update (@RequestBody UpdateModelDto updateDto) {
        try {
            ModelEntity entity = new ModelEntity();
            ObjectTransfer.transValue(updateDto, entity);
            service.update(entity);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/add")
    public Result add (@RequestBody SaveModelDto saveDto) {
        try {
            ModelEntity entity = new ModelEntity();
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
        ModelEntity entity = service.getById(id);
        return Result.success(entity);
    }


//    @PostMapping("/excel")
//    public void excel(@RequestBody SwcyAdDto swcyAdDto, HttpServletResponse response) throws Exception {
//        List<List<Object>> rows = new ArrayList();
//        LgmnPage<ModelEntity> page = service.page(swcyAdDto);
//        for (ModelEntity entity : page.getList()) {
//            List<Object> row = new ArrayList();
//                        row.add(entity.getId());
//                        row.add(entity.getPid());
//                        row.add(entity.getName());
//                        row.add(entity.getLongs());
//                        row.add(entity.getWidth());
//                        row.add(entity.getHeight());
//                        row.add(entity.getWeight());
//                        row.add(entity.getLongUnit());
//                        row.add(entity.getWidthUnit());
//                        row.add(entity.getHeightUnit());
//                        row.add(entity.getWeightUnit());
//                        row.add(entity.getColor());
//                        row.add(entity.getRemark());
//                    }
//        ExcelData data = new ExcelData();
//        data.setName("APP用户数据导出");
//        List<String> titles = new ArrayList();
//                            titles.add("id");
//                                titles.add("产品id（当启用产品分类时必填）");
//                                titles.add("产品名称");
//                                titles.add("长度");
//                                titles.add("宽度");
//                                titles.add("高度");
//                                titles.add("重量");
//                                titles.add("长度单位");
//                                titles.add("宽度单位");
//                                titles.add("高度单位");
//                                titles.add("重量单位");
//                                titles.add("颜色");
//                                titles.add("备注");
//
//        data.setTitles(titles);
//        data.setRows(rows);
//        ExportExcelUtils.exportExcel(response, "数据导出.xlsx", data);
//    }

}