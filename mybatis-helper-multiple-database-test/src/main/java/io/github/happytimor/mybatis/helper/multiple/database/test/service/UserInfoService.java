package io.github.happytimor.mybatis.helper.multiple.database.test.service;

import io.github.happytimor.mybatis.helper.core.service.BaseService;
import io.github.happytimor.mybatis.helper.multiple.database.test.domain.User;
import io.github.happytimor.mybatis.helper.multiple.database.test.domain.UserInfo;
import io.github.happytimor.mybatis.helper.multiple.database.test.mapper.datasource1.UserMapper;
import io.github.happytimor.mybatis.helper.multiple.database.test.mapper.datasource2.UserInfoMapper;
import org.springframework.stereotype.Service;

/**
 * @author chenpeng
 * @date 2019-09-07
 */
@Service
public class UserInfoService extends BaseService<UserInfoMapper, UserInfo> {

}
