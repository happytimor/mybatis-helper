package io.github.happytimor.mybatis.helper.test.service;

import io.github.happytimor.mybatis.helper.core.service.BaseService;
import io.github.happytimor.mybatis.helper.test.domain.User;
import io.github.happytimor.mybatis.helper.test.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author chenpeng
 * @date 2019-09-07
 */
@Service
public class UserService extends BaseService<UserMapper, User> {

}
