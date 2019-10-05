package io.github.happytimor.mybatis.helper.test;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 1. 比较执行速度
 * 2. 查看是否内存溢出(限制运行内存)
 *
 * @author chenpeng
 * @date 2019-09-01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PerformanceTests {
    private final static Logger logger = LoggerFactory.getLogger(PerformanceTests.class);


}
