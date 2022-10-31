package cn_java_main;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

//开启自动配置
@EnableAutoConfiguration
//扫描包
@ComponentScan(value = {"cn_java_controller", "cn_java_mapper", "cn_java_service", "cn_java_service_impl", "cn_java_config", "cn_java_utils","cn_java_interceptor"})
//扫描mapper
@MapperScan(value = "cn_java_mapper")
//过滤包扫描
public class SpringApplicationStart {
    public static void main(String[] args) {
        SpringApplication.run(SpringApplicationStart.class, args);
    }
}
