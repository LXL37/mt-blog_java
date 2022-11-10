package com.meet.aspect;

import com.alibaba.fastjson.JSON;
import com.meet.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: alyosha
 * @Date: 2022/3/31 9:48
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.meet.annotation.SystemLog)")
    public void print(){

    }

    @Around("print()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {

        Object proceed;
        try {
            //前置打印
            handleBefore(joinPoint);
            proceed = joinPoint.proceed();
            //后置打印
            handleAfter(proceed);
        } finally {
            // 结束后换行 拼接的是当前系统的换行符
            log.info("=====END====="+System.lineSeparator());
        }

        return proceed;
    }

    private void handleBefore(ProceedingJoinPoint joinPoint) {
        //获取被增强方法上的注释对象
        SystemLog systemLog = getSystemLog(joinPoint);
        String businessName = systemLog.businessName();

        Signature signature = joinPoint.getSignature();
        String pathName = signature.getDeclaringType().getName();
        String methodName = signature.getName();

        //获取请求对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String requestURI = request.getRequestURI();
        String requestMethod = request.getMethod();
        String remoteHost = request.getRemoteHost();



        log.info("==========START==========");
        //打印RUL
        log.info("URL             : {}",requestURI);
        //打印注释的内容
        log.info("BusinessName    : {}",businessName);

        log.info("HTTP Method     : {}",requestMethod);
        //打印调用controller层的全路径 以及执行方法
        log.info("Class Method    : {},{}",pathName,methodName);
        log.info("IP              : {}",remoteHost);
        //打印请求入参
        log.info("Request args    : {}", JSON.toJSONString(joinPoint.getArgs()));

    }

    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        //通过寻找 实现类 找到所需要的内容
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
       return signature.getMethod().getAnnotation(SystemLog.class);
    }

    private void handleAfter(Object proceed) {
        log.info("Response          : {}",JSON.toJSONString(proceed));

    }
}
