package com.atguigu.spzx.common.exception;

import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import lombok.Data;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/7 20:17
 * @version: 1.0
 */
@Data
public class GuiguException extends RuntimeException{
    private Integer code;
    private String message;

    private ResultCodeEnum resultCodeEnum;

    public GuiguException(ResultCodeEnum resultCodeEnum)
    {
        this.resultCodeEnum=resultCodeEnum;
        this.code=resultCodeEnum.getCode();
        this.message=resultCodeEnum.getMessage();
    }
}
