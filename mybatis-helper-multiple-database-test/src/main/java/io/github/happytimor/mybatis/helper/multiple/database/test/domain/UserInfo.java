package io.github.happytimor.mybatis.helper.multiple.database.test.domain;

import io.github.happytimor.mybatis.helper.core.annotation.TableName;

import java.io.Serializable;

/**
 * @author chenpeng
 */
@TableName("user_info")
public class UserInfo implements Serializable {
    private Integer id;
    private String name;
    private Integer age;
    private Boolean married;
    private String birthday;

    public UserInfo() {
    }

    public UserInfo(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getMarried() {
        return married;
    }

    public void setMarried(Boolean married) {
        this.married = married;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
