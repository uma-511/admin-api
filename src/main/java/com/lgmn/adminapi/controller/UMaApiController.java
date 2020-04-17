package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.uma.*;
import com.lgmn.adminapi.service.*;
import com.lgmn.adminapi.vo.HM_DeliveryNoteVo;
import com.lgmn.adminapi.vo.HM_RefundNoteVo;
import com.lgmn.adminapi.vo.ScanVo;
import com.lgmn.common.domain.LgmnUserInfo;
import com.lgmn.common.result.Result;
import com.lgmn.common.result.ResultEnum;
import com.lgmn.umaservices.basic.dto.DeliveryListDto;
import com.lgmn.umaservices.basic.dto.DeliveryNoteDto;
import com.lgmn.umaservices.basic.dto.LabelRecordDto;
import com.lgmn.umaservices.basic.dto.RefundNoteDto;
import com.lgmn.umaservices.basic.entity.*;
import com.lgmn.userservices.basic.util.UserUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
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
    private RefundNoteApiService refundNoteApiService;

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
                return Result.getResult(null,ResultEnum.DATA_NOT_EXISTS.getCode(),"找不到标签记录");
            }
            LabelRecordEntity labelRecord = labelRecordEntitys.get(0);
            Integer status = null;
            if (labelRecord.getStatus() == 8) {
                return Result.getResult(null,ResultEnum.DATA_PROHIBIT.getCode(),"标签已作废");
            }

            int labelStatus = labelRecord.getStatus();
            String message = "上传成功";
            // 车间入仓
            if ("storage_of_workshop".equals(postLabelRecordDto.getType())) {
                if (labelStatus != 0) {
                    return getResultByLabelStatus(labelStatus);
                }
                status = 1;
                labelRecord.setInUser(lgmnUserInfo.getId());
                labelRecord.setInTime(new Timestamp(System.currentTimeMillis()));
                message = "入仓成功";
                // 车间出仓
            } else if ("workshop_depot".equals(postLabelRecordDto.getType()))  {
                if(labelStatus != 1){
                    return getResultByLabelStatus(labelStatus);
                }
                status = 2;
                labelRecord.setOutUser(lgmnUserInfo.getId());
                labelRecord.setOutTime(new Timestamp(System.currentTimeMillis()));
                message = "出仓成功";
                // 返仓
            } else {
                if (labelRecord.getStatus() != 2) {
                    return getResultByLabelStatus(labelStatus);
                }
                status = 1;
                labelRecord.setInUser(lgmnUserInfo.getId());
                labelRecord.setInTime(new Timestamp(System.currentTimeMillis()));
                updateDeliveryList(labelRecord);
                message = "返仓成功";
            }
            labelRecord.setStatus(status);
            labelRecordApiService.update(labelRecord);

            ScanVo scanVo = getScanResultData(labelRecord);
            Result result =  Result.success(message);
            result.setData(scanVo);
            return result;
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
                return Result.getResult(null,ResultEnum.DATA_NOT_EXISTS.getCode(),"找不到标签记录");
            }
            LabelRecordEntity labelRecord = labelRecordEntitys.get(0);
            int labelStatus = labelRecord.getStatus();

            if(labelStatus != 1){
                return getResultByLabelStatus(labelStatus);
            }

            Integer status = 2;
            labelRecord.setOutUser(lgmnUserInfo.getId());
            labelRecord.setOutTime(new Timestamp(System.currentTimeMillis()));
            labelRecord.setDeliveryNum(dto.getDeliveryNum());
            labelRecord.setStatus(status);
            labelRecordApiService.update(labelRecord);
            addOrUpdateDeliveryList(labelRecord);

            ScanVo scanVo = getScanResultData(labelRecord);
            Result result =  Result.success("出仓成功");
            result.setData(scanVo);

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.serverError(e.getMessage());
        }
    }

    @ApiOperation(value = "退货")
    @PostMapping("/refund")
    public Result refund(@RequestHeader String Authorization, Principal principal, @RequestBody RefundDto dto){
        LgmnUserInfo lgmnUserInfo = UserUtil.getCurrUser(principal);
        try {
            LabelRecordDto labelRecordDto = new LabelRecordDto();
            labelRecordDto.setLabelNum(dto.getOrderNo());
            List<LabelRecordEntity> labelRecordEntitys = labelRecordApiService.list(labelRecordDto);
            if (labelRecordEntitys.size() <= 0) {
                return Result.getResult(null,ResultEnum.DATA_NOT_EXISTS.getCode(),"找不到标签记录");
            }
            LabelRecordEntity labelRecord = labelRecordEntitys.get(0);
            int labelStatus = labelRecord.getStatus();

            if(labelStatus != 2){
                return getResultByLabelStatus(labelStatus);
            }

            Integer status = 1;
            labelRecord.setRefundUser(lgmnUserInfo.getId());
            labelRecord.setRefundTime(new Timestamp(System.currentTimeMillis()));
            labelRecord.setRefundNum(dto.getRefundNum());
            labelRecord.setStatus(status);
            labelRecordApiService.update(labelRecord);
            addOrUpdateDeliveryList(labelRecord);

            ScanVo scanVo = getScanResultData(labelRecord);
            Result result =  Result.success("退货成功");
            result.setData(scanVo);

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.serverError(e.getMessage());
        }
    }

    private ScanVo getScanResultData(LabelRecordEntity labelRecord){
        ScanVo vo = new ScanVo();
        YjOrderEntity orderEntity = orderApiService.getById(labelRecord.getOrderId());

        BeanUtils.copyProperties(labelRecord,vo);
        BeanUtils.copyProperties(orderEntity,vo);

        vo.genHash();
        return vo;
    }

    private Result getResultByLabelStatus(int status){
        Result result;
        switch (status){
            case 8:
                result = Result.getResult(null,ResultEnum.DATA_PROHIBIT.getCode(),"标签已作废");
                break;
            case 2:
                result = Result.getResult(null,ResultEnum.OUTOF_WAREHOUSE_ERROR.getCode(),"标签已出仓");
                break;
            case 0:
                result = Result.getResult(null,ResultEnum.NOT_IN_WAREHOUSE_ERROR.getCode(),"便签未入仓，请先入仓");
                break;
            case 1:
                result = Result.getResult(null,ResultEnum.NOT_IN_WAREHOUSE_ERROR.getCode(),"标签已入仓");
                break;
            default:
                result = Result.serverError("未知状态");
                break;
        }
        return result;
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

    @ApiOperation(value = "退货单号+客户名称")
    @PostMapping("/refundList")
    public Result getRefundCustomerList(){
        Result result = Result.success("获取出货单成功");
        try {
            RefundNoteDto dto = new RefundNoteDto();
            dto.setPrinted(0);
            dto.setDelFlag(0);
            List<RefundNoteEntity> list = refundNoteApiService.list(dto);
            HM_RefundNoteVo vo=new HM_RefundNoteVo();
            List<HM_RefundNoteVo> voList=vo.getVoList(list,HM_RefundNoteVo.class);
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
//        labelRecordApiService.update(labelRecord);

    }

    private void updateDeliveryList(LabelRecordEntity labelRecord) {
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
                deliveryListQuantity = deliveryListQuantity - labelRecord.getQuantity();

                int packageQuantity = deliveryListEntity.getPackageQuantity();
                packageQuantity = packageQuantity - 1;

                if(packageQuantity > 0){
                    deliveryListEntity.setQuantity(deliveryListQuantity);
                    deliveryListEntity.setPackageQuantity(packageQuantity);
                    deliveryListEntity.setScanQuantity(packageQuantity);
                    deliveryListApiService.update(deliveryListEntity);
                }else{
                    deliveryListApiService.deleteById(deliveryListEntity.getId());
                }
            }
        }
    }

}
