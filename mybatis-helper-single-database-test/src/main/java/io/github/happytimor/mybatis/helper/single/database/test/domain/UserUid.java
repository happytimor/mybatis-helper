package io.github.happytimor.mybatis.helper.single.database.test.domain;

import io.github.happytimor.mybatis.helper.core.annotation.TablePrimaryKey;
import io.github.happytimor.mybatis.helper.core.annotation.TableName;

import java.util.Date;


/**
 * @author chenpeng
 */
public class UserUid {
    @TablePrimaryKey("uid")
    private Integer uid;
    private String name;
    private Integer age;
    private Boolean married;
    private Integer userGrade;
    private Date birthday;

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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}