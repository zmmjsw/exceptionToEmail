package com.github.zmm.exception.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

import com.github.zmm.exception.handler.ExceptionToEmailHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/**
 * 
* @ClassName: ExceptionToEmailAop 
* @Description: TODO(aop切面) 
* @author zhumingming 
* @date 2019年10月15日 下午4:26:24 
*
 */
@Aspect
@Slf4j
@RequiredArgsConstructor
public class ExceptionToEmailAop {
	private final  ExceptionToEmailHandler handler;
	// 注解切入点
	 @AfterThrowing(value = "@annotation(com.github.zmm.exception.annotation.ExceptionToEmail)", throwing = "e")
		public void doAfterThrow(JoinPoint joinPoint, Exception e) {
		 log.info("进入异常切面");
		 handler.createNotice(e, joinPoint);
	}

}
