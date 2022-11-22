package io.github.happytimor.mybatis.helper.single.database.test.domain;

import io.github.happytimor.mybatis.helper.core.annotation.TableColumn;

/**
 * @author chenpeng
 */
public class Student {
    private Integer id;
    private Integer teacherId;
    private String name;
    private Integer age;
    private Integer bestCourseId;
    @TableColumn(exist = false)
    private String courseName;
    private Boolean deleted;

    public Student() {

    }

    public Student(String name, Integer bestCourseId) {
        this.name = name;
        this.bestCourseId = bestCourseId;
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

    public Integer getBestCourseId() {
        return bestCourseId;
    }

    public void setBestCourseId(Integer bestCourseId) {
        this.bestCourseId = bestCourseId;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }
}
