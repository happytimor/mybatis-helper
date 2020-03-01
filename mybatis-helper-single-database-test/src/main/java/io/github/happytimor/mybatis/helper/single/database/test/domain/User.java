package io.github.happytimor.mybatis.helper.single.database.test.domain;

import java.util.Date;
import java.util.Objects;

/**
 * @author chenpeng
 */
public class User {
    private Integer id;
    private String name;
    private Integer age;
    private Integer nullableAge;
    private Boolean married;
    private Integer userGrade;
    private Date birthday;
    /**
     * 删除标记位
     */
    private String flag;

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
            if (this.birthday == null && other.birthday != null || this.birthday != null && other.birthday == null) {
                return false;
            }

            if (this.birthday != null && Math.abs(this.birthday.getTime() - other.getBirthday().getTime()) >= 1000) {
                return false;
            }
            return Objects.equals(this.getId(), other.getId())
                    && Objects.equals(this.getAge(), other.getAge())
                    && Objects.equals(this.getName(), other.getName())
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
