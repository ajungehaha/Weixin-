package com.imooc.sell.utils;

import com.imooc.sell.vo.ResultVO;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/4 19:41
 * @Version 1.0
 */
public class ResultVOUtils {
    //在这里写object可以解耦
    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);

    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
