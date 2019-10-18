package com.github.zmm.exception.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 
* @ClassName: ExceptionToEmail 
* @Description: TODO(出现异常就发送邮件通知) 
* @author zhumingming 
* @date 2019年10月15日 下午4:25:57 
*
 */
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExceptionToEmail {

}
