package io.github.happytimor.mybatis.helper.single.database.test.domain;

import java.util.List;

/**
 * @author chenpeng
 */
public class TeacherInfo {
    private Integer id;
    private List<Student> studentList;

    public boolean hasStudent() {
        return studentList != null && studentList.size() > 0;
    }


    private Student firstStudent;
    private String name;
    private Boolean deleted;
    private Boolean offline;


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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getOffline() {
        return offline;
    }

    public void setOffline(Boolean offline) {
        this.offline = offline;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public Student getFirstStudent() {
        return firstStudent;
    }

    public void setFirstStudent(Student firstStudent) {
        this.firstStudent = firstStudent;
    }
}
