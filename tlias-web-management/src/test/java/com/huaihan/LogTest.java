package com.huaihan;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {

    // 使用System.out.println控制台记录日志
    /*
    @Test
    public void testLog(){
        System.out.println(LocalDateTime.now() + " : 开始计算...");

        int sum = 0;
        int[] nums = {1, 5, 3, 2, 1, 4, 5, 4, 6, 7, 4, 34, 2, 23};
        for (int num : nums) {
            sum += num;
        }
        
        System.out.println("计算结果为: "+sum);
        System.out.println(LocalDateTime.now() + "结束计算...");
    }
    */


    // 使用Logback
    // 手动定义日志记录对象，固定格式
    private static final Logger logger = LoggerFactory.getLogger(LogTest.class);
    @Test
    public void testLog(){
        logger.debug("开始计算...");
        int sum = 0;
        int[] nums = {1, 5, 3, 2, 1, 4, 5, 4, 6, 7, 4, 34, 2, 23};
        for (int num : nums) {
            sum += num;
        }
        logger.info("计算结果为: "+sum);
        logger.debug("结束计算");
    }
}
