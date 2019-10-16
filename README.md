# mybatis-helper介绍

[![Maven Central](https://img.shields.io/maven-central/v/io.github.happytimor/mybatis-helper-core.svg?label=Maven%20Central)](https://mvnrepository.com/artifact/io.github.happytimor/mybatis-helper-core)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

## 1. 基本用法
举一个具体的例子, 例如需要在controller里面提供一个分页查询的接口
``` java
@RequestMapping("/list")
@ResponseBody
public Object list(int pageNo, int pageSize, String realName) throws Exception {
    Page<User> page = userService.selectPage(pageNo, pageSize, new SelectWrapper<User>()
            .eq(StringUtils.isNotBlank(realName), User::getRealName, realName)
            .eq(User::getDeleted, false)
            .orderByDesc(User::getId)
    );
    return page;
}
```
``` linux
curl http://localhost:8080/list?pageNo=1&pageSize=10
-> SELECT * FROM `user` WHERE `deleted` = false ORDER BY `id` DESC LIMIT 0,10


curl http://localhost:8080/list?pageNo=1&pageSize=10&realName=realName
-> SELECT * FROM `user` WHERE `real_name` = 'realName' AND `deleted` = false ORDER BY `id` DESC LIMIT 0,10

```
## 2.如何引入
### 2.1 引入maven依赖
``` xml
<dependency>
    <groupId>io.github.happytimor</groupId>
    <artifactId>mybatis-helper-core</artifactId>
    <version>1.0.1</version>
</dependency>
```

### 2.2 定义一个对象
``` java
@TableName("user")
public class User implements Serializable {
    @TablePrimaryKey(value = "id")
    private Integer id;
    private String realName;
    private Boolean deleted;
    //getter & setter
}
```
@TableName指定数据库表名, @TablePrimaryKey指定数据库表主键。

如果你的表名是一个标准下划线写法(例如: user_info), @TableName可以省略掉

如果你的表有主键且主键名称就是默认的id, @TablePrimarkKey可以省略掉

**大部分情况下, 不需要写这两个注解**。

## 2.3 定义mapper和service
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
## 2.4 启用mybatisHelper
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
全部配置已完成,可以完成步骤一的代码了。

## 3.特性
- 不需要写任何xml文件,即可完成最基本的增删改查操作
- 没有修改任何mybatis源码，也没有覆盖原先方法,只是做了简单的方法注入
- 与既有xml文件的方法不会冲突, 如果方法名已存在则跳过此方法的注入
- 支持多数据源,不需要额外引入jar包
- 支持分表查询(例如: user_01), 支持无主键表
- 全程lambda表达式链式调用 (需要jdk1.8+)


[更多介绍](api-introduce.md)