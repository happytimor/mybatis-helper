package io.github.happytimor.mybatis.helper.single.database.test.domain;

/**
 * @author chenpeng
 */
public class TeacherInfo {
    private Integer id;
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
}
