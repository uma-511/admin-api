package com.lgmn.adminapi.controller;

import com.lgmn.adminapi.dto.LabelFormat.SaveLabelFormatDto;
import com.lgmn.adminapi.dto.LabelFormat.UpdateLabelFormatDto;
import com.lgmn.adminapi.service.LabelFormatApiService;
import com.lgmn.adminapi.vo.LabelFormatListVo;
import com.lgmn.adminapi.vo.LabelFormatSelectVo;
import com.lgmn.adminapi.vo.UploadLabelModelVo;
import com.lgmn.common.domain.LgmnPage;
import com.lgmn.common.result.Result;
import com.lgmn.common.result.ResultEnum;
import com.lgmn.common.utils.ObjectTransfer;
import com.lgmn.umaservices.basic.dto.LabelFormatDto;
import com.lgmn.umaservices.basic.entity.LabelFormatEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/labelFormatApi")
public class LabelFormatController {

    @Autowired
    LabelFormatApiService service;

    @Value("${label.model.path}")
    private String labelModelPath;

    @PostMapping("/page")
    public Result page (@RequestBody LabelFormatDto dto) {
        try {
            dto.setDelFlag(0);
            LgmnPage<LabelFormatEntity> page = service.page(dto);
            return Result.success(page);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/update")
    public Result update (@RequestBody UpdateLabelFormatDto updateDto) {
        try {
            LabelFormatEntity entity = service.getById(updateDto.getId());
            ObjectTransfer.transValue(updateDto, entity);
            service.update(entity);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/add")
    public Result add (@RequestBody SaveLabelFormatDto saveDto) {
        try {
            LabelFormatEntity entity = new LabelFormatEntity();
            ObjectTransfer.transValue(saveDto, entity);
            service.add(entity);
            return Result.success("添加成功");
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/delete/{id}")
    public Result delete (@PathVariable("id") int id) {
        LabelFormatEntity entity = service.getById(id);
        entity.setDelFlag(1);
        service.update(entity);
        return Result.success("删除成功");
    }

    @PostMapping("/detail/{id}")
    public Result detail (@PathVariable("id") Integer id) {
        LabelFormatEntity entity = service.getById(id);
        return Result.success(entity);
    }

    @PostMapping("/getLabelFormatAllList")
    public Result getLabelFormatAllList () {
        try {
            LabelFormatDto labelFormatDto = new LabelFormatDto();
            labelFormatDto.setDelFlag(0);
            List<LabelFormatEntity> list = service.list(labelFormatDto);
            List<LabelFormatListVo> labelFormatListVos = new LabelFormatListVo().getVoList(list, LabelFormatListVo.class);
            return Result.success(labelFormatListVos);
        } catch (Exception e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/uploadLabelModel")
    public Result uploadLabelModel (@RequestBody MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error(ResultEnum.DATA_NOT_EXISTS);
        }

        String fileName = file.getOriginalFilename();
        String path = labelModelPath + System.currentTimeMillis() + fileName;
        UploadLabelModelVo uploadLabelModelVo = new UploadLabelModelVo();
        uploadLabelModelVo.setPath(path);
        File dest = new File(path);
        try {
            file.transferTo(dest);
            return Result.success(uploadLabelModelVo);
        } catch (IOException e) {
            return Result.serverError(e.getMessage());
        }
    }

    @PostMapping("/labelformatSelectList")
    public Result labelformatSelectList(){
        Result result = Result.success("获取标签模板成功");
        try {
            List<LabelFormatEntity> all = service.all();
            LabelFormatSelectVo labelFormatSelectVo = new LabelFormatSelectVo();
            List<LabelFormatSelectVo> list = labelFormatSelectVo.getVoList(all,LabelFormatSelectVo.class);
            result.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            result = Result.serverError("获取标签模板失败");
        }finally {
            return result;
        }
    }

//    @PostMapping("/excel")
//    public void excel(@RequestBody SwcyAdDto swcyAdDto, HttpServletResponse response) throws Exception {
//        List<List<Object>> rows = new ArrayList();
//        LgmnPage<LabelFormatEntity> page = service.page(swcyAdDto);
//        for (LabelFormatEntity entity : page.getList()) {
//            List<Object> row = new ArrayList();
//                        row.add(entity.getId());
//                        row.add(entity.getName());
//                    }
//        ExcelData data = new ExcelData();
//        data.setName("APP用户数据导出");
//        List<String> titles = new ArrayList();
//                            titles.add("id");
//                                titles.add("name");
//
//        data.setTitles(titles);
//        data.setRows(rows);
//        ExportExcelUtils.exportExcel(response, "数据导出.xlsx", data);
//    }

}