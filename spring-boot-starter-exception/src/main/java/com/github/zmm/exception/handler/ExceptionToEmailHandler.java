package com.github.zmm.exception.handler;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.zmm.exception.model.ExceptionInfo;
import com.github.zmm.exception.processor.ExceptionProcessor;
import com.github.zmm.exception.properties.Properties;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: ExceptionToEmailHandler
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhumingming
 * @date 2019年10月15日 下午4:26:59
 *
 */
@Slf4j
public class ExceptionToEmailHandler {
	private final ExceptionProcessor exceptionProcessor;

	private final Properties properties;

	public ExceptionToEmailHandler(ExceptionProcessor exceptionProcessor, Properties properties) {
		this.exceptionProcessor = exceptionProcessor;
		this.properties = properties;
	}

	public void createNotice(Exception ex, JoinPoint joinPoint) {
		if (containsException(ex)) {
			return;
		}
		log.error("捕获到异常开始发送消息通知:{}method:{}--->", null, joinPoint.getSignature().getName());
		// 获取请求参数
		Object parameter = getParameter(joinPoint);
		// 获取当前请求对象
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		String address = null;
		if (attributes != null) {
			HttpServletRequest request = attributes.getRequest();
			// 获取请求地址
			address = request.getRequestURL().toString()
					+ ((request.getQueryString() != null && request.getQueryString().length() > 0)
							? "?" + request.getQueryString()
							: "");
		}
		ExceptionInfo exceptionInfo = new ExceptionInfo(ex, joinPoint.getSignature().getName(), parameter, address);
		exceptionInfo.setProject(properties.getProjectName());
		new Runnable() {
			@Override
			public void run() {
				exceptionProcessor.sendNotice(exceptionInfo);
			}
		}.run();

	}

	private boolean containsException(Exception exception) {
		Class<? extends Exception> exceptionClass = exception.getClass();
		List<Class<? extends Exception>> list = properties.getExcludeExceptions();
		for (Class<? extends Exception> clazz : list) {
			if (clazz.isAssignableFrom(exceptionClass)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据方法和传入的参数获取请求参数
	 */
	private Object getParameter(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();
		Parameter[] parameters = method.getParameters();

		Object[] args = joinPoint.getArgs();
		List<Object> argList = new ArrayList<>(parameters.length);
		for (int i = 0; i < parameters.length; i++) {
			RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
			if (requestBody != null) {
				argList.add(args[i]);
			}
			RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
			if (requestParam != null) {
				Map<String, Object> map = new HashMap<>(1);
				String key = parameters[i].getName();
				if (!StringUtils.isEmpty(requestParam.value())) {
					key = requestParam.value();
				}
				map.put(key, args[i]);
				argList.add(map);
			}
		}
		if (argList.size() == 0) {
			return null;
		} else if (argList.size() == 1) {
			return argList.get(0);
		} else {
			return argList;
		}
	}

}
