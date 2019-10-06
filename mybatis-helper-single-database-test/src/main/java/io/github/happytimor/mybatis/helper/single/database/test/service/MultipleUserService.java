package io.github.happytimor.mybatis.helper.single.database.test.service;

import io.github.happytimor.mybatis.helper.core.service.MultipleTableService;
import io.github.happytimor.mybatis.helper.single.database.test.domain.User;
import io.github.happytimor.mybatis.helper.single.database.test.mapper.MultipleUserMapper;
import org.springframework.stereotype.Service;

/**
 * @author chenpeng
 * @date 2019-10-06
 */
@Service
public class MultipleUserService extends MultipleTableService<MultipleUserMapper, User> {
}
