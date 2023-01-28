package com.ansel.aspect;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Ansel Zhong
 * @title blog
 * @Date 2023/1/22
 * @Description ASpect
 */

@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.ansel.controller.*.*(..))")
    public void log(){

    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringType() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        Requesting requesting = new Requesting(url,ip,classMethod,args);
        logger.info("---------doBefore----------");
        logger.info("Request : {}", requesting);
    }

    @After(value = "log()")
    public void doAfter(){
        logger.info("--------doAfter-----------");
    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturn(Object result){
        logger.info("Result : {}" + result);
    }

    @Data
    @AllArgsConstructor
    private class Requesting{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;
    }
}
