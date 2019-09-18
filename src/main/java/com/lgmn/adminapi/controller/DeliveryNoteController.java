package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.DeliveryNote.SaveDeliveryNoteDto;
import com.lgmn.adminapi.dto.DeliveryNote.UpdateDeliveryNoteDto;
import com.lgmn.adminapi.service.CustomerApiService;
import com.lgmn.adminapi.service.DeliveryListApiService;
import com.lgmn.adminapi.service.DeliveryNoteApiService;
import com.lgmn.adminapi.service.UserService;
import com.lgmn.adminapi.utils.DateUtils;
import com.lgmn.adminapi.utils.ExcelUtils;
import com.lgmn.adminapi.utils.MoneyUtils;
import com.lgmn.adminapi.vo.DeliveryNoteDetailVo;
import com.lgmn.common.domain.LgmnPage;
import com.lgmn.common.domain.LgmnUserInfo;
import com.lgmn.common.result.Result;
import com.lgmn.common.result.ResultEnum;
import com.lgmn.common.utils.ObjectTransfer;
import com.lgmn.umaservices.basic.dto.DeliveryListDto;
import com.lgmn.umaservices.basic.dto.DeliveryNoteDto;
import com.lgmn.umaservices.basic.entity.CustomerEntity;
import com.lgmn.umaservices.basic.entity.DeliveryListEntity;
import com.lgmn.umaservices.basic.entity.DeliveryNoteEntity;
import com.lgmn.userservices.basic.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.security.Principal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;


@RestController
@RequestMapping("/deliveryNoteApi")
public class DeliveryNoteController {

    @Autowired
    DeliveryNoteApiService service;

    @Autowired
    DeliveryListApiService deliveryListApiService;

    @Autowired
    CustomerApiService customerApiService;

    @Autowired
    UserService userService;

    @Value("${delivery.tempPath}")
    private String tempPath;

    @Value("${delivery.exportPath}")
    private String exportPath;

    @PostMapping("/page")
    public Result page (@RequestBody DeliveryNoteDto dto) {
        try {
            dto.setDelFlag(0);
            LgmnPage<DeliveryNoteEntity> page = service.page(dto);
            return Result.success(page);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/remindpage")
    public Result remindpage (@RequestBody DeliveryNoteDto dto) {
        try {
            dto.setHadPaid(0);
            dto.setLtPaymentTime(new Timestamp(System.currentTimeMillis()));
            dto.setDelFlag(0);
            LgmnPage<DeliveryNoteEntity> page = service.page(dto);
            return Result.success(page);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/update")
    public Result update ( @RequestHeader String Authorization,@RequestBody UpdateDeliveryNoteDto updateDto, Principal principal) {
        try {
            DeliveryNoteEntity entity = service.getById(updateDto.getId());
            ObjectTransfer.transValue(updateDto, entity);
            service.update(entity);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/add")
    public Result add ( @RequestHeader String Authorization,@RequestBody SaveDeliveryNoteDto saveDto, Principal principal) {
        LgmnUserInfo lgmnUserEntity = UserUtil.getCurrUser(principal);
        try {
            DeliveryNoteDto dto = new DeliveryNoteDto();
            Date date=new Date(System.currentTimeMillis());

            SimpleDateFormat number_sdf = new SimpleDateFormat("yyyyMM");
            String ss = DateUtils.monthFirstDate();
            String ts = DateUtils.monthLastDate();

            Timestamp start_ts=Timestamp.valueOf(ss);
            Timestamp end_ts=Timestamp.valueOf(ts);

            dto.setStartCreateTime(start_ts);
            dto.setEndCreateTime(end_ts);

            List<DeliveryNoteEntity> list = service.list(dto);

            String number="0000"+(list.size()+1);

            number= number_sdf.format(date) + number.substring(number.length()-4);

            DeliveryNoteEntity entity = new DeliveryNoteEntity();
            ObjectTransfer.transValue(saveDto, entity);
            entity.setDeliveryNum(number);
            entity.setCreateUser(lgmnUserEntity.getNikeName());
            entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
            entity.setDelFlag(0);
            entity.setPrinted(0);
            entity.setHadPaid(0);
            service.add(entity);
            return Result.success("添加成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/delete/{id}")
    public Result delete (@PathVariable("id") Integer id) {
        DeliveryNoteEntity entity = service.getById(id);
        entity.setDelFlag(1);
        service.update(entity);
        return Result.success("删除成功");
    }

    @PostMapping("/detail/{id}")
    public Result detail (@PathVariable("id") Integer id) {

        Result result = Result.success("获取出货单详情成功");
        try {
            DeliveryNoteDetailVo vo = getDeliveryNoteDetailVo(id);
            result.setData(vo);
        } catch (Exception e) {
            e.printStackTrace();
            result = Result.serverError("获取出货单详情失败");
        }finally {
            return result;
        }
    }

    private DeliveryNoteDetailVo getDeliveryNoteDetailVo(@PathVariable("id") Integer id) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DeliveryNoteDetailVo vo = new DeliveryNoteDetailVo();
        DeliveryNoteEntity entity = service.getById(id);

        DeliveryListDto dto = new DeliveryListDto();
        dto.setDeliveryId(entity.getId());
        List<DeliveryListEntity> list = deliveryListApiService.list(dto);
        CustomerEntity customerEntity = customerApiService.getById(entity.getCustomerId());
        vo.setDeliveryNote(entity);
        vo.setCustomer(customerEntity);
        vo.setDeliveryList(list);

        BigDecimal totalPrice=new BigDecimal("0");
        String totalPriceChinese="";
        int totalQuantity=0;
        for (DeliveryListEntity item: vo.getDeliveryList()) {
            totalPrice = totalPrice.add(item.getTotalPrice());
            totalQuantity = totalQuantity+item.getScanQuantity();
        }
        vo.setTotalPrice(totalPrice.setScale(2, RoundingMode.HALF_UP));
        totalPriceChinese = MoneyUtils.change(totalPrice.doubleValue());
        vo.setTotalQuantity(totalQuantity);
        vo.setTotalPriceChinese(totalPriceChinese);
        String createTime = sdf.format(entity.getCreateTime());
        vo.setCreateDate(createTime);
        return vo;
    }


    @PostMapping("/excel/{id}")
    public void excel(@PathVariable("id") Integer id, HttpServletResponse response) throws Exception {
        DeliveryNoteDetailVo vo = getDeliveryNoteDetailVo(id);
        String filePath=ExcelUtils.exportLabelExcel(tempPath,exportPath,vo);
        File file=new File(filePath);
        // 如果文件名存在，则进行下载
        if (file.exists()) {

            // 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));

            // 实现文件下载
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                System.out.println("Download the song successfully!");
            }
            catch (Exception e) {
                System.out.println("Download the song failed!");
            }
            finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

//        ExportExcelUtils.exportExcel(response, "数据导出.xlsx", data);
    }



    @PostMapping("/payConfirm/{id}")
    public Result payConfirm(@RequestHeader String Authorization,@PathVariable("id") Integer id, Principal principal){
        LgmnUserInfo lgmnUserEntity = UserUtil.getCurrUser(principal);
        DeliveryNoteEntity entity = service.getById(id);
        Result result = Result.error(ResultEnum.DATA_NOT_EXISTS);
        try {
            if(entity!=null){
                entity.setPayConfirmer(lgmnUserEntity.getNikeName());
                entity.setHadPaid(1);
                service.update(entity);
                result = Result.success("确认回款成功");
            }
        } catch (Exception e) {
            result = Result.serverError(e.getMessage());
        }finally {
            return result;
        }
    }

}