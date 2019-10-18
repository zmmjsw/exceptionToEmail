
### 快速开始 

#### Maven install 
```java
mvn clean install -DskipTests
```

#### pom依赖 
```java
		<dependency>
			<groupId>com.github.zmm</groupId>
			<artifactId>spring-boot-starter-exception</artifactId>
			<version>0.0.1-SNAPSHOT</version>
		</dependency>
```



#### 项目配置文件
```java
spring.mail.host=smtp.qq.com
# 发送方的邮箱
spring.mail.username=ming745077881@qq.com
#对于qq邮箱而言 密码指的就是发送方的授权码 wkxwrdgvndpbbdcf  
spring.mail.password=wkxwrdgvndpbbdcf
spring.mail.port=465
spring.mail.protocol=smtp
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.ssl.enable=true
#是否用启用加密传送的协议验证项
#注意：在spring.mail.password处的值是需要在邮箱设置里面生成的授权码，这个不是真实的密码。
# 告警通知 多个以逗号分隔
exception.notice.mail.from=ming745077881@qq.com
exception.notice.mail.to=zhumingming@eshippinggateway.com
exception.notice.enable=true  #true表示开启
exception.notice.excludeExceptions=       #需要排除的异常
```

#### 用法
 在方法上添加@ExceptionToEmail

  ```
  @ExceptionToEmail
	public ResponseData saveUser(@RequestBody @Valid UserDto user) {
		iMailServiceImpl.sendFreemarker1();
		return new ResponseData(ResponseData.OK);
	}
 ```
