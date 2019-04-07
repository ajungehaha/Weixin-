package com.imooc.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 买家商品返回的最终的对象
 * @Author: 阿俊哥
 * @Date: 2019/3/3 15:47
 * @Version 1.0
 */
@Data
public class ResultVO<Object>implements Serializable {


    private static final long serialVersionUID = -3413077651933331651L;
    /*错误码*/
   private Integer code;
    /*返回的消息*/
   private String msg;
    /*具体信息*/
   private Object data;

}
