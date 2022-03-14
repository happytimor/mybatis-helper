package io.github.happytimor.mybatis.helper.multiple.database.test.domain;

import io.github.happytimor.mybatis.helper.core.annotation.TableColumn;
import io.github.happytimor.mybatis.helper.core.annotation.TableIdType;
import io.github.happytimor.mybatis.helper.core.annotation.TableName;
import io.github.happytimor.mybatis.helper.core.common.IdType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chenpeng
 */
@TableName("user")
public class User implements Serializable {
    private Integer id;
    private String name;
    private Integer age;
    private Boolean married;
    private Integer userGrade;
    private Integer gradeOfMath;
    private Integer gradeOfScience;
    private LocalDateTime lastLoginTime;

    public User() {
    }

    public User(String name) {
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

    public Integer getUserGrade() {
        return userGrade;
    }

    public void setUserGrade(Integer userGrade) {
        this.userGrade = userGrade;
    }

    public Integer getGradeOfMath() {
        return gradeOfMath;
    }

    public void setGradeOfMath(Integer gradeOfMath) {
        this.gradeOfMath = gradeOfMath;
    }

    public Integer getGradeOfScience() {
        return gradeOfScience;
    }

    public void setGradeOfScience(Integer gradeOfScience) {
        this.gradeOfScience = gradeOfScience;
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
