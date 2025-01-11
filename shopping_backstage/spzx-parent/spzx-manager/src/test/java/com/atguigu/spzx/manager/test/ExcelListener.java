package com.atguigu.spzx.manager.test;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/10 16:05
 * @version: 1.0
 */
public class ExcelListener<T> extends AnalysisEventListener<T> {
    private List<T> data=new ArrayList<>();
    //读取excel内容
    //从第二行开始读取，把每行读取的内容封装到对象里面
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        data.add(t);
    }
    public List<T> getData()
    {
        return data;
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
