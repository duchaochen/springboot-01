### 获取其它配置文件
    使用以下3个注解可以直接获取配置文件的对应的值映射到实体类中
    @Component
    @PropertySource(value = {"classpath:person.properties"})
    @ConfigurationProperties(prefix = "person")
    
    persion.properties中的数据
        person.person-name=张三
        person.age=25
        person.birth-day=2018/09/21
        person.boss=true
        person.dog.name=小狗
        person.dog.age=3
        person.lists=a,b,c
        person.maps.key1=aaa
        person.maps.key2=bbb

### 当在.properties的配置文件中有中文时，读取出来的总是乱码

    比如我的application.properties配置文件的内容如下：
    person.person-name=张三
    person.age=25
    person.birth-day=2018/09/21
    person.boss=true
    person.dog.name=小狗
    person.dog.age=3
    person.lists=a,b,c
    person.maps.key1=aaa
    person.maps.key2=bbb
    解决方案为：因为默认是以ISO-8859-1的字符编码读取，尝试的方法有：
    1.添加配置文件代码
      banner.charset=UTF-8
      server.tomcat.uri-encoding=UTF-8
      spring.http.encoding.charset=UTF-8
      spring.http.encoding.enabled=true
      spring.http.encoding.force=true
      spring.messages.encoding=UTF-8
    2.将application.properites的文件类型修改为UTF-8的编码类型。
    设置 File Encodings的Transparent native-to-ascii conversion为true，具体步骤如下：依次点击
    File -> Settings -> Editor -> File Encodings
    
    将Properties Files (*.properties)下的Default encoding for properties files设置为UTF-8，
    将Transparent native-to-ascii conversion前的勾选上。

### 加载其它bean的xml文件的2中方式

       bean.xml内容：
       <?xml version="1.0" encoding="UTF-8"?>
       <beans xmlns="http://www.springframework.org/schema/beans"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
       
           <bean id="helloService" class="com.adu.springboot.service.HelloService"/>
       </beans>
       第一种方式：现在要将这个bean.xml中的所有bean加载到springboot中需要使用@ImportResource(locations = {"classpath:bean.xml"})
       这个注解一定要写在配置类上。
       
       @ImportResource(locations = {"classpath:bean.xml"})
       @SpringBootApplication
       public class Springboot01Application {
       
       	public static void main(String[] args) {
       		SpringApplication.run(Springboot01Application.class, args);
       	}
       }
       
       第二种方式，官方推荐使用方式，如果需要构造一个helloService对象，需要创建一个配置类，配置类中创建一个返回HelloService对象的方法
       代码：
            /**
             * 表示配置类
             */
            @Configuration
            public class MyAppConfig {
            
                @Bean
                public HelloService helloService() {
                    System.out.println("helloService...");
                    return new HelloService();
                }
            }
       加上@Configuration注解就表示为配置类了，然后写一个需要返回HelloService的方法，方法名就是bean的对象名，并且需要在该方法上加上@Bean注解，
       那么执行测试代码即可加载成功helloService的对象,测试代码
       @Autowired
       	private ApplicationContext ioc;
       
       	@Test
       	public void contextIOC() {
       		boolean helloService = ioc.containsBean("helloService");
       		System.out.println(helloService);
       	}

### 占位符${random.value}、${random.int}、${random.long}${random.int(10)}、${random.int[1024,65536]}
        代码演示：
        person.last-name=张三${random.uuid}
        person.age=${random.int}
        person.birth=2017/12/15
        person.boss=false
        person.maps.k1=v1
        person.maps.k2=14
        person.lists=a,b,c
        person.dog.name=${person.hello:hello}_dog
        person.dog.age=15
        
        张三${random.uuid}：表示在张三后面加上一个uuid，
        person.age=${random.int}：给age赋一个int类型的随机数
        person.dog.name=${person.hello:hello}_dog：给person.dog.name赋了一个没有的属性person.hello的值，冒号后面为默认值，
        如果person.hello有值将使用自己已有的值，如果没有值将直接使用冒号后面的默认值。

### Profile配置多文件
       1、多Profile文件
       
       我们在主配置文件编写的时候，文件名可以是   application-{profile}.properties/yml
       
       默认使用application.properties的配置；
       
       现在有3个文件：application-dev.properties，application-prod.properties，application.properties
       我们需要在主配置文件application.properties中加上以下激活配置
    ​	1、在配置文件中指定  spring.profiles.active=dev
    
    ​	2、命令行：
    
    ​		java -jar spring-boot-02-config-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev；
    
    ​		可以直接在测试的时候，配置传入命令行参数
    
    ​	3、虚拟机参数；
    
    ​		-Dspring.profiles.active=dev
    
### 配置文件加载位置
    
    springboot 启动会扫描以下位置的application.properties或者application.yml文件作为Spring boot的默认配置文件
    
    –file:./config/   在idea中和src文件同级的config文件夹下
    
    –file:./          在程序的根目录下
    
    –classpath:/config/ 在reources/config文件夹下
    
    –classpath:/        在reources的根目录下
    
    优先级由高到底，高优先级的配置会覆盖低优先级的配置；
    
    SpringBoot会从这四个位置全部加载主配置文件；**互补配置**；
    
    
### 日志SLF4j使用
    
    ### 1、如何在系统中使用SLF4j   https://www.slf4j.org
    
    以后开发的时候，日志记录方法的调用，不应该来直接调用日志的实现类，而是调用日志抽象层里面的方法；
    
    给系统里面导入slf4j的jar和  logback的实现jar
    
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    
    public class HelloWorld {
      public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(HelloWorld.class);
        logger.info("Hello World");
      }
    }
### 指定日志级别
    代码：
    Logger logger = LoggerFactory.getLogger(getClass());
    logger.trace("这是trace...日志");
    logger.debug("这是debug...日志");
    logger.warn("这是warn...日志");
    logger.info("这是info...日志");
    logger.error("这是error...日志");
    配置：
    1.logging.level.com.adu = trace//logging.level.包名=trace/debug/warn/info/error
    
    2.指定日志生成路径,如果是windows下该路径会在项目对应的盘符的根目录（一般linux下使用）,和上面的2选一
    logging.path=/springboot/log
    
    3.在控制台输出日志
    logging.pattern.console=%d{yyyy-MM-dd} [%thread] %-5level %logger{50} - %msg%n
    4.指定文件中日志输出的格式
    logging.pattern.file=%d{yyyy-MM-dd} === [%thread] === %-5level === %logger{50} ==== %msg%n
