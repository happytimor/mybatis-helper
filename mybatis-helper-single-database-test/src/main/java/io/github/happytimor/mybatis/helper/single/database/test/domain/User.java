package io.github.happytimor.mybatis.helper.single.database.test.domain;

import io.github.happytimor.mybatis.helper.core.annotation.TableColumn;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

/**
 * @author chenpeng
 */
//@TableIdType(value = IdType.DYNAMIC_GENERATE, identity = "useraaa")
public class User {
    private Integer id;
    private String name;
    @TableColumn(value = "greater_then_60s_a_b_c_3_A_NN_axiba")
    private String strangeName;
    private Integer age;
    private Integer nullableAge;
    private Boolean married;
    private Integer userGrade;
    private Integer gradeOfMath;
    private Integer gradeOfScience;
    private LocalDateTime lastLoginTime;
    /**
     * 删除标记位
     */
    private String flag;

    @TableColumn(exist = false)
    private Long sumUserGrade;

    public User() {
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }

        if (obj instanceof User) {
            User other = (User) obj;
            boolean loginNameNotSame = this.lastLoginTime == null && other.lastLoginTime != null
                    || this.lastLoginTime != null && other.lastLoginTime == null;
            if (loginNameNotSame) {
                return false;
            }

            if (this.lastLoginTime != null) {

                long timestamp1 = Date.from(this.lastLoginTime.atZone(ZoneId.systemDefault()).toInstant()).getTime();
                long timestamp2 = Date.from(other.lastLoginTime.atZone(ZoneId.systemDefault()).toInstant()).getTime();
                if (Math.abs(timestamp1 - timestamp2) > 1000) {
                    return false;
                }

            }
            return Objects.equals(this.getId(), other.getId())
                    && Objects.equals(this.getAge(), other.getAge())
                    && Objects.equals(this.getName(), other.getName())
                    && Objects.equals(this.getStrangeName(), other.getStrangeName())
                    && Objects.equals(this.getFlag(), other.getFlag())
                    && Objects.equals(this.getUserGrade(), other.getUserGrade())
                    && Objects.equals(this.getMarried(), other.getMarried());
        }

        return false;
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

    public String getStrangeName() {
        return strangeName;
    }

    public void setStrangeName(String strangeName) {
        this.strangeName = strangeName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getNullableAge() {
        return nullableAge;
    }

    public void setNullableAge(Integer nullableAge) {
        this.nullableAge = nullableAge;
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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Long getSumUserGrade() {
        return sumUserGrade;
    }

    public void setSumUserGrade(Long sumUserGrade) {
        this.sumUserGrade = sumUserGrade;
    }
}
