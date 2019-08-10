package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.Order.SaveOrderDto;
import com.lgmn.adminapi.dto.Order.UpdateOrderDto;
import com.lgmn.adminapi.service.*;
import com.lgmn.adminapi.vo.UOrderPageVo;
import com.lgmn.common.domain.LgmnPage;
import com.lgmn.common.result.Result;
import com.lgmn.common.utils.ObjectTransfer;
import com.lgmn.umaservices.basic.dto.ViewOrderDto;
import com.lgmn.umaservices.basic.entity.*;
import com.lgmn.userservices.basic.entity.LgmnUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/orderApi")
public class OrderController {

    @Autowired
    OrderApiService service;

    @Autowired
    UserService userService;

    @Autowired
    ProductApiService productApiService;

    @Autowired
    ModelApiService modelApiService;

    @Autowired
    LabelFormatApiService labelFormatApiService;

    @Autowired
    CustomerApiService customerApiService;

    @Autowired
    ViewOrderApiService viewOrderApiService;

    @PostMapping("/page")
    public Result page (@RequestBody ViewOrderDto dto) {
        try {
            dto.setDelFlag(0);
            LgmnPage<ViewOrderEntity> page = viewOrderApiService.page(dto);
            return Result.success(page);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/update")
    public Result update (@RequestBody UpdateOrderDto updateDto) {
        try {
            UOrderEntity entity = service.getById(updateDto.getId());
            ObjectTransfer.transValue(updateDto, entity);
            entity.setCreateUser("402881e86b26a9cb016b26b2e7410001");
            service.update(entity);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/add")
    public Result add (@RequestBody SaveOrderDto saveDto) {
        try {
            UOrderEntity entity = new UOrderEntity();
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
        UOrderEntity entity = service.getById(id);
        entity.setDelFlag(1);
        service.update(entity);
        return Result.success("删除成功");
    }

    @PostMapping("/detail/{id}")
    public Result detail (@PathVariable("id") Integer id) {
        UOrderEntity entity = service.getById(id);
        UOrderPageVo uOrderPageVo = new UOrderPageVo().getVo(entity, UOrderPageVo.class);
        LgmnUserEntity lgmnUserEntity = userService.getById(uOrderPageVo.getCreateUser());
        uOrderPageVo.setCreateUser(lgmnUserEntity.getNikeName());
        CustomerEntity customerEntity = customerApiService.getById(uOrderPageVo.getClientId());
        uOrderPageVo.setClientName(customerEntity.getName());
        ProductEntity productEntity = productApiService.getById(uOrderPageVo.getProdId());
        uOrderPageVo.setProdName(productEntity.getName());
        LabelFormatEntity labelFormatEntity = labelFormatApiService.getById(uOrderPageVo.getLableId());
        uOrderPageVo.setLableName(labelFormatEntity.getName());
        ModelEntity modelEntity = modelApiService.getById(uOrderPageVo.getModelId());
        uOrderPageVo.setModelName(modelEntity.getName());
        return Result.success(uOrderPageVo);
    }


//    @PostMapping("/excel")
//    public void excel(@RequestBody SwcyAdDto swcyAdDto, HttpServletResponse response) throws Exception {
//        List<List<Object>> rows = new ArrayList();
//        LgmnPage<OrderEntity> page = service.page(swcyAdDto);
//        for (OrderEntity entity : page.getList()) {
//            List<Object> row = new ArrayList();
//                        row.add(entity.getId());
//                        row.add(entity.getOrderNo());
//                        row.add(entity.getProdId());
//                        row.add(entity.getModelId());
//                        row.add(entity.getClientId());
//                        row.add(entity.getRequirement());
//                        row.add(entity.getRemark());
//                        row.add(entity.getLableId());
//                        row.add(entity.getCreateTime());
//                        row.add(entity.getCreateUser());
//                        row.add(entity.getDeliveryDate());
//                        row.add(entity.getQuantity());
//                        row.add(entity.getInt1());
//                        row.add(entity.getInt2());
//                        row.add(entity.getInt3());
//                        row.add(entity.getInt4());
//                        row.add(entity.getInt5());
//                        row.add(entity.getVar501());
//                        row.add(entity.getVar502());
//                        row.add(entity.getVar503());
//                        row.add(entity.getVar504());
//                        row.add(entity.getVar505());
//                        row.add(entity.getVar1001());
//                        row.add(entity.getVar1002());
//                        row.add(entity.getVar1003());
//                        row.add(entity.getVar1004());
//                        row.add(entity.getVar1005());
//                        row.add(entity.getVar2001());
//                        row.add(entity.getVar2002());
//                        row.add(entity.getVar2003());
//                        row.add(entity.getVar2004());
//                        row.add(entity.getVar2005());
//                        row.add(entity.getDatetime1());
//                        row.add(entity.getDatetime2());
//                        row.add(entity.getDecimal1021());
//                        row.add(entity.getDecimal1022());
//                        row.add(entity.getDecimal1023());
//                        row.add(entity.getDecimal1024());
//                        row.add(entity.getDecimal1131());
//                        row.add(entity.getDecimal1132());
//                        row.add(entity.getDecimal1133());
//                        row.add(entity.getDecimal1134());
//                    }
//        ExcelData data = new ExcelData();
//        data.setName("APP用户数据导出");
//        List<String> titles = new ArrayList();
//                            titles.add("id");
//                                titles.add("orderNo");
//                                titles.add("产品id");
//                                titles.add("型号id");
//                                titles.add("客户id");
//                                titles.add("工艺要求");
//                                titles.add("备注");
//                                titles.add("标签id");
//                                titles.add("订单创建时间");
//                                titles.add("订单创建人");
//                                titles.add("交货时间");
//                                titles.add("数量");
//                                titles.add("保留int1");
//                                titles.add("保留int2");
//                                titles.add("保留int3");
//                                titles.add("保留int4");
//                                titles.add("保留int5");
//                                titles.add("保留varchar(50)1");
//                                titles.add("保留varchar(50)2");
//                                titles.add("保留varchar(50)3");
//                                titles.add("保留varchar(50)4");
//                                titles.add("保留varchar(50)5");
//                                titles.add("保留varchar(100)1");
//                                titles.add("保留varchar(100)2");
//                                titles.add("保留varchar(100)3");
//                                titles.add("保留varchar(100)4");
//                                titles.add("保留varchar(100)5");
//                                titles.add("保留varchar(200)1");
//                                titles.add("保留varchar(200)2");
//                                titles.add("保留varchar(200)3");
//                                titles.add("保留varchar(200)4");
//                                titles.add("保留varchar(200)5");
//                                titles.add("保留datetime1");
//                                titles.add("保留datetime2");
//                                titles.add("保留decimal(10,2)1");
//                                titles.add("保留decimal(10,2)2");
//                                titles.add("保留decimal(10,2)3");
//                                titles.add("保留decimal(10,2)4");
//                                titles.add("保留decimal(11,3)1");
//                                titles.add("保留decimal(11,3)2");
//                                titles.add("保留decimal(11,3)3");
//                                titles.add("保留decimal(11,3)4");
//
//        data.setTitles(titles);
//        data.setRows(rows);
//        ExportExcelUtils.exportExcel(response, "数据导出.xlsx", data);
//    }

}