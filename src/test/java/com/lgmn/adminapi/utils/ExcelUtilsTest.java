package com.lgmn.adminapi.utils;

import com.lgmn.umaservices.basic.entity.YjOrderEntity;
import org.junit.Test;

import java.sql.Timestamp;

public class ExcelUtilsTest {

    @Test
    public void exportLabelExcel() {
    }

    @Test
    public void exportOrderExcel() {
        String tempPath="C:\\Users\\Lonel\\Desktop\\exportTest\\order_temp.xlsx";
        String exportPath="C:\\Users\\Lonel\\Desktop\\exportTest\\";
        YjOrderEntity yjOrderEntity = new YjOrderEntity();
        yjOrderEntity.setClientName("test");
        yjOrderEntity.setOrderNum("23456745364576");
        yjOrderEntity.setNumber("123456");
        yjOrderEntity.setName("test");
        yjOrderEntity.setColor("345");
        yjOrderEntity.setSpecs("23456");
        yjOrderEntity.setWidth("2345");
        yjOrderEntity.setDeliveryDate(new Timestamp(System.currentTimeMillis()));
        yjOrderEntity.setRemark("3456");
        yjOrderEntity.setCreateUser("23456");
        yjOrderEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
       String p= ExcelUtils.exportOrderExcel(tempPath,exportPath,yjOrderEntity);
        System.out.println(p);
    }
}