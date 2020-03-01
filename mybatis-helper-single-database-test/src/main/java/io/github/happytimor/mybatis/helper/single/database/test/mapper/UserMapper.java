package io.github.happytimor.mybatis.helper.single.database.test.mapper;

import io.github.happytimor.mybatis.helper.core.mapper.BaseMapper;
import io.github.happytimor.mybatis.helper.single.database.test.domain.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author chenpeng
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 原生xml测试专用
     *
     * @param user
     */
    void insertOrg(@Param("entity") User user);
}
