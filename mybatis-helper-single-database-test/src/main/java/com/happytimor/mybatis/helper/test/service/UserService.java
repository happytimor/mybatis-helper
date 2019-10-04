package com.happytimor.mybatis.helper.test.service;

import com.happytimor.mybatis.helper.core.service.BaseService;
import com.happytimor.mybatis.helper.test.domain.User;
import com.happytimor.mybatis.helper.test.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author chenpeng
 * @date 2019-09-07
 */
@Service
public class UserService extends BaseService<UserMapper, User> {

}
