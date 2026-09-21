package com.huaihan.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huaihan.mapper.OperateLogMapper;
import com.huaihan.pojo.OperateLog;
import com.huaihan.utils.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 操作日志切面类 AOP
 * 作用：使用AOP对标记@Log注解的Controller方法进行增强，自动记录操作日志存入数据库
 * 记录内容：操作人ID、操作时间、类名、方法名、请求参数、返回结果、耗时
 */
@Component  //交给Spring容器管理
@Aspect     //标记为切面类
@Slf4j      //lombok注解，自动生成log日志对象，替代System.out打印
public class OperateLogAspect {

    //注入操作日志Mapper，用于向数据库插入日志记录
    @Autowired
    private OperateLogMapper operateLogMapper;

    //Jackson工具类，用来把返回结果对象转为JSON字符串
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 定义切点表达式
     * execution(* com.huaihan.controller.*.*(..))：匹配controller包下所有类的所有方法
     * && @annotation(com.huaihan.anno.Log)：并且方法上带有自定义@Log注解才会触发切面
     */
    @Pointcut("execution(* com.huaihan.controller.*.*(..)) && @annotation(com.huaihan.anno.Log)")
    public void logPointCut() {
        //切点方法，方法体不需要写代码，仅作为切点标识
    }

    /**
     * 环绕通知 @Around
     * 功能：在目标方法执行【之前】和【之后】都执行代码，可以捕获目标方法返回值和异常
     * @param joinPoint 连接点对象，可以获取目标方法信息：方法名、参数、类信息
     * @return 目标方法执行后的返回结果
     * @throws Throwable 抛出目标方法产生的异常
     */
    @Around("logPointCut()")
    public Object recordOperateLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("【AOP】切面进入了！");
        //记录方法开始执行的时间戳，用于计算接口耗时
        long start = System.currentTimeMillis();
        //目标方法返回结果
        Object result = null;
        //存储目标方法返回值的JSON字符串
        String returnValue;

        try {
            //执行目标Controller方法（相当于调用接口业务代码）
            result = joinPoint.proceed();
            //将返回结果序列化为JSON字符串
            returnValue = objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            //捕获返回值JSON序列化异常
            log.error("返回值序列化失败", e);
            returnValue = "返回值序列化失败";
        } catch (Throwable e) {
            //捕获目标业务方法抛出的所有异常
            returnValue = "执行异常：" + e.getMessage();
            throw e; //重新抛出异常，保证前端可以收到报错，不能吞掉异常
        }

        // 获取方法签名，用来拿到方法详细信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 获取目标类全限定类名（包名+类名）
        String className = joinPoint.getTarget().getClass().getName();
        // 获取目标方法原始名称
        String methodName = signature.getName();
        // 获取目标方法的所有请求参数
        Object[] args = joinPoint.getArgs();

        // 遍历所有参数，调用toString()拼接参数信息，解决Jackson序列化实体对象报错问题
        StringBuilder sb = new StringBuilder();
        for (Object arg : args) {
            sb.append(arg).append(" | ");
        }

        String methodParams = sb.toString();
        // 限制参数字符串最大长度2000，防止数据库字段超长报错
        if (methodParams.length() > 2000) {
            methodParams = methodParams.substring(0, 2000);
        }

        // 限制返回值字符串最大长度2000
        if (returnValue.length() > 2000) {
            returnValue = returnValue.substring(0, 2000);
        }

        // 计算接口执行耗时（当前时间 - 开始时间）
        long costTime = System.currentTimeMillis() - start;

        // 封装操作日志实体对象
        OperateLog operateLog = new OperateLog();
        operateLog.setOperateEmpId(getCurrentUserId());     // 获取当前登录员工ID（操作人）
        operateLog.setOperateTime(LocalDateTime.now());     // 记录操作发生时间
        operateLog.setClassName(className);                 // 操作类全类名
        operateLog.setMethodName(methodName);               // 操作方法名
        operateLog.setMethodParams(methodParams);           // 请求参数
        operateLog.setReturnValue(returnValue);             // 接口返回结果
        operateLog.setCostTime(costTime);                   // 方法执行耗时

        log.info("【AOP】准备插入日志对象：{}", operateLog);
        // 调用mapper，把这条日志插入数据库operate_log表
        operateLogMapper.insert(operateLog);
        log.info("【AOP】insert执行完毕");

        //将目标方法的返回结果返回给前端
        return result;
    }

    /**
     * 获取当前登录用户ID的工具方法
     * @return 当前登录员工id
     */
    private Integer getCurrentUserId() {
        return UserContext.getEmpId();
    }
}
