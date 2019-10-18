package com.github.zmm.exception.model;

import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.DigestUtils;

import lombok.Data;
/**
 * 
* @ClassName: ExceptionInfo 
* @Description: TODO(异常信息详情类) 
* @author zhumingming 
* @date 2019年10月15日 下午2:17:19 
*
 */
@Data
public class ExceptionInfo {
	 /**
     * 工程名
     */
    private String project;

    /**
     * 异常的标识码
     */
    private String uid;

    /**
     * 请求地址
     */
    private String reqAddress;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 方法参数信息
     */
    private Object params;

    /**
     * 类路径
     */
    private String classPath;

    /**
     * 异常信息
     */
    private String exceptionMessage;

    /**
     * 异常追踪信息
     */
    private List<String> traceInfo = new ArrayList<>();

    /**
     * 最后一次出现的时间
     */
    private LocalDateTime latestShowTime = LocalDateTime.now();
    
    public ExceptionInfo(Throwable ex, String methodName, Object args, String reqAddress) {
        this.exceptionMessage = gainExceptionMessage(ex);
        this.reqAddress = reqAddress;
        this.params = args;
        List<StackTraceElement> list = Arrays.stream(ex.getStackTrace())
                .filter(x -> !"<generated>".equals(x.getFileName())).collect(toList());
        if (list.size() > 0) {
            this.traceInfo = list.stream().map(StackTraceElement::toString).collect(toList());
            this.methodName = null == methodName ? list.get(0).getMethodName() : methodName;
            this.classPath = list.get(0).getClassName();
        }
        this.uid = calUid();
    }


    private String gainExceptionMessage(Throwable exception) {
        String em = exception.toString();
        if (exception.getCause() != null) {
            em = String.format("%s\r\n\tcaused by : %s", em, gainExceptionMessage(exception.getCause()));
        }
        return em;
    }

    private String calUid() {
        return DigestUtils.md5DigestAsHex(
                String.format("%s-%s", exceptionMessage, traceInfo.size() > 0 ? traceInfo.get(0) : "").getBytes());
    }

    public String createText() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("项目名称：").append(project).append("\n");
        stringBuilder.append("类路径：").append(classPath).append("\n");
        stringBuilder.append("请求地址：").append(reqAddress).append("\n");
        stringBuilder.append("方法名：").append(methodName).append("\n");
        if (params != null) {
            stringBuilder.append("方法参数：").append(params).append("\n");
        }
        stringBuilder.append("异常信息：").append("\n").append(exceptionMessage).append("\n");
        stringBuilder.append("异常追踪：").append("\n").append(String.join("\n", traceInfo)).append("\n");
        stringBuilder.append("最后一次出现时间：")
                .append(latestShowTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return stringBuilder.toString();
    }

}
