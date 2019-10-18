package com.github.zmm.exception.properties;

import lombok.Data;

/**
 * 
* @ClassName: MailProperties 
* @Description: TODO(邮箱配置) 
* @author zhumingming 
* @date 2019年10月15日 下午2:30:48 
*
 */
@Data
public class MailProperties {

    /**
     * 发送人
     */
    private String from;
    /**
     * 接收人，可多选
     */
    private String[] to;
    /**
     * 抄送人，可多选
     */
    private String[] cc;

}
