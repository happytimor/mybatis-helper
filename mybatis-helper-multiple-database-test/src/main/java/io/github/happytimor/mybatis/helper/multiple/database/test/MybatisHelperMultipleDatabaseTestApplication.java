package io.github.happytimor.mybatis.helper.multiple.database.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * @author chenpeng
 */
@SpringBootApplication
@MapperScan(basePackages = "io.github.happytimor.mybatis.helper.multiple.database.test.mapper.*")
public class MybatisHelperMultipleDatabaseTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisHelperMultipleDatabaseTestApplication.class, args);
	}

	/**
	 * 设置系统时区
	 */
	@PostConstruct
	void setDefaultTimezone() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
	}
}
