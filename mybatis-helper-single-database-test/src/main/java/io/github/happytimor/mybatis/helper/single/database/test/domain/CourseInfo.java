package io.github.happytimor.mybatis.helper.single.database.test.domain;

/**
 * @author chenpeng
 */
public class CourseInfo {
    private Integer id;
    private String name;
    private Integer teacherId;
    private Boolean deleted;

    public CourseInfo(String name) {
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

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
