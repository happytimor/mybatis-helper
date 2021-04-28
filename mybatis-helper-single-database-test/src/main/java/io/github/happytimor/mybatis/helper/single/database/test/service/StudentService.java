package io.github.happytimor.mybatis.helper.single.database.test.service;

import io.github.happytimor.mybatis.helper.core.service.BaseService;
import io.github.happytimor.mybatis.helper.single.database.test.domain.Student;
import io.github.happytimor.mybatis.helper.single.database.test.mapper.StudentMapper;
import org.springframework.stereotype.Service;

/**
 * @author chenpeng
 */
@Service
public class StudentService extends BaseService<StudentMapper,Student> {

}
