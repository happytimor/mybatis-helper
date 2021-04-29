package io.github.happytimor.mybatis.helper.single.database.test;

import io.github.happytimor.mybatis.helper.core.function.SqlFunction;
import io.github.happytimor.mybatis.helper.core.wrapper.SelectWrapper;
import io.github.happytimor.mybatis.helper.single.database.test.domain.User;
import io.github.happytimor.mybatis.helper.single.database.test.service.GenerateService;
import io.github.happytimor.mybatis.helper.single.database.test.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenpeng
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OomTests {

    @Resource
    private GenerateService generateService;

    @Resource
    private UserService userService;

    @Test
    public void bigQuery() {
//        long[] arr = new long[10000000];
        //-Xms32m -Xmx32m
        generateService.generateBatch(((flag, userList) -> {
            for (User user : userList) {
                this.userService.selectList(new SelectWrapper<User>()
                        .eq(User::getName, user.getName())
                        .eq(User::getAge, user.getAge())
                        .eq(User::getUserGrade, user.getUserGrade())
                        .eq(User::getMarried, user.getMarried())
                        .eq(User::getLastLoginTime, user.getLastLoginTime())
                );
            }
        }));
    }

    /**
     * 多线程并发测试
     */
    @Test
    public void multipleThreadTest() {
        generateService.generateBatch(((flag, userList) -> {
            AtomicInteger atomicInteger = new AtomicInteger(0);
            int loopCount = 1000;
            for (int i = 0; i < loopCount; i++) {
                new Thread(() -> {
                    try {
                        Thread.sleep(new Random().nextInt(5));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    this.userService.selectMap(new SelectWrapper<User>()
                            .select(SqlFunction.count(SqlFunction.distinct(User::getId)),
                                    SqlFunction.min(User::getAge),
                                    SqlFunction.sum(SqlFunction.distinct(User::getAge)))
                            .eq(User::getAge, new Random().nextInt(20))
                            .eq(User::getFlag, flag)
                    );
                    atomicInteger.incrementAndGet();
                }).start();
            }

            while (atomicInteger.get() != loopCount) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }));
    }
}
