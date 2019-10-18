package com.github.zmm.exception.properties;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;


import lombok.Data;
/**
 * 
* @ClassName: Properties 
* @Description: TODO(配置文件) 
* @author zhumingming 
* @date 2019年10月15日 下午4:27:50 
*
 */
@Data
@ConfigurationProperties(prefix = Properties.PREFIX)
public class Properties {
    public static final String PREFIX = "exception.notice";
    
    /**
     * 启用开关
     */
    private boolean enable;
    /**
     * 异常工程名
     */
    @Value("${spring.application.name:${exception.project-name:无名工程}}")
    private String projectName;
    /**
     * 排除的需要统计的异常
     */
    private List<Class<? extends Exception>> excludeExceptions = new ArrayList<>();
    /**
     * 邮箱通知配置
     */
    @NestedConfigurationProperty
    private MailProperties mail;
}
