package com.huaihan.exception;

import com.huaihan.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice // 全局异常处理器
public class GlobalExceptionHandler {
    /**
     * 捕获所有其他未知异常
     */
    @ExceptionHandler // 说明这是一个异常处理的方法
    public Result handleException(Exception e) {
        log.error("程序出错，异常是：",e);
        return Result.error("出错了，请等待修复");
    }

    /**
     * 专门捕获唯一索引冲突异常：如手机号重复
     */
    @ExceptionHandler
    public Result handleException(DuplicateKeyException e) {
        log.error("出现重复异常：",e);
        String message = e.getMessage();
        int index = message.indexOf("Duplicate entry");
        String string = message.substring(index);
        String[] arr = string.split(" ");
        return Result.error(arr[2] + "已经存在，请修改后重试");
    }
    /**
     * 捕获自定义业务异常（班级删除判断学生关联）
     */
    @ExceptionHandler(BusinessException.class)
    public Result handleBusinessException(BusinessException e){
        log.error("业务异常：{}", e.getMessage());
        return Result.error("对不起, 该班级下有学生, 不能直接删除");
    }
}
