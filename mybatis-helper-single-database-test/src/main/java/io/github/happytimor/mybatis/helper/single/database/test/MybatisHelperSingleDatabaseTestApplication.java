package io.github.happytimor.mybatis.helper.single.database.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * @author chenpeng
 */
@SpringBootApplication
@MapperScan(basePackages = "io.github.happytimor.mybatis.helper.single.database.test.mapper")
public class MybatisHelperSingleDatabaseTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisHelperSingleDatabaseTestApplication.class, args);
    }

    @PostConstruct
    void setDefaultTimezone() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }

}
