package io.github.happytimor.mybatis.helper.single.database.test;

import io.github.happytimor.mybatis.helper.core.MybatisHelper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author chenpeng
 */
@Component
public class MyHelper extends MybatisHelper implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
        this.registerSingleDatabase("io.github.happytimor.mybatis.helper.single.database.test.mapper");
    }
}
