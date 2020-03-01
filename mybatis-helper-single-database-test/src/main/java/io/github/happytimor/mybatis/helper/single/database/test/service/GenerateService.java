package io.github.happytimor.mybatis.helper.single.database.test.service;

import io.github.happytimor.mybatis.helper.core.wrapper.DeleteWrapper;
import io.github.happytimor.mybatis.helper.core.wrapper.SelectWrapper;
import io.github.happytimor.mybatis.helper.single.database.test.domain.GenerateInterface;
import io.github.happytimor.mybatis.helper.single.database.test.domain.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author chenpeng
 */
@Service
public class GenerateService {
    @Resource
    private UserService userService;

    public void generateBatch(GenerateInterface generateInterfaceInterface) {
        this.generateBatch(new Random().nextInt(1000 + 1), generateInterfaceInterface);
    }

    public void generateBatch(int count, GenerateInterface generateInterfaceInterface) {
        String flag = UUID.randomUUID().toString();
        List<User> list = new ArrayList<User>();
        for (int i = 0; i < count; i++) {
            list.add(this.generateOne(flag));
        }
        this.userService.batchInsert(list);
        Number insertCount = this.userService.selectCount(new SelectWrapper<User>().eq(User::getFlag, flag));
        assert insertCount.intValue() == count;
        try {
            generateInterfaceInterface.test(flag, list);
        } finally {
            this.userService.delete(new DeleteWrapper<User>().eq(User::getFlag, flag));
            Number existsCount = this.userService.selectCount(new SelectWrapper<User>().eq(User::getFlag, flag));
            assert existsCount.intValue() == 0;
        }
    }

    public User generateOne(String flag) {
        User user = new User();
        user.setName("name_" + new Random().nextInt(10000));
        user.setAge(new Random().nextInt(100));
        //10%的概率产生null值
        if (new Random().nextInt(100) > 10) {
            user.setNullableAge(user.getAge());
        }
        user.setMarried(new Random().nextBoolean());
        //不要毫秒, 否则数据库和内存保存不一致, 随机生成近一个月内的日期
        user.setLastLoginTime(LocalDateTime.now()
                .minusDays(new Random().nextInt(30))
                .minusHours(new Random().nextInt(24))
                .minusSeconds(new Random().nextInt(60))
                .withNano(0));
        user.setUserGrade(new Random().nextInt(700));
        user.setFlag(flag);
        return user;
    }
}
