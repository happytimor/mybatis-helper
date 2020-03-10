package io.github.happytimor.mybatis.helper.single.database.test;

import io.github.happytimor.mybatis.helper.core.wrapper.SelectWrapper;
import io.github.happytimor.mybatis.helper.single.database.test.domain.User;
import io.github.happytimor.mybatis.helper.single.database.test.service.GenerateService;
import io.github.happytimor.mybatis.helper.single.database.test.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

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
    public void bigQuery() throws InterruptedException {
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
}
