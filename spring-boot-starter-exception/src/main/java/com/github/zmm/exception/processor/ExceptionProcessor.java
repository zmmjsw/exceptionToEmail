package com.github.zmm.exception.processor;

import com.github.zmm.exception.model.ExceptionInfo;
/**
 * 
* @ClassName: ExceptionProcessor 
* @Description: TODO(异常信息发送接口) 
* @author zhumingming 
* @date 2019年10月15日 下午2:22:33 
*
 */
public interface ExceptionProcessor {
	 void sendNotice(ExceptionInfo exceptionInfo);

}
