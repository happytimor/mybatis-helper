package io.github.happytimor.mybatis.helper.single.database.test.service;

import io.github.happytimor.mybatis.helper.core.service.UniqueIndexEnhanceService;
import io.github.happytimor.mybatis.helper.single.database.test.domain.UserUniqueIndex;
import io.github.happytimor.mybatis.helper.single.database.test.mapper.UserUniqueIndexMapper;
import org.springframework.stereotype.Service;

/**
 * @author chenpeng
 */
@Service
public class UserUniqueIndexService extends UniqueIndexEnhanceService<UserUniqueIndexMapper, UserUniqueIndex> {

}
