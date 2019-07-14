package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.LgmnUser.QueryLgmnUserDto;
import com.lgmn.adminapi.dto.LgmnUser.SaveLgmnUserDto;
import com.lgmn.adminapi.dto.LgmnUser.UpdateLgmnUserDto;
import com.lgmn.adminapi.service.LgmnUserApiService;
import com.lgmn.adminapi.vo.UserVo;
import com.lgmn.common.domain.LgmnPage;
import com.lgmn.common.result.Result;
import com.lgmn.common.utils.ObjectTransfer;
import com.lgmn.userservices.basic.dto.LgmnUserDto;
import com.lgmn.userservices.basic.entity.LgmnUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/user")
public class LgmnUserController {

    @Autowired
    LgmnUserApiService service;

    @PostMapping("/page")
    public Result page (@RequestBody LgmnUserDto dto) {
        try {
            LgmnPage<LgmnUserEntity> page = service.page(dto);
            return Result.success(page);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/update")
    public Result update (@RequestBody UpdateLgmnUserDto updateDto) {
        try {
            LgmnUserEntity entity = service.getById(updateDto.getId());
            ObjectTransfer.transValue(updateDto, entity);
            service.update(entity);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/add")
    public Result add (@RequestBody SaveLgmnUserDto saveDto) {
        try {
            LgmnUserEntity entity = new LgmnUserEntity();
            ObjectTransfer.transValue(saveDto, entity);
            service.add(entity);
            return Result.success("添加成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/delete/{id}")
    public Result delete (@PathVariable("id") String id) {
        service.deleteById(id);
        return Result.success("删除成功");
    }

    @PostMapping("/detail/{id}")
    public Result detail (@PathVariable("id") String id) {
        LgmnUserEntity entity = service.getById(id);
        UserVo vo=new UserVo();
        try {
            List<String> ignoreFields=new ArrayList<>();
            ignoreFields.add("avatar");
            ObjectTransfer.transValue(entity,vo,ignoreFields);
            vo.getAvatar().add(entity.getAvatar());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success(vo);
    }


    @PostMapping("/excel")
    public void excel(@RequestBody QueryLgmnUserDto queryLgmnUserDto, HttpServletResponse response) throws Exception {
//        List<List<Object>> rows = new ArrayList();
//        LgmnUserDto userDto=new LgmnUserDto();
//        ObjectTransfer.transValue(queryLgmnUserDto,userDto);
//        LgmnPage<LgmnUserEntity> page = service.page(userDto);
//        for (LgmnUserEntity entity : page.getList()) {
//            List<Object> row = new ArrayList();
//                        row.add(entity.getId());
//                        row.add(entity.getAvatar());
//                        row.add(entity.getAccount());
//                        row.add(entity.getNikeName());
//                        row.add(entity.getPassword());
//                        row.add(entity.getSalt());
//                        row.add(entity.getRegTime());
//                        row.add(entity.getLastLoginTime());
//                        row.add(entity.getUserType());
//                    }
//        ExcelData data = new ExcelData();
//        data.setName("APP用户数据导出");
//        List<String> titles = new ArrayList();
//                            titles.add("id");
//                                titles.add("头像");
//                                titles.add("登录账号");
//                                titles.add("昵称");
//                                titles.add("密码");
//                                titles.add("密码盐");
//                                titles.add("注册时间");
//                                titles.add("最后一次登录时间");
//                                titles.add("用户类型 0：app用户 1：销售员 3：后台用户");
//
//        data.setTitles(titles);
//        data.setRows(rows);
//        ExportExcelUtils.exportExcel(response, "数据导出.xlsx", data);
    }

}