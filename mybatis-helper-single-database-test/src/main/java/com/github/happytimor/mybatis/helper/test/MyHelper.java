package com.github.happytimor.mybatis.helper.test;

import com.github.happytimor.mybatis.helper.core.MybatisHelper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author chenpeng
 * @date 2019-09-01
 */
@Component
public class MyHelper extends MybatisHelper implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
        this.registSingleDatabase("com.github.happytimor.mybatis.helper.test.mapper");
    }
}
