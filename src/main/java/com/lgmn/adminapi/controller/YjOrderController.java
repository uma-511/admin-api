package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.yjOrder.YjOrderSaveDto;
import com.lgmn.adminapi.dto.yjOrder.YjOrderSearchDto;
import com.lgmn.adminapi.dto.yjOrder.YjOrderUpdateDto;
import com.lgmn.adminapi.service.YjOrderApiService;
import com.lgmn.adminapi.utils.ExcelUtils;
import com.lgmn.common.domain.LgmnPage;
import com.lgmn.common.domain.LgmnUserInfo;
import com.lgmn.common.result.Result;
import com.lgmn.common.utils.ObjectTransfer;
import com.lgmn.umaservices.basic.dto.YjOrderDto;
import com.lgmn.umaservices.basic.entity.YjOrderEntity;
import com.lgmn.userservices.basic.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.security.Principal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("/yjOrderApi")
public class YjOrderController {

    @Autowired
    YjOrderApiService service;

    @Value("${order.tempPath}")
    private String tempPath;

    @Value("${order.exportPath}")
    private String exportPath;


    @PostMapping("/page")
    public Result page (@RequestBody YjOrderSearchDto dto) {
        try {
            dto.setDelFlag(0);
            YjOrderDto yjOrderDto = new YjOrderDto();
            ObjectTransfer.transValue(dto,yjOrderDto);
            LgmnPage<YjOrderEntity> page = service.page(yjOrderDto);
            return Result.success(page);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/update")
    public Result update (@RequestHeader String Authorization,@RequestBody YjOrderUpdateDto updateDto, Principal principal) {
        try {
            YjOrderEntity entity = service.getById(updateDto.getId());
            ObjectTransfer.transValue(updateDto, entity);
            service.update(entity);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/add")
    public Result add (@RequestHeader String Authorization,@RequestBody YjOrderSaveDto saveDto, Principal principal) {
        LgmnUserInfo lgmnUserEntity = UserUtil.getCurrUser(principal);
        try {
            Date date=new Date(System.currentTimeMillis());

            Calendar calendar=new GregorianCalendar();
            calendar.setTime(new Date(System.currentTimeMillis()));
            calendar.add(Calendar.DATE,1);



            SimpleDateFormat start_sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
            SimpleDateFormat end_sdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
            SimpleDateFormat number_sdf = new SimpleDateFormat("yyMMdd");

            YjOrderEntity entity = new YjOrderEntity();
            ObjectTransfer.transValue(saveDto, entity);

            YjOrderDto dto=new YjOrderDto();
            String ss=start_sdf.format(date);
            String ts=end_sdf.format(calendar.getTime());

            Timestamp start_ts=Timestamp.valueOf(ss);
            Timestamp end_ts=Timestamp.valueOf(ts);
            dto.setStartCreateTime(start_ts);
            dto.setEndCreateTime(end_ts);

            List<YjOrderEntity> dateOrder = service.list(dto);
            String number="0000"+(dateOrder.size()+1);
            number= number_sdf.format(date) + number.substring(number.length()-3);

            entity.setCreateUserId(lgmnUserEntity.getId());
            entity.setCreateUser(lgmnUserEntity.getNikeName());
            entity.setOrderNum(number);
            entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
            entity.setDelFlag(0);
            service.add(entity);
            return Result.success("添加成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/delete/{id}")
    public Result delete (@RequestHeader String Authorization,@PathVariable("id") Integer id) {
        YjOrderEntity entity = service.getById(id);
        entity.setDelFlag(1);
        service.update(entity);
        return Result.success("删除成功");
    }

    @PostMapping("/detail/{id}")
    public Result detail (@PathVariable("id") Integer id) {
        YjOrderEntity entity = service.getById(id);
        return Result.success(entity);
    }

    @PostMapping("/exportData/{id}")
    public void exportData(@PathVariable("id") Integer id, HttpServletResponse response) throws Exception {
        YjOrderEntity entity = service.getById(id);
        String filePath = ExcelUtils.exportOrderExcel(tempPath,exportPath, entity);
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
    }


    private static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>(16);
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            map.put(fieldName, value);
        }
        return map;
    }
}