package io.github.happytimor.mybatis.helper.single.database.test.domain;

import io.github.happytimor.mybatis.helper.core.annotation.TableColumn;

import java.time.LocalDateTime;
import java.util.Date;


/**
 * @author chenpeng
 */
public class UserUid {
    @TableColumn(primaryKey = true, value = "uid")
    private Integer uid;
    private String name;
    @TableColumn(value = "greater_then_60s_a_b_c_3_A_NN_axiba")
    private String strangeName;
    private Integer age;
    private Boolean married;
    private Integer userGrade;
    private LocalDateTime lastLoginTime;

    public UserUid() {
    }

    public UserUid(String name) {
        this.name = name;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
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

    public Integer getUserGrade() {
        return userGrade;
    }

    public void setUserGrade(Integer userGrade) {
        this.userGrade = userGrade;
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getStrangeName() {
        return strangeName;
    }

    public void setStrangeName(String strangeName) {
        this.strangeName = strangeName;
    }
}