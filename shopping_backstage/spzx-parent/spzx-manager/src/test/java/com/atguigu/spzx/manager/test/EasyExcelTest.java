package com.atguigu.spzx.manager.test;

import com.alibaba.excel.EasyExcel;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;

import java.util.List;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/10 16:04
 * @version: 1.0
 */
public class EasyExcelTest {
    public static void main(String[] args) {
        read();
    }
    //读操作
    public static void read()
    {
        //1 定义读取excel文件位置
        String fileName="D://cmy//learning//01.xlsx";
        ExcelListener excelListener=new ExcelListener();
        EasyExcel.read(fileName, CategoryExcelVo.class,excelListener).sheet().doRead();
        List<CategoryExcelVo> data=excelListener.getData();
        System.out.println(data);
    }
//    public static void write()
//    {
//
//    }
}
