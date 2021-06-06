# mybatis-helper简单介绍

[![Build Status](https://api.travis-ci.org/happytimor/mybatis-helper.svg?branch=master)](https://travis-ci.org/happytimor/mybatis-helper)
[![Codecov](https://codecov.io/gh/happytimor/mybatis-helper/branch/master/graph/badge.svg)](https://codecov.io/gh/happytimor/mybatis-helper/branch/master)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.happytimor/mybatis-helper-core.svg?label=Maven%20Central)](https://mvnrepository.com/artifact/io.github.happytimor/mybatis-helper-core)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)


## 1. 使用案例
### 1.1 普通查询
例如需要在controller提供一个分页查询的API接口,代码可以这么写:
``` java
@RequestMapping("/list")
@ResponseBody
public Object list(int pageNo, int pageSize, String name) throws Exception {
    Page<User> page = userService.selectPage(pageNo, pageSize, new SelectWrapper<User>()
            .eqNotBlank(User::getName, name)
            .eq(User::getDeleted, false)
            .orderByDesc(User::getId)
    );
    return page;
}
```
``` linux
curl http://localhost:8080/list?pageNo=1&pageSize=10
-> SELECT * FROM `user` WHERE `deleted` = false ORDER BY `id` DESC LIMIT 0,10


curl http://localhost:8080/list?pageNo=1&pageSize=10&name=name
-> SELECT * FROM `user` WHERE `name` = 'name' AND `deleted` = false ORDER BY `id` DESC LIMIT 0,10
```
### 1.2 函数查询
```
this.userService.selectMap(new SelectWrapper<User>()
                .select(SqlFunction.count(SqlFunction.distinct(User::getId), "userCount"),
                        SqlFunction.min(User::getAge, VoInfo::getMinAge),
                        SqlFunction.max(User::getAge, VoInfo::getMaxAge))
                .eq(User::getAge, 20)
        );
```
```
SELECT COUNT(DISTINCT(`id`)) AS 'userCount',MIN(`age`) AS 'minAge',MAX(`age`) AS 'maxAge' 
    FROM `user` WHERE `age` = ?
```
### 1.3 嵌套查询
```
long dbCount = this.userService.selectCount(new SelectWrapper<User>()
                    .eqNested(User::getAge, t -> t.applySelectWrapper(User.class)
                            .select(User::getAge)
                            .eq(User::getAge, randomAge)
                            .limit(1)
                    )
                    .eq(User::getFlag, flag)
            );
```
```
SELECT COUNT(*) FROM `user` WHERE `age` = (SELECT `age` FROM `user` WHERE `age` = ? LIMIT 1) AND `flag` = ?
```
### 1.4 join查询
```
List<JoinResultVO> list = this.studentService.selectObjectList(JoinResultVO.class, new SelectJoinWrapper<Student>()
                .select(Student::getName)
                .selectAs(TeacherInfo::getName, JoinResultVO::getTeacherName)
                .selectAs(Student::getName, JoinResultVO::getStudentName)
                .selectAs(CourseInfo::getName, JoinResultVO::getCourseName)
                .leftJoin(CourseInfo.class, CourseInfo::getId, Student::getId)
                .rightJoin(TeacherInfo.class, TeacherInfo::getId, CourseInfo::getTeacherId)
                .eq(Student::getDeleted, false)
                .eq(CourseInfo::getDeleted, false)
        );
```
```
SELECT t1.`name` ,t3.`name` AS 'teacherName' ,t1.`name` AS 'studentName' ,t2.`name` AS 'courseName' 
FROM `student` t1 
LEFT JOIN course_info t2 ON t2.`id` = t1.`id`
LEFT JOIN teacher_info t3 ON t3.`id` = t2.`teacher_id` 
WHERE t1.`deleted` = ? AND t2.`deleted` = ? 
```

### 1.5 自定义sql
```
this.userService.update(new UpdateWrapper<User>()
    .setDiySql(User::getAge, t -> t.applyDiySqlWrapper(User.class)
        .setExpression("{} - {} + 10")
        .setColumns(User::getId, User::getUserGrade))
    .eq(User::getFlag, flag)
);
```
```
UPDATE `user` SET `age` = (`id` - `user_grade` + 10) WHERE `flag` = ? 
```

```
this.userService.selectCount(new SelectWrapper<User>()
    .andDiySql(t -> t.applyDiySqlWrapper(User.class)
        .setExpression("{} > {} + 100")
        .setColumns(User::getId, User::getAge))
    .eq(User::getFlag, flag)
);
```
```
SELECT COUNT(*) FROM `user` WHERE (`id` > `age` + 100) AND `flag` = ? 
```
## 2.如何使用
### 2.1 引入maven依赖
``` xml
<!-- https://mvnrepository.com/artifact/io.github.happytimor/mybatis-helper-core -->
<dependency>
    <groupId>io.github.happytimor</groupId>
    <artifactId>mybatis-helper-core</artifactId>
    <version>1.0.11</version>
</dependency>
```

## 2.2 定义对象、mapper和service
``` java
public class User {
    private Integer id;
    private String realName;
    private Boolean deleted;
    //getter & setter
}
```
``` java
public interface UserMapper extends BaseMapper<User> {
    //不需要写任何方法
}
```
``` java
@Service
public class UserService extends BaseService<UserMapper, User> {
    //不需要写任何方法
}
```
## 2.3 启用mybatisHelper
``` java
@Component
public class MyHelper extends MybatisHelper implements InitializingBean {
    @Override
    public void afterPropertiesSet() {
        //指定mapper所在包路径
        this.registSingleDatabase("io.github.happytimor.test.mapper");
    }
}
```

全部配置已完成,可以愉快的增删改查了。

## 3.特性
- 不需要写任何xml文件,即可完成最基本的增删改查操作
- 没有修改任何mybatis源码，也没有覆盖原先方法,只是做了简单的方法注入
- 与既有xml文件的方法不会冲突(如果方法名已存在则跳过此方法的注入)
- 支持多数据源以及动态数据源
- 支持分表查询(例如: user_01), 支持无主键表
- 支持join操作
- 全程lambda表达式链式调用 (需要jdk1.8+)
- 自动对数据库字段进行包裹(例如: `order`)

## 4.导航
- [本地运行单元测试](http://www.ichenpeng.net/1428013)
- [注解介绍](http://www.ichenpeng.net/1428014)
- [更多介绍](api-introduce.md)

## 5.其它
有问题请发邮件到我的邮箱: ichenpeng@qq.com

## 6.powered by
[![powered by](https://github.com/happytimor/mybatis-helper/blob/master/jetbrains-variant-2.svg)](https://www.jetbrains.com/?from=mybatis-helper)
