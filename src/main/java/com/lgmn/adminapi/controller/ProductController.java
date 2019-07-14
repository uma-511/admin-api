package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.Product.SaveProductDto;
import com.lgmn.adminapi.dto.Product.UpdateProductDto;
import com.lgmn.adminapi.service.ProductApiService;
import com.lgmn.adminapi.vo.ProductListVo;
import com.lgmn.common.domain.LgmnPage;
import com.lgmn.common.result.Result;
import com.lgmn.common.utils.ObjectTransfer;
import com.lgmn.umaservices.basic.dto.ProductDto;
import com.lgmn.umaservices.basic.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/productApi")
public class ProductController {

    @Autowired
    ProductApiService service;

    @PostMapping("/page")
    public Result page (@RequestBody ProductDto dto) {
        try {
            dto.setDelFlag(0);
            LgmnPage<ProductEntity> page = service.page(dto);
            return Result.success(page);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/update")
    public Result update (@RequestBody UpdateProductDto updateDto) {
        try {
            ProductEntity entity = service.getById(updateDto.getId());
            ObjectTransfer.transValue(updateDto, entity);
            service.update(entity);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/add")
    public Result add (@RequestBody SaveProductDto saveDto) {
        try {
            ProductEntity entity = new ProductEntity();
            ObjectTransfer.transValue(saveDto, entity);
            service.add(entity);
            return Result.success("添加成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/delete/{id}")
    public Result delete (@PathVariable("id") Integer id) {
        ProductEntity entity = service.getById(id);
        entity.setDelFlag(1);
        service.update(entity);
        return Result.success("删除成功");
    }

    @PostMapping("/detail/{id}")
    public Result detail (@PathVariable("id") Integer id) {
        ProductEntity entity = service.getById(id);
        return Result.success(entity);
    }

    @PostMapping("/getProductAllList")
    public Result getProductAllList () {
        try {
            ProductDto productDto = new ProductDto();
            productDto.setDelFlag(0);
            List<ProductEntity> list = service.list(productDto);
            List<ProductListVo> productListVos = new ProductListVo().getVoList(list, ProductListVo.class);
            return Result.success(productListVos);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }


//    @PostMapping("/excel")
//    public void excel(@RequestBody SwcyAdDto swcyAdDto, HttpServletResponse response) throws Exception {
//        List<List<Object>> rows = new ArrayList();
//        LgmnPage<ProductEntity> page = service.page(swcyAdDto);
//        for (ProductEntity entity : page.getList()) {
//            List<Object> row = new ArrayList();
//                        row.add(entity.getId());
//                        row.add(entity.getName());
//                    }
//        ExcelData data = new ExcelData();
//        data.setName("APP用户数据导出");
//        List<String> titles = new ArrayList();
//                            titles.add("id");
//                                titles.add("产品名称");
//
//        data.setTitles(titles);
//        data.setRows(rows);
//        ExportExcelUtils.exportExcel(response, "数据导出.xlsx", data);
//    }

}