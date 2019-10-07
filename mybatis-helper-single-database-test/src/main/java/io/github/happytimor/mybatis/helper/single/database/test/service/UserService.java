package io.github.happytimor.mybatis.helper.single.database.test.service;

import io.github.happytimor.mybatis.helper.core.service.BaseService;
import io.github.happytimor.mybatis.helper.single.database.test.domain.User;
import io.github.happytimor.mybatis.helper.single.database.test.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author chenpeng
 */
@Service
public class UserService extends BaseService<UserMapper, User> {

}
