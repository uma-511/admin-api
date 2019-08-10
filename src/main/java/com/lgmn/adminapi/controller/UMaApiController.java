package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.uma.DeliveryDto;
import com.lgmn.adminapi.dto.uma.ExitLoginDto;
import com.lgmn.adminapi.dto.uma.LoginDto;
import com.lgmn.adminapi.dto.uma.PostLabelRecordDto;
import com.lgmn.adminapi.service.*;
import com.lgmn.adminapi.vo.HM_DeliveryNoteVo;
import com.lgmn.common.domain.LgmnUserInfo;
import com.lgmn.common.result.Result;
import com.lgmn.common.result.ResultEnum;
import com.lgmn.umaservices.basic.dto.DeliveryListDto;
import com.lgmn.umaservices.basic.dto.DeliveryNoteDto;
import com.lgmn.umaservices.basic.dto.LabelRecordDto;
import com.lgmn.umaservices.basic.entity.DeliveryListEntity;
import com.lgmn.umaservices.basic.entity.DeliveryNoteEntity;
import com.lgmn.umaservices.basic.entity.LabelRecordEntity;
import com.lgmn.umaservices.basic.entity.YjOrderEntity;
import com.lgmn.userservices.basic.util.UserUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/umaApi")
public class UMaApiController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private LabelRecordApiService labelRecordApiService;

    @Autowired
    private DeliveryNoteApiService deliveryNoteApiService;

    @Autowired
    private YjOrderApiService orderApiService;

    @Autowired
    private DeliveryListApiService deliveryListApiService;

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public Result login (@RequestBody LoginDto loginDto) {
        try {
            return loginService.login(loginDto);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/exitLogin")
    public Result exitLogin(@RequestBody ExitLoginDto exitLoginDto) {
        return loginService.exitLogin(exitLoginDto);
    }

    @ApiOperation(value = "上传数据")
    @PostMapping("/postLabelRecord")
    public Result postLabelRecord (@RequestHeader String Authorization, Principal principal, @RequestBody PostLabelRecordDto postLabelRecordDto) {
        LgmnUserInfo lgmnUserInfo = UserUtil.getCurrUser(principal);
        try {
            LabelRecordDto labelRecordDto = new LabelRecordDto();
            labelRecordDto.setLabelNum(postLabelRecordDto.getOrderNo());
            List<LabelRecordEntity> labelRecordEntitys = labelRecordApiService.list(labelRecordDto);
            if (labelRecordEntitys.size() <= 0) {
                return Result.error(ResultEnum.DATA_NOT_EXISTS);
            }
            LabelRecordEntity labelRecord = labelRecordEntitys.get(0);
            Integer status = null;
            if (labelRecord.getStatus() == 8) {
                return Result.error(ResultEnum.DATA_PROHIBIT);
            }
            // 车间入仓
            if ("storage_of_workshop".equals(postLabelRecordDto.getType())) {
                if (labelRecord.getStatus() != 0) {
                    return Result.error(ResultEnum.HASENTEREDWAREHOUSE);
                }
                status = 1;
                labelRecord.setInUser(lgmnUserInfo.getId());
                labelRecord.setInTime(new Timestamp(System.currentTimeMillis()));
                // 车间出仓
            } else if ("workshop_depot".equals(postLabelRecordDto.getType()))  {
                if (labelRecord.getStatus() == 2) {
                    return Result.error(ResultEnum.OUTOF_WAREHOUSE_ERROR);
                }
                if (labelRecord.getStatus() != 1) {
                    return Result.error(ResultEnum.NOT_IN_WAREHOUSE_ERROR);
                }
                status = 2;
                labelRecord.setOutUser(lgmnUserInfo.getId());
                labelRecord.setOutTime(new Timestamp(System.currentTimeMillis()));

                // 返仓
            } else {
                if (labelRecord.getStatus() == 1) {
                    return Result.error(ResultEnum.HASENTEREDWAREHOUSE);
                }
                if (labelRecord.getStatus() != 2) {
                    return Result.error(ResultEnum.NOT_OUTOF_WAREHOUSE_ERROR);
                }
                status = 1;
                labelRecord.setInUser(lgmnUserInfo.getId());
                labelRecord.setInTime(new Timestamp(System.currentTimeMillis()));
            }
            labelRecord.setStatus(status);
            labelRecordApiService.update(labelRecord);
            return Result.success("上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.serverError(e.getMessage());
        }
    }

    @ApiOperation(value = "出仓")
    @PostMapping("/delivery")
    public Result delivery(@RequestHeader String Authorization, Principal principal, @RequestBody DeliveryDto dto){
        LgmnUserInfo lgmnUserInfo = UserUtil.getCurrUser(principal);
        try {
            LabelRecordDto labelRecordDto = new LabelRecordDto();
            labelRecordDto.setLabelNum(dto.getOrderNo());
            List<LabelRecordEntity> labelRecordEntitys = labelRecordApiService.list(labelRecordDto);
            if (labelRecordEntitys.size() <= 0) {
                return Result.error(ResultEnum.DATA_NOT_EXISTS);
            }
            LabelRecordEntity labelRecord = labelRecordEntitys.get(0);
            Integer status = null;
            if (labelRecord.getStatus() == 8) {
                return Result.error(ResultEnum.DATA_PROHIBIT);
            }

            if (labelRecord.getStatus() == 2) {
                return Result.error(ResultEnum.OUTOF_WAREHOUSE_ERROR);
            }
            if (labelRecord.getStatus() != 1) {
                return Result.error(ResultEnum.NOT_IN_WAREHOUSE_ERROR);
            }
            status = 2;
            labelRecord.setOutUser(lgmnUserInfo.getId());
            labelRecord.setOutTime(new Timestamp(System.currentTimeMillis()));
            labelRecord.setDeliveryNum(dto.getDeliveryNum());
            labelRecord.setStatus(status);
            labelRecordApiService.update(labelRecord);
            addOrUpdateDeliveryList(labelRecord);
            return Result.success("上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.serverError(e.getMessage());
        }
    }

    @ApiOperation(value = "出货单号+客户名称")
    @PostMapping("/deliveryList")
    public Result getDeliveryCustomerList(){
        Result result = Result.success("获取出货单成功");
        try {
            DeliveryNoteDto dto = new DeliveryNoteDto();
            dto.setPrinted(0);
            dto.setDelFlag(0);
            List<DeliveryNoteEntity> list = deliveryNoteApiService.list(dto);
            HM_DeliveryNoteVo vo=new HM_DeliveryNoteVo();
            List<HM_DeliveryNoteVo> voList=vo.getVoList(list,HM_DeliveryNoteVo.class);
            result.setData(voList);
        } catch (Exception e) {
            e.printStackTrace();
            result = Result.serverError("获取出货单失败");
        }finally {
            return result;
        }
    }

    @ApiOperation(value = "获取基本信息")
    @PostMapping("/getPersonInfo")
    public Result getPersonInfo (@RequestHeader String Authorization, Principal principal) {
        LgmnUserInfo lgmnUserInfo = UserUtil.getCurrUser(principal);
        try {
            return Result.success(lgmnUserInfo);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    private void addOrUpdateDeliveryList(LabelRecordEntity labelRecord){
        DeliveryNoteEntity deliveryNoteEntity = deliveryNoteApiService.getDeliverByNum(labelRecord.getDeliveryNum());
        YjOrderEntity orderEntity = orderApiService.getById(labelRecord.getOrderId());
        if(deliveryNoteEntity!=null){
            DeliveryListDto deliveryListDto = new DeliveryListDto();
            deliveryListDto.setDeliveryId(deliveryNoteEntity.getId());
            deliveryListDto.setClientId(deliveryNoteEntity.getCustomerId());
            deliveryListDto.setName(orderEntity.getName());
            deliveryListDto.setSpecs(orderEntity.getSpecs());
            deliveryListDto.setWidth(orderEntity.getWidth());
            deliveryListDto.setColor(orderEntity.getColor());
            DeliveryListEntity deliveryListEntity = deliveryListApiService.getByDto(deliveryListDto);
            if(deliveryListEntity!=null){
                int deliveryListQuantity = deliveryListEntity.getQuantity();
                deliveryListQuantity=deliveryListQuantity+labelRecord.getQuantity();

                int packageQuantity = deliveryListEntity.getPackageQuantity();
                packageQuantity=packageQuantity+1;

                deliveryListEntity.setQuantity(deliveryListQuantity);
                deliveryListEntity.setPackageQuantity(packageQuantity);
                deliveryListEntity.setScanQuantity(packageQuantity);
                deliveryListApiService.update(deliveryListEntity);
            }else{
                deliveryListEntity = new DeliveryListEntity();
                deliveryListEntity.setClientId(deliveryNoteEntity.getCustomerId());
                deliveryListEntity.setClientName(deliveryNoteEntity.getCustomer());
                deliveryListEntity.setPackageQuantity(1);
                deliveryListEntity.setQuantity(labelRecord.getQuantity());
                deliveryListEntity.setColor(orderEntity.getColor());
                deliveryListEntity.setSpecs(orderEntity.getSpecs());
                deliveryListEntity.setWidth(orderEntity.getWidth());
                deliveryListEntity.setDeliveryId(deliveryNoteEntity.getId());
                deliveryListEntity.setScanQuantity(1);
                deliveryListEntity.setRequirement(orderEntity.getRequirement());
                deliveryListEntity.setNumber(orderEntity.getNumber());
                deliveryListEntity.setName(orderEntity.getName());
                deliveryListApiService.add(deliveryListEntity);
            }
        }
        labelRecordApiService.update(labelRecord);

    }

}
