package com.github.zmm.exception.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.Assert;

import com.github.zmm.exception.aop.ExceptionToEmailAop;
import com.github.zmm.exception.handler.ExceptionToEmailHandler;
import com.github.zmm.exception.processor.ExceptionProcessor;
import com.github.zmm.exception.processor.impl.MailExceptionProcessorImpl;
import com.github.zmm.exception.properties.MailProperties;
import com.github.zmm.exception.properties.Properties;
/**
 * 
* @ClassName: ExceptionNoticeAutoConfiguration 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author zhumingming 
* @date 2019年10月15日 下午3:31:50 
*
 */
@Configuration
@ConditionalOnProperty(prefix = Properties.PREFIX, name = "enable", havingValue = "true")
@EnableConfigurationProperties(value = Properties.class)
public class ExceptionNoticeAutoConfiguration {
    @Autowired(required = false)
    private JavaMailSender mailSender;
	 @Bean
	    public ExceptionToEmailHandler noticeHandler(Properties properties) {
	        List<ExceptionProcessor> noticeProcessors = new ArrayList<>(2);
	        ExceptionProcessor noticeProcessor = null;
	        MailProperties email = properties.getMail();
	        if (null != email && null != mailSender) {
	            noticeProcessor = new MailExceptionProcessorImpl(properties, mailSender);
	            noticeProcessors.add(noticeProcessor);
	        }
	        Assert.isTrue(noticeProcessors.size() != 0,"Exception notification configuration is incorrect");
	      return new  ExceptionToEmailHandler(noticeProcessor,properties);
	    }
	 
	 
	    @Bean
	    @ConditionalOnBean(ExceptionToEmailHandler.class)
	    public ExceptionToEmailAop exceptionListener(ExceptionToEmailHandler noticeHandler) {
	        return new ExceptionToEmailAop(noticeHandler);
	    }

}
