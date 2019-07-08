package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.Customer.SaveCustomerDto;
import com.lgmn.adminapi.dto.Customer.UpdateCustomerDto;
import com.lgmn.adminapi.service.CustomerApiService;
import com.lgmn.common.domain.LgmnPage;
import com.lgmn.common.result.Result;
import com.lgmn.common.utils.ObjectTransfer;
import com.lgmn.umaservices.basic.dto.CustomerDto;
import com.lgmn.umaservices.basic.entity.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/customerApi")
public class CustomerController {

    @Autowired
    CustomerApiService service;

    @PostMapping("/page")
    public Result page (@RequestBody CustomerDto dto) {
        try {
            LgmnPage<CustomerEntity> page = service.page(dto);
            return Result.success(page);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/update")
    public Result update (@RequestBody UpdateCustomerDto updateDto) {
        try {
            CustomerEntity entity = new CustomerEntity();
            ObjectTransfer.transValue(updateDto, entity);
            service.update(entity);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/add")
    public Result add (@RequestBody SaveCustomerDto saveDto) {
        try {
            CustomerEntity entity = new CustomerEntity();
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
        CustomerEntity entity = service.getById(id);
        return Result.success(entity);
    }


//    @PostMapping("/excel")
//    public void excel(@RequestBody SwcyAdDto swcyAdDto, HttpServletResponse response) throws Exception {
//        List<List<Object>> rows = new ArrayList();
//        LgmnPage<CustomerEntity> page = service.page(swcyAdDto);
//        for (CustomerEntity entity : page.getList()) {
//            List<Object> row = new ArrayList();
//                        row.add(entity.getId());
//                        row.add(entity.getName());
//                        row.add(entity.getPhone());
//                        row.add(entity.getFax());
//                        row.add(entity.getAddress());
//                        row.add(entity.getRemark());
//                        row.add(entity.getCreateUser());
//                        row.add(entity.getCreateTime());
//                    }
//        ExcelData data = new ExcelData();
//        data.setName("APP用户数据导出");
//        List<String> titles = new ArrayList();
//                            titles.add("id");
//                                titles.add("客户名称");
//                                titles.add("联系电话");
//                                titles.add("传真");
//                                titles.add("地址");
//                                titles.add("备注");
//                                titles.add("创建用户（保存用户名）");
//                                titles.add("创建时间");
//
//        data.setTitles(titles);
//        data.setRows(rows);
//        ExportExcelUtils.exportExcel(response, "数据导出.xlsx", data);
//    }

}