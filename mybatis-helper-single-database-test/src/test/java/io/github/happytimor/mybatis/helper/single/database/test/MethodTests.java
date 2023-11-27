package io.github.happytimor.mybatis.helper.single.database.test;

import io.github.happytimor.mybatis.helper.core.function.SqlFunction;
import io.github.happytimor.mybatis.helper.core.metadata.Page;
import io.github.happytimor.mybatis.helper.core.wrapper.DeleteWrapper;
import io.github.happytimor.mybatis.helper.core.wrapper.SelectJoinWrapper;
import io.github.happytimor.mybatis.helper.core.wrapper.SelectWrapper;
import io.github.happytimor.mybatis.helper.core.wrapper.UpdateWrapper;
import io.github.happytimor.mybatis.helper.single.database.test.domain.CourseInfo;
import io.github.happytimor.mybatis.helper.single.database.test.domain.JoinResultVO;
import io.github.happytimor.mybatis.helper.single.database.test.domain.Student;
import io.github.happytimor.mybatis.helper.single.database.test.domain.User;
import io.github.happytimor.mybatis.helper.single.database.test.service.CourseInfoService;
import io.github.happytimor.mybatis.helper.single.database.test.service.GenerateService;
import io.github.happytimor.mybatis.helper.single.database.test.service.StudentService;
import io.github.happytimor.mybatis.helper.single.database.test.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 方法测试
 *
 * @author chenpeng
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MethodTests {

    private final static Logger logger = LoggerFactory.getLogger(MethodTests.class);
    @Resource
    private GenerateService generateService;

    @Resource
    private UserService userService;

    @Resource
    private StudentService studentService;
    @Resource
    private CourseInfoService courseInfoService;

    /**
     * 单条插入测试
     */
    @Test
    public void insert() {
        User user = this.generateService.generateOne();
        assert user.getId() == null;
        this.userService.insert(user);
        assert user.getId() != null;

        String strangeName = user.getStrangeName();
        assert strangeName != null && !strangeName.equals("");

        User dbExistsUser = this.userService.selectById(user.getId());
        assert Objects.equals(user, dbExistsUser);

        User strangeUser = this.userService.selectOne(new SelectWrapper<User>()
                .eq(User::getStrangeName, strangeName));
        assert strangeUser != null;

        this.userService.deleteById(user.getId());
        User dbUnExistsUser = this.userService.selectById(user.getId());
        assert dbUnExistsUser == null;
    }

    /**
     * 批量插入测试
     */
    @Test
    public void batchInsert() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = this.generateService.generateOne();
            assert user.getId() == null;
            list.add(user);
        }
        this.userService.batchInsert(list);

        List<Integer> idList = list.stream().map(User::getId).collect(Collectors.toList());
        this.userService.deleteByIdList(idList);
    }

    /**
     * 单主键查询
     */
    @Test
    public void selectById() {
        this.generateService.generateBatch((flag, userList) -> {
            List<User> dbUserList = this.userService.selectList(new SelectWrapper<User>().eq(User::getFlag, flag));
            for (User user : dbUserList) {
                User selectByIdUser = this.userService.selectById(user.getId());
                assert Objects.equals(user, selectByIdUser);
            }
        });
    }

    /**
     * 多主键批量查询
     */
    @Test
    public void selectByIdList() {
        this.generateService.generateBatch((flag, userList) -> {
            List<User> dbUserList = this.userService.selectList(new SelectWrapper<User>().eq(User::getFlag, flag).orderByAsc(User::getId));
            List<Integer> idList = dbUserList.stream().map(User::getId).collect(Collectors.toList());

            List<User> selectByIdListUserList = this.userService.selectByIdList(idList);
            for (int i = 0; i < selectByIdListUserList.size(); i++) {
                User selectByIdUser = selectByIdListUserList.get(i);
                User dbUser = dbUserList.get(i);
                assert Objects.equals(dbUser, selectByIdUser);
            }

            User user = dbUserList.get(0);
            List<User> users = this.userService.selectByIdList(Collections.singletonList(user.getId()));
            assert users.size() == 1 && Objects.equals(user, users.get(0));
        });
    }

    /**
     * 单主键删除
     */
    @Test
    public void deleteById() {
        this.generateService.generateBatch((flag, userList) -> {
            List<User> dbUserList = this.userService.selectList(new SelectWrapper<User>().eq(User::getFlag, flag).orderByAsc(User::getId));
            int restCount = dbUserList.size();
            for (User user : dbUserList) {
                assert this.userService.deleteById(user.getId());
                restCount--;
                Number dbRestCount = this.userService.selectCount(new SelectWrapper<User>().eq(User::getFlag, flag));
                assert dbRestCount.intValue() == restCount;
            }
        });
    }

    /**
     * 多主键批量删除
     */
    @Test
    public void deleteByIdList() {
        this.generateService.generateBatch((flag, userList) -> {
            List<User> dbUserList = this.userService.selectList(new SelectWrapper<User>().eq(User::getFlag, flag).orderByAsc(User::getId));
            Map<Integer, List<User>> ageMap = dbUserList.stream().collect(Collectors.groupingBy(User::getAge));

            int restCount = dbUserList.size();
            for (List<User> tmpDeleteList : ageMap.values()) {
                List<Integer> idList = tmpDeleteList.stream().map(User::getId).collect(Collectors.toList());
                assert this.userService.deleteByIdList(idList) == idList.size();
                restCount -= idList.size();
                Number dbRestCount = this.userService.selectCount(new SelectWrapper<User>().eq(User::getFlag, flag));
                assert dbRestCount.intValue() == restCount;
            }
        });
    }

    /**
     * 单主键更新测试
     */
    @Test
    public void updateById() {
        this.generateService.generateBatch((flag, userList) -> {
            List<User> dbUserList = this.userService.selectList(new SelectWrapper<User>().eq(User::getFlag, flag).orderByAsc(User::getId));
            for (User user : dbUserList) {
                User newUser = this.generateService.generateOne(flag);
                newUser.setId(user.getId());
                BeanUtils.copyProperties(newUser, user);
                this.userService.updateById(user);
            }

            List<User> updateUserList = this.userService.selectList(new SelectWrapper<User>().eq(User::getFlag, flag).orderByAsc(User::getId));
            for (int i = 0; i < updateUserList.size(); i++) {
                assert Objects.equals(updateUserList.get(i), dbUserList.get(i));
            }
        });
    }

    /**
     * 多主键更新测试
     */
    @Test
    public void batchUpdateById() {
        this.generateService.generateBatch((flag, userList) -> {
            List<User> dbUserList = this.userService.selectList(new SelectWrapper<User>().eq(User::getFlag, flag).orderByAsc(User::getId));
            Map<Integer, List<User>> ageMap = dbUserList.stream().collect(Collectors.groupingBy(User::getAge));
            for (List<User> tmpUserList : ageMap.values()) {
                for (User user : tmpUserList) {
                    User newUser = this.generateService.generateOne(flag);
                    newUser.setId(user.getId());
                    BeanUtils.copyProperties(newUser, user);
                }
                this.userService.batchUpdateById(tmpUserList);
            }

            List<User> updateUserList = this.userService.selectList(new SelectWrapper<User>().eq(User::getFlag, flag).orderByAsc(User::getId));
            updateUserList.sort(Comparator.comparing(User::getId));
            for (int i = 0; i < updateUserList.size(); i++) {
                assert Objects.equals(updateUserList.get(i), dbUserList.get(i));
            }
        });
    }

    /**
     * 条数查询测试
     */
    @Test
    public void selectCount() {
        this.generateService.generateBatch(((flag, userList) -> {
            long dbCount = this.userService.selectCount(new SelectWrapper<User>().eq(User::getFlag, flag));
            assert userList.size() == dbCount;

            List<User> dbUserList = this.userService.selectList(new SelectWrapper<User>().eq(User::getFlag, flag));
            for (User user : dbUserList) {
                long onlyOne = this.userService.selectCount(new SelectWrapper<User>()
                        .eq(User::getId, user.getId())
                        .eq(User::getAge, user.getAge())
                        .eq(User::getFlag, user.getFlag())
                        .eq(User::getName, user.getName())
                        .eq(User::getLastLoginTime, user.getLastLoginTime())
                        .eq(User::getMarried, user.getMarried())
                        .eq(User::getUserGrade, user.getUserGrade())
                );
                assert onlyOne == 1;
            }
        }));
    }

    /**
     * 唯一索引冲突更新测试
     */
    @Test
    public void insertOrUpdateWithUniqueIndex() {
        this.generateService.generateBatch((flag, userList) -> {
            List<User> dbUserList = this.userService.selectList(new SelectWrapper<User>().eq(User::getFlag, flag));
            for (User user : dbUserList) {
                //唯一索引冲突更新测试(id冲突)
                User newUser = this.generateService.generateOne(flag);
                newUser.setId(user.getId());
                this.userService.insertOrUpdateWithUniqueIndex(newUser);

                User dbNewUser = this.userService.selectById(user.getId());
                assert Objects.equals(dbNewUser, newUser);
            }
        });
    }

    /**
     * 单值查询
     */
    @Test
    public void selectSingleValue() {
        this.generateService.generateBatch((flag, userList) -> {
            int maxAge = -1;
            int sumAge = 0;
            for (User user : userList) {
                if (maxAge < user.getAge()) {
                    maxAge = user.getAge();
                }
                sumAge += user.getAge();
            }

            Number dbCount = this.userService.selectSingleValue(new SelectWrapper<User>()
                    .select(SqlFunction.count(User::getId))
                    .eq(User::getFlag, flag));
            assert dbCount.intValue() == userList.size();

            Number dbMaxAge = this.userService.selectSingleValue(new SelectWrapper<User>()
                    .select(SqlFunction.max(User::getAge), SqlFunction.sum(User::getAge, User::getAge))
                    .eq(User::getFlag, flag));
            assert dbMaxAge.intValue() == maxAge;

            Number dbSumAge = this.userService.selectSingleValue(new SelectWrapper<User>()
                    .select(SqlFunction.sum(User::getAge))
                    .eq(User::getFlag, flag));
            assert dbSumAge.intValue() == sumAge;

        });
    }

    /**
     * selectObjectList 针对返回基本数据类型的返回测试
     */
    @Test
    public void selectObjectListBasicTypeTest() {
        this.generateService.generateBatch(1000, (flag, userList) -> {
            List<String> nameList = userService.selectObjectList(String.class, new SelectWrapper<User>()
                    .select(SqlFunction.distinct(User::getName))
                    .eq(User::getFlag, flag)
            );
            List<Integer> ageList = userService.selectObjectList(Integer.class, new SelectWrapper<User>()
                    .select(User::getAge)
                    .eq(User::getFlag, flag)
            );
            List<LocalDateTime> timeList = userService.selectObjectList(LocalDateTime.class, new SelectWrapper<User>()
                    .select(User::getLastLoginTime)
                    .eq(User::getFlag, flag)
            );

            assert nameList.size() > 0;
            assert ageList.size() > 0;
            assert timeList.size() > 0;
        });
    }

    /**
     * 对象转换测试
     */
    @Test
    public void selectObjectList() {
        generateService.generateBatch(1000, (flag, userList) -> {
            Map<Integer, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, x -> x));
            List<User> objectList = userService.selectObjectList(User.class, new SelectWrapper<User>()
                    .eq(User::getFlag, flag)
            );
            for (User user : objectList) {
                assert user.equals(userMap.get(user.getId()));
            }
        });

        generateService.generateBatch(1000, (flag, userList) -> {
            List<Integer> integerList = userService.selectObjectList(Integer.class, new SelectWrapper<User>()
                    .select(User::getAge)
                    .eq(User::getFlag, flag)
            );
            assert integerList.size() == 1000;

            List<String> stringList = userService.selectObjectList(String.class, new SelectWrapper<User>()
                    .select(User::getName)
                    .eq(User::getFlag, flag)
            );
            assert stringList.size() == 1000;

            List<LocalDateTime> localDateTimeList = userService.selectObjectList(LocalDateTime.class, new SelectWrapper<User>()
                    .select(User::getLastLoginTime)
                    .eq(User::getFlag, flag)
            );
            assert localDateTimeList.size() == 1000;
        });
    }

    @Test
    public void groupTest() {
        generateService.generateBatch(5000, (flag, userList) -> {
            Random random = new Random();
            int randomAgeStart = random.nextInt(5);
            int randomAgeEnd = randomAgeStart + 20;

            int randomUserGradeStart = random.nextInt(50);
            int randomUserGradeEnd = randomUserGradeStart + 20;

            Page<User> page = this.userService.selectPage(1, 10, new SelectWrapper<User>()
                    .select(User::getAge, SqlFunction.sum(User::getUserGrade, User::getSumUserGrade))
                    .between(User::getAge, randomAgeStart, randomAgeEnd)
                    .between(User::getUserGrade, randomUserGradeStart, randomUserGradeEnd)
                    .groupBy(User::getAge)
            );

            Map<Integer, Long> verifyData = new HashMap<>();
            for (int age = randomAgeStart; age <= randomAgeEnd; age++) {
                Number sumUserGrade = this.userService.selectSingleValue(new SelectWrapper<User>()
                        .select(SqlFunction.sum(User::getUserGrade))
                        .eq(User::getAge, age)
                        .between(User::getUserGrade, randomUserGradeStart, randomUserGradeEnd)
                );
                if (sumUserGrade == null) {
                    continue;
                }
                verifyData.put(age, sumUserGrade.longValue());
            }
            List<User> records = page.getRecords();
            for (User record : records) {
                assert Objects.equals(verifyData.get(record.getAge()), record.getSumUserGrade());
            }
        });
    }

    @Test
    public void selectObject() {
        generateService.generateBatch(1000, (flag, userList) -> {
            for (User user : userList) {
                User object = userService.selectObject(User.class, new SelectWrapper<User>()
                        .eq(User::getFlag, flag)
                        .eq(User::getId, user.getId())
                );
                assert user.equals(object);
            }
        });
    }

    @Test
    public void selectOneOrderBy() {
        generateService.generateBatch(1000, (flag, userList) -> {
            userList.sort(Comparator.comparingInt(User::getId));
            User user = userService.selectOne(new SelectWrapper<User>()
                    .eq(User::getFlag, flag)
                    .orderByDesc(User::getId)
            );
            assert Objects.equals(user.getId(), userList.get(userList.size() - 1).getId());
        });
    }

    @Test
    public void joinSelectAll() {
        try {
            int studentCount = 50, courseCount = 10;
            //generate 10 course
            List<CourseInfo> courseInfoList = Stream.iterate(1, num -> num + 1)
                    .limit(courseCount)
                    .map(id -> "course_" + id)
                    .map(CourseInfo::new).collect(Collectors.toList());
            this.courseInfoService.batchInsert(courseInfoList);

            List<Student> studentList = Stream.iterate(1, num -> num + 1)
                    .limit(studentCount)
                    .map(id -> "stu_" + id)
                    .map(name -> {
                        int index = ThreadLocalRandom.current().nextInt(courseInfoList.size());
                        CourseInfo courseInfo = courseInfoList.get(index);
                        return new Student(name, courseInfo.getId());
                    }).collect(Collectors.toList());
            this.studentService.batchInsert(studentList);


            List<Student> list = this.studentService.selectObjectList(Student.class, new SelectJoinWrapper<Student>()
                    .selectAll(Student.class)
                    .selectAs(CourseInfo::getName, Student::getCourseName)
                    .leftJoin(CourseInfo.class, CourseInfo::getId, Student::getBestCourseId)
                    .and(t -> t.ge(CourseInfo::getId, 0).or().lt(CourseInfo::getId, 100))
                    .ge(CourseInfo::getId, 0)
                    .ge(Student::getId, 0)
                    .orderByAsc(CourseInfo::getId)
                    .orderByDesc(Student::getId)
            );
            assert list.size() > 0;
        } finally {
            this.courseInfoService.delete(new DeleteWrapper<>());
            this.studentService.delete(new DeleteWrapper<>());
        }
    }

    @Test
    public void joinWithGroup() {
        try {
            int studentCount = 50, courseCount = 10;
            //generate 10 course
            List<CourseInfo> courseInfoList = Stream.iterate(1, num -> num + 1)
                    .limit(courseCount)
                    .map(id -> "course_" + id)
                    .map(CourseInfo::new).collect(Collectors.toList());
            this.courseInfoService.batchInsert(courseInfoList);

            List<Student> studentList = Stream.iterate(1, num -> num + 1)
                    .limit(studentCount)
                    .map(id -> "stu_" + id)
                    .map(name -> {
                        int index = ThreadLocalRandom.current().nextInt(courseInfoList.size());
                        CourseInfo courseInfo = courseInfoList.get(index);
                        return new Student(name, courseInfo.getId());
                    }).collect(Collectors.toList());
            this.studentService.batchInsert(studentList);

            this.studentService.selectJoinPage(Student.class, 1, 10, new SelectJoinWrapper<Student>()
                    .select(SqlFunction.sum(Student::getAge))
                    .leftJoin(CourseInfo.class, CourseInfo::getId, Student::getBestCourseId)
                    .groupBy(Student::getName)

            );
        } finally {
            this.courseInfoService.delete(new DeleteWrapper<>());
            this.studentService.delete(new DeleteWrapper<>());
        }
    }

    @Test
    public void joinPage() {
        try {
            int studentCount = 50, courseCount = 10;
            Map<String, String> bestCourseMatch = new HashMap<>();
            //generate 10 course
            List<CourseInfo> courseInfoList = Stream.iterate(1, num -> num + 1)
                    .limit(courseCount)
                    .map(id -> "course_" + id)
                    .map(CourseInfo::new).collect(Collectors.toList());
            this.courseInfoService.batchInsert(courseInfoList);

            List<Student> studentList = Stream.iterate(1, num -> num + 1)
                    .limit(studentCount)
                    .map(id -> "stu_" + id)
                    .map(name -> {
                        int index = ThreadLocalRandom.current().nextInt(courseInfoList.size());
                        CourseInfo courseInfo = courseInfoList.get(index);
                        bestCourseMatch.put(name, courseInfo.getName());
                        return new Student(name, courseInfo.getId());
                    }).collect(Collectors.toList());
            this.studentService.batchInsert(studentList);


            Page<JoinResultVO> page = this.studentService.selectJoinPage(JoinResultVO.class, 1, 15, new SelectJoinWrapper<Student>()
                    .selectAs(Student::getName, JoinResultVO::getStudentName)
                    .selectAs(CourseInfo::getName, JoinResultVO::getCourseName)
                    .leftJoin(CourseInfo.class, CourseInfo::getId, Student::getBestCourseId)
            );
            assert page.getTotal() == studentCount;
            for (JoinResultVO joinResultVO : page.getRecords()) {
                assert bestCourseMatch.get(joinResultVO.getStudentName()).equals(joinResultVO.getCourseName());
            }
        } finally {
            this.courseInfoService.delete(new DeleteWrapper<>());
            this.studentService.delete(new DeleteWrapper<>());
        }
    }

    @Test
    public void forUpdateTest() {
        this.generateService.generateBatch((flag, userList) -> {
            List<User> list = this.userService.selectList(new SelectWrapper<User>()
                    .eq(User::getFlag, flag)
                    .orderByAsc(User::getId)
                    .forUpdate()
            );
            assert list.size() == userList.size();
        });
    }

    /**
     * 分页测试
     */
    @Test
    public void selectPage() {
        this.generateService.generateBatch((flag, userList) -> {
            List<User> dbUserList = this.userService.selectList(new SelectWrapper<User>()
                    .eq(User::getFlag, flag)
                    .orderByAsc(User::getId)
            );

            int pageSize = 10;
            int pageNo = 0;
            while (true) {
                pageNo++;
                Page<User> page = this.userService.selectPage(pageNo, pageSize, new SelectWrapper<User>()
                        .eq(User::getFlag, flag)
                        .orderByAsc(User::getId));
                assert page.getTotal() == userList.size();

                List<User> records = page.getRecords();
                if (CollectionUtils.isEmpty(records)) {
                    break;
                }
                //数据库分页和内存分页做比较
                long sum = records.stream().map(User::getId).reduce(0, Integer::sum);
                List<User> partList = dbUserList.subList((pageNo - 1) * pageSize, Math.min(pageNo * pageSize, userList.size()));
                long checkSum = partList.stream().map(User::getId).reduce(0, Integer::sum);
                assert sum == checkSum;
            }
        });
    }

    @Test
    public void updateLimit() {
        this.generateService.generateBatch((flag, userList) -> {
            int randomSize = new Random().nextInt(userList.size() - 1) + 1;
            int randomAge = new Random().nextInt(100);
            this.userService.update(new UpdateWrapper<User>()
                    .set(User::getAge, randomAge)
                    .eq(User::getFlag, flag)
            );

            this.userService.update(new UpdateWrapper<User>()
                    .set(User::getAge, randomAge + 10)
                    .eq(User::getFlag, flag)
                    .limit(randomSize)
            );
            Number updateCount = this.userService.selectCount(new SelectWrapper<User>()
                    .eq(User::getFlag, flag)
                    .eq(User::getAge, randomAge + 10)
            );
            assert Objects.equals(updateCount.intValue(), randomSize);
        });
    }

    @Test
    public void deleteLimit() {
        this.generateService.generateBatch((flag, userList) -> {
            int deleteCount = new Random().nextInt(userList.size() - 1) + 1;
            this.userService.delete(new UpdateWrapper<User>()
                    .eq(User::getFlag, flag)
                    .limit(deleteCount)
            );

            Number restCount = this.userService.selectCount(new SelectWrapper<User>()
                    .eq(User::getFlag, flag)
            );
            assert Objects.equals(restCount.intValue(), userList.size() - deleteCount);
        });
    }

    @Test
    public void havingTest() {
        this.generateService.generateBatch((flag, userList) -> {
            long sumGradeOfMathOfMarried = 0L;
            long sumGradeOfMathOfUnMarried = 0L;

            long sumGradeOfScienceOfMarried = 0L;
            long sumGradeOfScienceOfUnMarried = 0L;

            for (User user : userList) {
                if (user.getMarried()) {
                    sumGradeOfMathOfMarried += user.getGradeOfMath();
                    sumGradeOfScienceOfMarried += user.getGradeOfScience();
                } else {
                    sumGradeOfMathOfUnMarried += user.getGradeOfMath();
                    sumGradeOfScienceOfUnMarried += user.getGradeOfScience();
                }
            }

            long avgGradeOfMath = (sumGradeOfMathOfMarried + sumGradeOfMathOfUnMarried) / 2;
            long maxGradeOfMath = Math.max(sumGradeOfMathOfMarried, sumGradeOfMathOfUnMarried);
            int married = maxGradeOfMath == sumGradeOfMathOfMarried ? 1 : 0;
            long minAvgGradeOfScience = Math.min(sumGradeOfScienceOfMarried, sumGradeOfScienceOfUnMarried) / userList.size();
            List<Map<String, Object>> mapList = this.userService.selectMapList(new SelectWrapper<User>()
                    .select(User::getMarried, SqlFunction.sum(User::getGradeOfMath, User::getGradeOfMath),
                            SqlFunction.avg(User::getGradeOfScience, User::getGradeOfScience))
                    .eq(User::getFlag, flag)
                    .groupBy(User::getMarried)
                    .havingGt(SqlFunction.sum(User::getGradeOfMath), avgGradeOfMath)
                    .havingGe(SqlFunction.avg(User::getGradeOfScience), minAvgGradeOfScience)
            );
            for (Map<String, Object> stringObjectMap : mapList) {
                assert maxGradeOfMath == ((Number) (stringObjectMap.get("gradeOfMath"))).longValue();
                assert (int) stringObjectMap.get("married") == married;
            }
        });
    }
}
