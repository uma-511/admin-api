package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.yjProduct.YjProductSaveDto;
import com.lgmn.adminapi.dto.yjProduct.YjProductSelectDto;
import com.lgmn.adminapi.dto.yjProduct.YjProductUpdateDto;
import com.lgmn.adminapi.service.YjProductApiService;
import com.lgmn.adminapi.vo.YJProductVo;
import com.lgmn.common.domain.LgmnPage;
import com.lgmn.common.domain.LgmnUserInfo;
import com.lgmn.common.result.Result;
import com.lgmn.common.utils.ObjectTransfer;
import com.lgmn.umaservices.basic.dto.YjProductDto;
import com.lgmn.umaservices.basic.entity.YjProductEntity;
import com.lgmn.userservices.basic.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;


@RestController
@RequestMapping("/yjProductApi")
public class YjProductController {

    @Autowired
    YjProductApiService service;

    @PostMapping("/page")
    public Result page (@RequestBody YjProductDto dto) {
        try {
            LgmnPage<YjProductEntity> page = service.page(dto);
            return Result.success(page);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/update")
    public Result update (@RequestBody YjProductUpdateDto updateDto) {
        try {
            YjProductEntity entity = service.getById(updateDto.getId());
            ObjectTransfer.transValue(updateDto, entity);
            service.update(entity);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/add")
    public Result add (@RequestHeader String Authorization,@RequestBody YjProductSaveDto saveDto, Principal principal) {
        LgmnUserInfo lgmnUserEntity = UserUtil.getCurrUser(principal);
        try {
            YjProductEntity entity = new YjProductEntity();
            ObjectTransfer.transValue(saveDto, entity);
            entity.setCreateUserId(lgmnUserEntity.getId());
            entity.setCreateUserName(lgmnUserEntity.getNikeName());
            entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
            entity.setStatus(1);
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
        YjProductEntity entity = service.getById(id);
        return Result.success(entity);
    }

    @PostMapping("/productSelectList")
    public Result productSelectList(@RequestBody YjProductSelectDto yjProductSelectDto){
        Result result = Result.success("获取编号成功");
        List<YjProductEntity> list = null;
        try {
            YjProductDto dto = new YjProductDto();
            ObjectTransfer.transValue(yjProductSelectDto,dto);
            dto.setStatus(1);
            list = service.list(dto);
            YJProductVo yjProductVo = new YJProductVo();
            List<YJProductVo> voList = yjProductVo.getVoList(list,YJProductVo.class);
            result.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            result = Result.serverError("获取编号失败");
        }finally {
            return result;
        }
    }
}