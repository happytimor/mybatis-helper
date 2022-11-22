package io.github.happytimor.mybatis.helper.single.database.test.domain;

/**
 * @author chenpeng
 */
//@TableIdType(value = IdType.DYNAMIC_GENERATE, identity = "useraaa")
public class User extends BaseUser {

    public User() {

    }

    public User(String name) {
        super.setName(name);
    }
}
