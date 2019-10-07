package io.github.happytimor.mybatis.helper.single.database.test.service;

import io.github.happytimor.mybatis.helper.core.service.NoPrimaryKeyService;
import io.github.happytimor.mybatis.helper.single.database.test.domain.UserNoKey;
import io.github.happytimor.mybatis.helper.single.database.test.mapper.UserNoKeyMapper;
import org.springframework.stereotype.Service;

/**
 * @author chenpeng
 * @date 2019-10-07
 */
@Service
public class UserNoKeyService extends NoPrimaryKeyService<UserNoKeyMapper, UserNoKey> {
}
