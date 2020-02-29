package io.github.happytimor.mybatis.helper.single.database.test.domain;

import java.util.List;

/**
 * @author chenpeng
 */
public interface GenerateInterface {
    /**
     * 批量用户生成
     *
     * @param list 生成的用户数据
     */
    void test(String flag, List<User> list);
}
