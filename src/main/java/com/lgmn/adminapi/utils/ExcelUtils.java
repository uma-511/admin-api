package com.lgmn.adminapi.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtils {
//    public static String exportLabelExcel(String templatePath,String exportPath, DeliveryNoteDetailVo printPojo) {
//
//        File templateFile=new File(templatePath);
//        if(!templateFile.exists()){
//            System.out.println("找不到模板文件");
//            try {
//                throw new FileNotFoundException("找不到模板文件");
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//        // 加载模板
//        TemplateExportParams params = new TemplateExportParams(templatePath);
//        // 生成workbook 并导出
//        Workbook workbook = null;
//        try {
//            workbook = ExcelExportUtil.exportExcel(params, objectToMap(printPojo));
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        String savePath=exportPath;
//        File savefile = new File(savePath);
//        if (!savefile.exists()) {
//            boolean result = savefile.mkdirs();
//            System.out.println("目录不存在,进行创建,创建" + (result ? "成功!" : "失败！"));
//        }
//        String filePath = savePath + printPojo.getCustomer().getName()+"_"+printPojo.getCreateDate() + ".xls";
//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream(filePath);
//            workbook.write(fos);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                fos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return filePath;
//    }
//
//    public static String exportOrderExcel(String templatePath, String exportPath, YjOrderEntity printPojo) {
//
//        File templateFile=new File(templatePath);
//        if(!templateFile.exists()){
//            System.out.println("找不到模板文件");
//            try {
//                throw new FileNotFoundException("找不到模板文件");
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//        // 加载模板
//        TemplateExportParams params = new TemplateExportParams(templatePath);
//        // 生成workbook 并导出
//        Workbook workbook = null;
//        try {
//            workbook = ExcelExportUtil.exportExcel(params, objectToMap(printPojo));
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        String savePath=exportPath;
//        File savefile = new File(savePath);
//        if (!savefile.exists()) {
//            boolean result = savefile.mkdirs();
//            System.out.println("目录不存在,进行创建,创建" + (result ? "成功!" : "失败！"));
//        }
//        String filePath = savePath + printPojo.getClientName()+ "_" + printPojo.getOrderNum() + ".xls";
//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream(filePath);
//            workbook.write(fos);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                fos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return filePath;
//    }

    /**
     * 对象转换为Map<String, Object>的工具类
     *
     * @param obj
     *            要转换的对象
     * @return map
     * @throws IllegalAccessException
     */
    private static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>(16);
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            /*
             * Returns the value of the field represented by this {@code Field}, on the
             * specified object. The value is automatically wrapped in an object if it
             * has a primitive type.
             * 注:返回对象该该属性的属性值，如果该属性的基本类型，那么自动转换为包装类
             */
            Object value = field.get(obj);
            map.put(fieldName, value);
        }
        return map;
    }
}