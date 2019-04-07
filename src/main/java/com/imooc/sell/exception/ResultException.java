package com.imooc.sell.exception;

import com.imooc.sell.enums.ResultEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**返回异常类
 * @Author: 阿俊哥
 * @Date: 2019/3/6 19:52
 * @Version 1.0
 */
@Getter
@Setter
@ToString
public class ResultException extends RuntimeException {

    private Integer code;


    public ResultException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());

        this.code = resultEnum.getCode();
    }

    public ResultException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
