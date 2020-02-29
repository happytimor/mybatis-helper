package io.github.happytimor.mybatis.helper.single.database.test.service;

import io.github.happytimor.mybatis.helper.core.wrapper.DeleteWrapper;
import io.github.happytimor.mybatis.helper.core.wrapper.SelectWrapper;
import io.github.happytimor.mybatis.helper.single.database.test.domain.GenerateInterface;
import io.github.happytimor.mybatis.helper.single.database.test.domain.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author chenpeng
 */
@Service
public class GenerateService {
    @Resource
    private UserService userService;

    public void generateBatch(int count, GenerateInterface generateInterfaceInterface) {
        String flag = UUID.randomUUID().toString();
        List<User> list = new ArrayList<User>();
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setName("name_" + new Random().nextInt(10000));
            user.setAge(new Random().nextInt(100));
            user.setMarried(new Random().nextBoolean());
            user.setBirthday(new Date());
            user.setFlag(flag);
            list.add(user);
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
}
