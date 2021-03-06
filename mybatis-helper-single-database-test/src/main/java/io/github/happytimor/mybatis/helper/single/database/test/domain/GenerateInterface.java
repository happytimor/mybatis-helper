package io.github.happytimor.mybatis.helper.single.database.test.domain;

import java.util.List;

/**
 * @author chenpeng
 */
public interface GenerateInterface {
    /**
     * 批量用户生成
     *
     * @param flag 数据批次标记
     * @param userList 生成的用户数据
     */
    void test(String flag, List<User> userList);
}
