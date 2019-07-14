package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.CustomerContact.SaveCustomerContactDto;
import com.lgmn.adminapi.dto.CustomerContact.UpdateCustomerContactDto;
import com.lgmn.adminapi.service.CustomerContactApiService;
import com.lgmn.adminapi.service.UserService;
import com.lgmn.common.domain.LgmnPage;
import com.lgmn.common.result.Result;
import com.lgmn.common.utils.ObjectTransfer;
import com.lgmn.umaservices.basic.dto.CustomerContactDto;
import com.lgmn.umaservices.basic.entity.CustomerContactEntity;
import com.lgmn.userservices.basic.entity.LgmnUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/customerContactApi")
public class CustomerContactController {

    @Autowired
    CustomerContactApiService service;

    @Autowired
    UserService userService;

    @PostMapping("/page")
    public Result page (@RequestBody CustomerContactDto dto) {
        try {
            dto.setDelFlag(0);
            LgmnPage<CustomerContactEntity> page = service.page(dto);
            for (CustomerContactEntity customerContactEntity : page.getList()) {
                LgmnUserEntity lgmnUserEntity = userService.getById(customerContactEntity.getCreateUser());
                customerContactEntity.setCreateUser(lgmnUserEntity.getNikeName());
            }
            return Result.success(page);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/update")
    public Result update (@RequestBody UpdateCustomerContactDto updateDto) {
        try {
            CustomerContactEntity entity = service.getById(updateDto.getId());
            ObjectTransfer.transValue(updateDto, entity);
            entity.setCreateUser("402881e86b26a9cb016b26b2e7410001");
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
            entity.setCreateUser("402881e86b26a9cb016b26b2e7410001");
            service.add(entity);
            return Result.success("添加成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/delete/{id}")
    public Result delete (@PathVariable("id") Integer id) {
        CustomerContactEntity entity = service.getById(id);
        entity.setDelFlag(1);
        service.update(entity);
        return Result.success("删除成功");
    }

    @PostMapping("/detail/{id}")
    public Result detail (@PathVariable("id") Integer id) {
        CustomerContactEntity entity = service.getById(id);
        LgmnUserEntity lgmnUserEntity = userService.getById(entity.getCreateUser());
        entity.setCreateUser(lgmnUserEntity.getNikeName());
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