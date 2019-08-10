package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.Customer.SaveCustomerDto;
import com.lgmn.adminapi.dto.Customer.UpdateCustomerDto;
import com.lgmn.adminapi.dto.Customer.YjClientSearchDto;
import com.lgmn.adminapi.service.CustomerApiService;
import com.lgmn.adminapi.service.UserService;
import com.lgmn.adminapi.vo.CustomerListVo;
import com.lgmn.adminapi.vo.CustomerSelectVo;
import com.lgmn.common.domain.LgmnPage;
import com.lgmn.common.domain.LgmnUserInfo;
import com.lgmn.common.result.Result;
import com.lgmn.common.utils.ObjectTransfer;
import com.lgmn.umaservices.basic.dto.CustomerDto;
import com.lgmn.umaservices.basic.entity.CustomerEntity;
import com.lgmn.userservices.basic.entity.LgmnUserEntity;
import com.lgmn.userservices.basic.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;
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
            return Result.success(page);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/update")
    public Result update (@RequestHeader String Authorization,@RequestBody UpdateCustomerDto updateDto, Principal principal) {
        try {
            CustomerEntity entity = service.getById(updateDto.getId());
            ObjectTransfer.transValue(updateDto, entity);
            service.update(entity);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/add")
    public Result add (@RequestHeader String Authorization,@RequestBody SaveCustomerDto saveDto, Principal principal) {
        LgmnUserInfo lgmnUserEntity = UserUtil.getCurrUser(principal);
        try {
            CustomerEntity entity = new CustomerEntity();
            ObjectTransfer.transValue(saveDto, entity);
            entity.setCreateUserId(lgmnUserEntity.getId());
            entity.setCreateUser(lgmnUserEntity.getNikeName());
            entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
            entity.setDelFlag(0);
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
        LgmnUserEntity lgmnUserEntity = userService.getById(entity.getCreateUserId());
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

    @PostMapping("/customerSelectList")
    public Result clientList(@RequestBody YjClientSearchDto yjClientSearchDto){
        Result result = Result.success("获取客户成功");
        try {
            CustomerDto dto=new CustomerDto();
            ObjectTransfer.transValue(yjClientSearchDto,dto);
            dto.setDelFlag(0);
            List<CustomerEntity> all = service.list(dto);
            CustomerSelectVo customerSelectVo = new CustomerSelectVo();
            List<CustomerSelectVo> list = customerSelectVo.getVoList(all,CustomerSelectVo.class);
            result.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            result = Result.serverError("获取客户失败");
        }finally {
            return result;
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