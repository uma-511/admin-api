package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.Customer.SaveCustomerDto;
import com.lgmn.adminapi.dto.Customer.UpdateCustomerDto;
import com.lgmn.adminapi.service.CustomerApiService;
import com.lgmn.adminapi.service.UserService;
import com.lgmn.adminapi.vo.CustomerListVo;
import com.lgmn.common.domain.LgmnPage;
import com.lgmn.common.result.Result;
import com.lgmn.common.utils.ObjectTransfer;
import com.lgmn.umaservices.basic.dto.CustomerDto;
import com.lgmn.umaservices.basic.entity.CustomerEntity;
import com.lgmn.userservices.basic.entity.LgmnUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/customerApi")
public class CustomerController {

    @Autowired
    CustomerApiService service;

    @Autowired
    UserService userService;

    @PostMapping("/page")
    public Result page (@RequestBody CustomerDto dto) {
        try {
            dto.setDelFlag(0);
            LgmnPage<CustomerEntity> page = service.page(dto);
            for (CustomerEntity customerEntity : page.getList()) {
                LgmnUserEntity lgmnUserEntity = userService.getById(customerEntity.getCreateUser());
                customerEntity.setCreateUser(lgmnUserEntity.getNikeName());
            }
            return Result.success(page);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/update")
    public Result update (@RequestBody UpdateCustomerDto updateDto) {
        try {
            CustomerEntity entity = service.getById(updateDto.getId());
            ObjectTransfer.transValue(updateDto, entity);
            entity.setCreateUser("402881e86b26a9cb016b26b2e7410001");
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
            entity.setCreateUser("402881e86b26a9cb016b26b2e7410001");
            service.add(entity);
            return Result.success("添加成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/delete/{id}")
    public Result delete (@PathVariable("id") Integer id) {
        CustomerEntity entity = service.getById(id);
        entity.setDelFlag(1);
        service.update(entity);
        return Result.success("删除成功");
    }

    @PostMapping("/detail/{id}")
    public Result detail (@PathVariable("id") Integer id) {
        CustomerEntity entity = service.getById(id);
        LgmnUserEntity lgmnUserEntity = userService.getById(entity.getCreateUser());
        entity.setCreateUser(lgmnUserEntity.getNikeName());
        return Result.success(entity);
    }

    @PostMapping("/getCustomerList")
    public Result getCustomerList () {
        try {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setDelFlag(0);
            List<CustomerEntity> list = service.list(customerDto);
            List<CustomerListVo> customerListVo = new CustomerListVo().getVoList(list, CustomerListVo.class);
            return Result.success(customerListVo);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
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