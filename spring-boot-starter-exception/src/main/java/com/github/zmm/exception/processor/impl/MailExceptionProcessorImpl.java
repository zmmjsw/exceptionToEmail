package com.github.zmm.exception.processor.impl;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.StringUtils;
import com.github.zmm.exception.model.ExceptionInfo;
import com.github.zmm.exception.processor.AbstractException;
import com.github.zmm.exception.processor.ExceptionProcessor;
import com.github.zmm.exception.properties.MailProperties;
import com.github.zmm.exception.properties.Properties;
/**
 * 
* @ClassName: MailExceptionProcessorImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author zhumingming 
* @date 2019年10月15日 下午4:27:38 
*
 */
public class MailExceptionProcessorImpl extends AbstractException implements ExceptionProcessor {

	public MailExceptionProcessorImpl(Properties Properties, JavaMailSender mailSender) {
		super(Properties, mailSender);
	}

	@Override
	public void sendNotice(ExceptionInfo exceptionInfo) {
		MailProperties mail = Properties.getMail();
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(mail.getFrom());
		mailMessage.setTo(mail.getTo());
		if (!StringUtils.isEmpty(mailMessage.getCc())) {
			mailMessage.setCc(mailMessage.getCc());
		}
		mailMessage.setText(exceptionInfo.createText());
		mailMessage.setSubject(String.format("来自%s项目的异常通知", exceptionInfo.getProject()));
		mailSender.send(mailMessage);
	}

}
