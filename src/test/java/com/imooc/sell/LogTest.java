package com.imooc.sell;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.PrivateKey;

/**
 * @Author: 阿俊哥
 * @Date: 2019/2/28 18:46
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j//可以不用引入变量自动匹配类
public class LogTest {

//    //要使用日志必须引入一个变量logger       写当前类
//    private final Logger logger = LoggerFactory.getLogger(LogTest.class);

    @Test
    public void testLog()
    {

        log.debug("debug......name: ");
        log.info("info....");
        log.error("error......");
    }
}
