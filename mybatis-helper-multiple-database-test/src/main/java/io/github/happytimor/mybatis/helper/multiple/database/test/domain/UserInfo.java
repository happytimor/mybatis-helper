package io.github.happytimor.mybatis.helper.multiple.database.test.domain;

import io.github.happytimor.mybatis.helper.core.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chenpeng
 */
@TableName("user_info")
public class UserInfo implements Serializable {
    private Integer id;
    private String name;
    private Integer age;
    private Boolean married;
    private LocalDateTime lastLoginTime;

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

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
