package io.github.happytimor.mybatis.helper.single.database.test.mapper;

import io.github.happytimor.mybatis.helper.core.mapper.BaseMapper;
import io.github.happytimor.mybatis.helper.single.database.test.domain.Student;
import io.github.happytimor.mybatis.helper.single.database.test.domain.TeacherInfo;
import io.github.happytimor.mybatis.helper.single.database.test.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenpeng
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 原生xml测试专用
     *
     * @param user
     */
    void insertOrg(@Param("entity") User user);

    void batchInsertDemo(String tableNum, List<User> list);

    List<TeacherInfo> selectTeacherInfo();

    List<Student> selectStudentByTeacherId(int teacherId);
}
