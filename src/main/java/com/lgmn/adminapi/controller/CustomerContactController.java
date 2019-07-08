package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.CustomerContact.SaveCustomerContactDto;
import com.lgmn.adminapi.dto.CustomerContact.UpdateCustomerContactDto;
import com.lgmn.adminapi.service.CustomerContactApiService;
import com.lgmn.common.domain.LgmnPage;
import com.lgmn.common.result.Result;
import com.lgmn.common.utils.ObjectTransfer;
import com.lgmn.umaservices.basic.dto.CustomerContactDto;
import com.lgmn.umaservices.basic.entity.CustomerContactEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/customerContactApi")
public class CustomerContactController {

    @Autowired
    CustomerContactApiService service;

    @PostMapping("/page")
    public Result page (@RequestBody CustomerContactDto dto) {
        try {
            LgmnPage<CustomerContactEntity> page = service.page(dto);
            return Result.success(page);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/update")
    public Result update (@RequestBody UpdateCustomerContactDto updateDto) {
        try {
            CustomerContactEntity entity = new CustomerContactEntity();
            ObjectTransfer.transValue(updateDto, entity);
            service.update(entity);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/add")
    public Result add (@RequestBody SaveCustomerContactDto saveDto) {
        try {
            CustomerContactEntity entity = new CustomerContactEntity();
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
        CustomerContactEntity entity = service.getById(id);
        return Result.success(entity);
    }


//    @PostMapping("/excel")
//    public void excel(@RequestBody SwcyAdDto swcyAdDto, HttpServletResponse response) throws Exception {
//        List<List<Object>> rows = new ArrayList();
//        LgmnPage<CustomerContactEntity> page = service.page(swcyAdDto);
//        for (CustomerContactEntity entity : page.getList()) {
//            List<Object> row = new ArrayList();
//                        row.add(entity.getId());
//                        row.add(entity.getCustomerId());
//                        row.add(entity.getName());
//                        row.add(entity.getPhone());
//                        row.add(entity.getPosition());
//                        row.add(entity.getRemark());
//                        row.add(entity.getCreateUser());
//                        row.add(entity.getCreateTime());
//                    }
//        ExcelData data = new ExcelData();
//        data.setName("APP用户数据导出");
//        List<String> titles = new ArrayList();
//                            titles.add("id");
//                                titles.add("客户id");
//                                titles.add("联系人姓名");
//                                titles.add("联系电话");
//                                titles.add("职位");
//                                titles.add("备注");
//                                titles.add("创建人");
//                                titles.add("创建时间");
//
//        data.setTitles(titles);
//        data.setRows(rows);
//        ExportExcelUtils.exportExcel(response, "数据导出.xlsx", data);
//    }

}