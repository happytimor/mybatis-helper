package io.github.happytimor.mybatis.helper.single.database.test.service;

import io.github.happytimor.mybatis.helper.core.service.UniqueIndexEnhanceForMultipleTableService;
import io.github.happytimor.mybatis.helper.single.database.test.domain.UserUniqueIndex;
import io.github.happytimor.mybatis.helper.single.database.test.mapper.UserMultipleTableUniqueIndexMapper;
import org.springframework.stereotype.Service;

/**
 * @author chenpeng
 */
@Service
public class UserMultipleTableUniqueIndexService extends UniqueIndexEnhanceForMultipleTableService<UserMultipleTableUniqueIndexMapper, UserUniqueIndex> {

}
