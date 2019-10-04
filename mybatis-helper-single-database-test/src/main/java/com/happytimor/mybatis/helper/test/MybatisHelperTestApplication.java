package com.happytimor.mybatis.helper.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author chenpeng
 */
@SpringBootApplication
@MapperScan(basePackages = "com.happytimor.mybatis.helper.test.mapper")
@EnableScheduling
public class MybatisHelperTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisHelperTestApplication.class, args);
    }

}
