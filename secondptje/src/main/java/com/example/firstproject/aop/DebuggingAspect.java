package com.example.firstproject.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class DebuggingAspect {
    @Pointcut("execution(* com.example.firstproject.service.CommentService.create(..))")
    private void cut() {}
        @Before("cut()")
                public void loggingArgs(JoinPoint joinPoint) {
            //입력값 가져오기
            Object[] args = joinPoint.getArgs();
            //클래스 명
            String className = joinPoint.getTarget()
                    .getClass()
                    .getSimpleName();
            //메소드 명
            String methodName = joinPoint.getSignature()
                    .getName();
            //입력값 로깅하기
            for (Object obj : args) {
                log.info("{}#{}의 입력값 => {} ", className,methodName, obj);
            }
        }

}
