package com.github.zmm.exception.processor;

import org.springframework.mail.javamail.JavaMailSender;
import com.github.zmm.exception.properties.Properties;
/**
 * 
* @ClassName: AbstractException 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author zhumingming 
* @date 2019年10月15日 下午4:27:27 
*
 */

public abstract class AbstractException {
	protected final Properties Properties;
	protected final  JavaMailSender mailSender;
	    public AbstractException(Properties Properties,JavaMailSender mailSender) {
	        this.Properties =Properties;
	        this.mailSender = mailSender;
	    }
	    
}
