package com.imooc.sell.enums;

import lombok.Getter;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/6 20:00
 * @Version 1.0
 */
@Getter
public enum ResultEnum {
    ORDER_NOT_PAY(1,"订单未支付"),
    ORDER_PAY_STATUS_ERROR(2,"订单支付状态错误"),
    PARAM_ERROR(3,"参数不正确"),
    BUYCAR_IS_NULL(4,"购物车不能为空"),
    PRODUCT_NOT_EXIT(10,"商品不存在"),
    PRODUCT_NOT_ENOUGH(11,"商品库存不够"),
    ORDER_NOT_EXIT(12,"订单不存在"),
    ORDER_DETAILS_NOT_EXIT(13,"商品详情订单列表不存在"),
    ORDER_STATUS_ERROR(14,"订单状态错误"),
    ORDER_OPENID_ERROR(15,"订单所有者不一致"),
    ORDER_UPDATE_FAIL(16,"订单更新失败"),
    WXCHAT_MP_ERROR(17,"微信公众扎行号方面错误"),
    WXCHAT_MONEY_NOT_SAME(18,"支付金额和系统金额不一致"),
    SUCCESS(19,"操作成功"),
    ERROR(21,"操作失败"),
    WXCAHT_OPEN_ERROR(21,"微信网页扫码登录失败"),
    PRODUCT_STATUS_ERROR(20,"商品状态错误"),
    LOGIN_ERROR(22,"登录错误，用户不存在"),
    LOGOUT_SUCCESS(23,"登出成功"),
    NOT_LOGIN(24,"请登录！"),
    ;

    private Integer code;

    private String msg;

    private String url;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
