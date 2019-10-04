package com.github.happytimor.mybatis.helper.test.service;

import com.github.happytimor.mybatis.helper.core.service.BaseService;
import com.github.happytimor.mybatis.helper.test.domain.User;
import com.github.happytimor.mybatis.helper.test.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author chenpeng
 * @date 2019-09-07
 */
@Service
public class UserService extends BaseService<UserMapper, User> {

}
