# mybatis-helper-generator

mybatis-helper-generator是一个用于生成mybatis-helper CRUD代码的工具包，它基于Velocity模板引擎，可以快速生成实体类、Mapper接口、Service、Controller等代码，提高开发效率。

## 功能特性

- 支持MySQL数据库
- 基于Velocity模板引擎，易于扩展和定制
- 支持链式调用配置，使用简洁方便
- 自动生成实体类、Mapper接口、Service、Controller等代码
- 支持自定义包结构
- 支持自动去除表前缀
- 支持下划线转驼峰命名策略
- 支持覆盖已生成文件

## 快速开始

### 1. 添加依赖

在项目的pom.xml文件中添加以下依赖：

```xml
<dependency>
    <groupId>io.github.happytimor</groupId>
    <artifactId>mybatis-helper-generator</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>

<!-- 代码生成 -->
<dependency>
    <groupId>org.apache.velocity</groupId>
    <artifactId>velocity-engine-core</artifactId>
    <version>2.3</version>
    <scope>test</scope>
</dependency>
```

### 2. 编写代码生成器

创建一个Java类，编写代码生成逻辑：

```java
import io.github.happytimor.mybatis.helper.generator.CodeGenerator;

public class MyCodeGenerator {
    public static void main(String[] args) {
        CodeGenerator.create()
                // 配置数据源
                .dataSource(
                        "jdbc:mysql://127.0.0.1:3306/test?characterEncoding=UTF-8&useUnicode=true&useSSL=false&useInformationSchema=true&remarks=true",
                        "root",
                        "root"
                )
                // 全局配置
                .globalConfig(builder -> {
                    builder.author("author") // 设置作者
                            .commentDate("yyyy-MM-dd hh:mm:ss") // 注释日期格式
                            .outputDir(System.getProperty("user.dir") + "/src/main/java") // 输出目录
                            .disableOpenDir() // 禁止打开输出目录
                    ;
                })
                // 包配置
                .packageConfig(builder -> {
                    builder.parent("com.example.mybatis") // 父包名
                            .result("common.Result") // 通用返回体包名+类名(针对不同使用者各自项目规范)
                            .entity("domain.entity") // 实体包名
                            .request("domain.vo.request") // 请求对象包名
                            .response("domain.vo.response") // 响应对象包名
                            .mapper("mapper") // Mapper包名
                            .service("service") // Service包名
                            .controller("controller"); // Controller包名
                })
                // 策略配置
                .strategyConfig(builder -> {
                    builder.addInclude("table_name") // 需要生成的表名
                            .enableFileOverride() // 覆盖已生成文件
                    ;
                })
                // 执行生成
                .execute();
    }
}
```

### 3. 运行代码生成器

运行上述Java类，即可生成代码。

## 配置说明

### 1. 数据源配置

```java
.dataSource(url, username, password)
```

- `url`: 数据库连接URL
- `username`: 数据库用户名
- `password`: 数据库密码

### 2. 全局配置

```java
.globalConfig(builder -> {
    builder.author("author")
            .commentDate("yyyy-MM-dd hh:mm:ss")
            .outputDir("output/path")
            .disableOpenDir()
            .autoRemovePre(true)
            .tablePrefix("t_", "tb_");
})
```

- `author`: 作者名
- `commentDate`: 注释日期格式
- `outputDir`: 代码输出目录
- `disableOpenDir`: 是否禁止打开输出目录
- `autoRemovePre`: 是否自动去除表前缀
- `tablePrefix`: 表前缀列表，多个前缀用逗号分隔

### 3. 包配置

```java
.packageConfig(builder -> {
    builder.parent("com.example.mybatis")
            .result("common.Result")
            .entity("domain.entity")
            .request("domain.vo.request")
            .response("domain.vo.response")
            .mapper("mapper")
            .service("service")
            .controller("controller");
})
```

- `parent`: 父包名
- `result`: 通用返回体类名（相对于父包,含包名）
- `entity`: 实体类包名（相对于父包）
- `request`: 请求对象包名（相对于父包）
- `response`: 响应对象包名（相对于父包）
- `mapper`: Mapper接口包名（相对于父包）
- `service`: Service包名（相对于父包）
- `controller`: Controller包名（相对于父包）

### 4. 策略配置

```java
.strategyConfig(builder -> {
    builder.addInclude("table1", "table2")
            .naming(NamingStrategy.underline_to_camel)
            .columnNaming(NamingStrategy.underline_to_camel)
            .enableFileOverride();
})
```

- `addInclude`: 需要生成的表名，支持多个表
- `naming`: 表名命名策略
  - `underline_to_camel`: 下划线转驼峰
  - `keep`: 保持原命名
- `columnNaming`: 字段名命名策略
  - `underline_to_camel`: 下划线转驼峰
  - `keep`: 保持原命名
- `enableFileOverride`: 是否覆盖已生成文件

## 生成的文件

使用mybatis-helper-generator可以生成以下文件：

- `Entity.java`: 实体类
- `Mapper.java`: Mapper接口
- `Service.java`: Service接口
- `Controller.java`: Controller类
- `AddRequest.java`: 添加请求对象
- `EditRequest.java`: 编辑请求对象
- `QueryRequest.java`: 查询请求对象
- `Response.java`: 响应对象
- `Convertor.java`: 转换工具类

## MapStruct配置

mybatis-helper-generator生成的代码中使用了MapStruct来进行对象转换，避免了使用`BeanUtils.copyProperties`时的性能问题。在使用生成的代码前，需要在项目中添加以下配置：

### 1. 添加依赖

```xml
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>1.6.0.Beta1</version>
</dependency>
```

### 2. 配置构建插件

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.11.0</version>
            <configuration>
                <source>21</source>
                <target>21</target>
                <annotationProcessorPaths>
                    <path>
                        <groupId>org.projectlombok</groupId>
                        <artifactId>lombok</artifactId>
                        <version>1.18.30</version>
                    </path>
                    <path>
                        <groupId>org.mapstruct</groupId>
                        <artifactId>mapstruct-processor</artifactId>
                        <version>1.5.5.Final</version>
                    </path>
                </annotationProcessorPaths>
            </configuration>
        </plugin>
    </plugins>
</build>
```

## 模板定制

mybatis-helper-generator使用Velocity模板引擎，可以通过修改模板文件来定制生成的代码。模板文件位于`src/main/resources/java/`目录下：

- `entity.java.vm`: 实体类模板
- `mapper.java.vm`: Mapper接口模板
- `service.java.vm`: Service接口模板
- `controller.java.vm`: Controller类模板
- `addRequest.java.vm`: 添加请求对象模板
- `editRequest.java.vm`: 编辑请求对象模板
- `queryRequest.java.vm`: 查询请求对象模板
- `response.java.vm`: 响应对象模板
- `convertor.java.vm`: 转换工具类模板

## 示例

### 代码生成器示例

以下是一个完整的代码生成示例：

```java
import io.github.happytimor.mybatis.helper.generator.CodeGenerator;

public class CodeGeneratorTest {
    public static void main(String[] args) {
        String tableName = "user";
        
        CodeGenerator.create()
                .dataSource(
                        "jdbc:mysql://127.0.0.1:3306/test?characterEncoding=UTF-8&useUnicode=true&useSSL=false&useInformationSchema=true&remarks=true",
                        "root",
                        "root"
                )
                .globalConfig(builder -> {
                    builder.author("gaomingyuan")
                            .commentDate("yyyy-MM-dd hh:mm:ss")
                            .outputDir(System.getProperty("user.dir") + "/src/main/java")
                            .disableOpenDir()
                            .autoRemovePre(true)
                            .tablePrefix("t_");
                })
                .packageConfig(builder -> {
                    builder.parent("com.gmy.mptest")
                            .result("common.WebResponse")
                            .entity("domain.entity")
                            .request("domain.vo.request")
                            .response("domain.vo.response")
                            .mapper("mapper")
                            .service("service")
                            .controller("controller");
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tableName)
                            .enableFileOverride();
                })
                .execute();
    }
}
```

### 生成代码示例

以下是使用mybatis-helper-generator生成的代码示例：

#### Controller类

```java
package com.gmy.mptest.controller;

import com.gmy.mptest.common.WebResponse;
import com.gmy.mptest.domain.vo.request.UserAddReq;
import com.gmy.mptest.domain.vo.request.UserEditReq;
import com.gmy.mptest.domain.vo.request.UserQueryReq;
import com.gmy.mptest.domain.vo.response.UserResp;
import com.gmy.mptest.service.UserService;
import io.github.happytimor.mybatis.helper.core.metadata.Page;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;

/**
 * UserController
 *
 * @author gaomingyuan
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

  @Resource
  private UserService userService;

  /**
   * 列表
   *
   * @param userQueryReq 查询请求类
   * @return WebResponse
   */
  @GetMapping("/userPage")
  public WebResponse<Page<UserResp>> userPage(UserQueryReq userQueryReq) {
    WebResponse<Page<UserResp>> WebResponse = new WebResponse<>();
    Page<UserResp> page = this.userService.queryPage(userQueryReq);
    WebResponse.setData(page);
    return WebResponse;
  }

  /**
   * 新增
   *
   * @param userAddReq 新增请求类
   * @return WebResponse
   */
  @PostMapping("/addUser")
  public WebResponse<Object> addUser(@RequestBody UserAddReq userAddReq) {
    this.userService.addUser(userAddReq);
    return new WebResponse<>();
  }

  /**
   * 修改
   *
   * @param userEditReq 编辑请求类
   * @return WebResponse
   */
  @PostMapping("/editUser")
  public WebResponse<Object> editUser(@RequestBody UserEditReq userEditReq) {
    boolean updateSuccess = this.userService.editUser(userEditReq);
    if (!updateSuccess) {
      return WebResponse.error("更新失败");
    }
    return new WebResponse<>();
  }

  /**
   * 删除
   *
   * @param id id
   * @return WebResponse
   */
  @PostMapping("/deleteUser")
  public WebResponse<Object> deleteUser(@RequestParam("id") Integer id) {
    this.userService.deleteUser(id);
    return new WebResponse<>();
  }
}

```

#### 实体类

```java
package com.gmy.mptest.domain.entity;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户对象 user
 *
 * @author gaomingyuan
 */
@Data
@NoArgsConstructor
public class User {

  /**
   * 用户id
   */
  private Integer id;

  /**
   * 用户昵称
   */
  private String name;

  /**
   * 用户头像
   */
  private String avatar;

  /**
   * 性别 1为男性，2为女性
   */
  private Integer sex;

  /**
   * 微信openid用户标识
   */
  private String openId;

  /**
   * 在线状态 1在线 2离线
   */
  private Integer activeStatus;

  /**
   * 最后上下线时间
   */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
  private LocalDateTime lastOptTime;

  /**
   * ip信息
   */
  private String ipInfo;

  /**
   * 佩戴的徽章id
   */
  private Long itemId;

  /**
   * 使用状态 0.正常 1拉黑
   */
  private Integer status;

  /**
   * 创建时间
   */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
  private LocalDateTime gmtCreated;
}

```

#### 转换工具类

```java
package com.gmy.mptest.domain.entity.convertor;

import com.gmy.mptest.domain.entity.User;
import com.gmy.mptest.domain.vo.request.UserAddReq;
import com.gmy.mptest.domain.vo.response.UserResp;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * UserConvertor
 *
 * @author gaomingyuan
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserConvertor {

  UserConvertor INSTANCE = Mappers.getMapper(UserConvertor.class);

  /**
   * 转换为resp
   */
  public UserResp mapToUserResp(User user);

  /**
   * 转换为实体
   */
  public User mapToUser(UserAddReq addReq);

  /**
   * 转换为resp列表
   */
  public List<UserResp> mapToUserRespList(List<User> userList);
}
```

#### 添加请求类

```java
package com.gmy.mptest.domain.vo.request;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserAddRequest
 *
 * @author gaomingyuan
 */
@Data
@NoArgsConstructor
public class UserAddReq {

  /**
   * 用户昵称
   */
  private String name;

  /**
   * 用户头像
   */
  private String avatar;

  /**
   * 性别 1为男性，2为女性
   */
  private Integer sex;

  /**
   * 微信openid用户标识
   */
  private String openId;

  /**
   * 在线状态 1在线 2离线
   */
  private Integer activeStatus;

  /**
   * 最后上下线时间
   */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
  private LocalDateTime lastOptTime;

  /**
   * ip信息
   */
  private String ipInfo;

  /**
   * 佩戴的徽章id
   */
  private Long itemId;

  /**
   * 使用状态 0.正常 1拉黑
   */
  private Integer status;
}
```

#### 编辑请求类

```java
package com.gmy.mptest.domain.vo.request;

import lombok.EqualsAndHashCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserEditRequest
 *
 * @author gaomingyuan
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UserEditReq extends UserAddReq {

  /**
   * 用户id
   */
  private Integer id;
}
```

#### 查询请求类

```java
package com.gmy.mptest.domain.vo.request;

import lombok.EqualsAndHashCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserQueryRequest
 *
 * @author gaomingyuan
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UserQueryReq extends UserAddReq {

  /**
   * 页数
   */
  private Integer pageNo;
  /**
   * 条数
   */
  private Integer pageSize;
  /**
   * 用户id
   */
  private Integer id;
}
```

#### 响应类

```java
package com.gmy.mptest.domain.vo.response;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserResp
 *
 * @author gaomingyuan
 */
@Data
@NoArgsConstructor
public class UserResp {

  /**
   * 用户id
   */
  private Integer id;

  /**
   * 用户昵称
   */
  private String name;

  /**
   * 用户头像
   */
  private String avatar;

  /**
   * 性别 1为男性，2为女性
   */
  private Integer sex;

  /**
   * 微信openid用户标识
   */
  private String openId;

  /**
   * 在线状态 1在线 2离线
   */
  private Integer activeStatus;

  /**
   * 最后上下线时间
   */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
  private LocalDateTime lastOptTime;

  /**
   * ip信息
   */
  private String ipInfo;

  /**
   * 佩戴的徽章id
   */
  private Long itemId;

  /**
   * 使用状态 0.正常 1拉黑
   */
  private Integer status;

  /**
   * 创建时间
   */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
  private LocalDateTime gmtCreated;
}
```

#### Mapper接口

```java
package com.gmy.mptest.mapper;

import com.gmy.mptest.domain.entity.User;
import io.github.happytimor.mybatis.helper.core.mapper.BaseMapper;

/**
 * UserMapper
 *
 * @author gaomingyuan
 */
public interface UserMapper extends BaseMapper<User> {

}

```

#### Service实现类

```java
package com.gmy.mptest.service;

import com.gmy.mptest.domain.entity.convertor.UserConvertor;
import com.gmy.mptest.mapper.UserMapper;
import com.gmy.mptest.domain.entity.User;
import com.gmy.mptest.domain.vo.request.UserAddReq;
import com.gmy.mptest.domain.vo.request.UserEditReq;
import com.gmy.mptest.domain.vo.request.UserQueryReq;
import com.gmy.mptest.domain.vo.response.UserResp;
import io.github.happytimor.mybatis.helper.core.metadata.Page;
import io.github.happytimor.mybatis.helper.core.service.BaseService;
import io.github.happytimor.mybatis.helper.core.wrapper.SelectWrapper;
import io.github.happytimor.mybatis.helper.core.wrapper.UpdateWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserService
 *
 * @author gaomingyuan
 */
@Service
public class UserService extends BaseService<UserMapper, User> {

  /**
   * 列表
   *
   * @param userQueryReq 查询请求类
   * @return Page<UserResp> 分页数据
   */
  public Page<UserResp> queryPage(UserQueryReq userQueryReq) {
    int pageNo = userQueryReq.getPageNo();
    int pageSize = userQueryReq.getPageSize();
    Page<User> page = this.selectPage(pageNo, pageSize, new SelectWrapper<User>()
            .likeNotBlank(User::getName, StringUtils.trim(userQueryReq.getName()))
            .likeNotBlank(User::getAvatar, StringUtils.trim(userQueryReq.getAvatar()))
            .likeNotBlank(User::getOpenId, StringUtils.trim(userQueryReq.getOpenId()))
            .likeNotBlank(User::getIpInfo, StringUtils.trim(userQueryReq.getIpInfo()))
            .orderByDesc(User::getId)
    );
    List<User> records = page.getRecords();
    List<UserResp> voList = UserConvertor.INSTANCE.mapToUserRespList(records);
    Page<UserResp> pageVo = new Page<>();
    pageVo.setPageNo(page.getPageNo());
    pageVo.setPageSize(page.getPageSize());
    pageVo.setTotal(page.getTotal());
    pageVo.setRecords(voList);
    return pageVo;
  }

  /**
   * 新增
   *
   * @param userAddReq 新增请求类
   */
  public void addUser(UserAddReq userAddReq) {
    User user = UserConvertor.INSTANCE.mapToUser(userAddReq);
    this.insert(user);
  }

  /**
   * 修改
   *
   * @param userEditReq 编辑请求类
   * @return Boolean 是否更新成功
   */
  public Boolean editUser(UserEditReq userEditReq) {
    return this.update(new UpdateWrapper<User>()
            .set(User::getName, userEditReq.getName())
            .set(User::getAvatar, userEditReq.getAvatar())
            .set(User::getSex, userEditReq.getSex())
            .set(User::getOpenId, userEditReq.getOpenId())
            .set(User::getActiveStatus, userEditReq.getActiveStatus())
            .set(User::getLastOptTime, userEditReq.getLastOptTime())
            .set(User::getIpInfo, userEditReq.getIpInfo())
            .set(User::getItemId, userEditReq.getItemId())
            .set(User::getStatus, userEditReq.getStatus())
            .eq(User::getId, userEditReq.getId())
    ) > 0;
  }

  /**
   * 删除
   *
   * @param id 主键id
   */
  public void deleteUser(Integer id) {
    this.deleteById(id);
  }
}

```

## 注意事项

1. 请确保数据库连接信息正确，并且数据库中有对应的表
2. 生成的代码会覆盖已存在的文件，请谨慎使用`enableFileOverride`选项
3. 建议先在测试环境中使用，确认生成的代码符合预期后再应用到生产环境
4. 可以根据需要修改模板文件，定制生成的代码格式
5. 使用生成的代码前，请确保已正确配置MapStruct依赖和构建插件

## License

Apache 2.0
