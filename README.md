# mybatis-helper
## 1.什么是mybatis-helper
无意间看到过`mybatis-plus` 这个开源项目，发现真的非常好用，完全不用写xml文件，开发起来(小项目)，速度飞快。但是简单使用的时候，发现有一些问题:
1. 多数据源支持还需要单独引入jar包,更改了原本的多数据源配置方式
2. 觉得saveOrUpdate这个方法有点搓
3. 有很不需要的插件(暂时不需要)~
4. 关键字需要自己另行处理
所以想弄一个更轻量级的辅助jar包，只需要注入基本的增删改查即可，不用像mybatis-plus那样覆盖了很多类。
实际上还加入了一些自己想要的功能，比如 唯一索引冲突的时候，改插入为更新。

> 总结: mybatis-helper是更轻量的mybatis-plus，代码很简单，很容易看得懂。

## 2. 如何使用
- 添加tableName注解
``` java
@TableName("user")
public class User{
    private int id;
    private String name;
    ...
}
```
@TableName注解将对象类和数据库表进行映射, 对象类字段自动按照驼峰格式和数据库表进行映射，如果需要自己制定映射名称，可以使用 @TableField的注解

- Mapper继承BaseMapper
``` java
public interface UserMapper extends BaseMapper<User> {

}
```
- Service继承BaseService
``` java
@Service
public class UserService extends BaseService<UserMapper, User> {

}
```

- 启用-单数据源
``` java
@Component
public class MyHelper extends MybatisHelper implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        this.registSingleDatabase("io.github.happytimor.mybatis.helper.single.database.test.mapper");
    }
}
```

- 启用-多数据源
``` java
@Component
public class MyHelper extends MybatisHelper {

}
```
``` java
//在创建 SqlSessionTemplate 时,注入方法。 具体可以参考源码
myHelper.regist(sqlSessionFactory, "io.github.happytimor.mybatis.helper.single.database.test.mapper");
```
## 3.API

### 3.1 insert
``` java
@Test
public void insert() throws Exception {
    User user = new User();
    user.setName("阿西吧");
    userService.insert(user);
    logger.info("id:{}", user.getId());
}
```
```
2019-09-04 19:34:28.348 DEBUG : ==>  Preparing: INSERT INTO `user` ( `name` ) VALUES ( ? )
2019-09-04 19:34:28.375 DEBUG : ==>  Parameters: 阿西吧(String)
2019-09-04 19:34:28.379 DEBUG : <==  Updates: 1
2019-09-04 19:34:28.401  INFO : id:10012
```

### 3.2 batchInsert
``` java
@Test
public void batchInsert() throws Exception {
    List<User> users = Arrays.asList(new User("阿西吧1"), new User("阿西吧2"));
    userService.batchInsert(users);
}
```
```
2019-09-04 19:37:52.946 DEBUG : ==>  Preparing: INSERT INTO `user` (`id`,`name`,`age`,`married`,`birthday`) VALUES (?,?,?,?,?) , (?,?,?,?,?)
2019-09-04 19:37:52.980 DEBUG : ==>  Parameters: null, 阿西吧1(String), null, null, null, null, 阿西吧2(String), null, null, null
2019-09-04 19:37:52.982 DEBUG : <==  Updates: 2
```

### 3.3 selectById
``` java
@Test
public void selectById() throws Exception {
    User user = userService.selectById(5);
    logger.info("user:{}", user);
}
```
```
2019-09-04 19:42:37.488 DEBUG : ==>  Preparing: SELECT * FROM `user` WHERE id=?
2019-09-04 19:42:37.521 DEBUG : ==>  Parameters: 5(Integer)
2019-09-04 19:42:37.552 DEBUG : <==  Total: 1
2019-09-04 19:42:37.555  INFO : user:User(id=5, name=name_4, age=77, married=false, birthday=Sun Jul 28 23:39:32 CST 2019)
```

### 3.4 selectByIdList
``` java
@Test
public void selectByIdList() throws Exception {
    List<User> userList = userService.selectByIdList(Arrays.asList(5, 6, 7, 8));
    for (User user : userList) {
        logger.info("{}", user);
    }
}
```
```
2019-09-04 19:45:37.993 DEBUG : ==>  Preparing: SELECT * FROM `user` WHERE id IN ( ? , ? , ? , ? )
2019-09-04 19:45:38.030 DEBUG : ==> Parameters: 5(Integer), 6(Integer), 7(Integer), 8(Integer)
2019-09-04 19:45:38.064 DEBUG : <==      Total: 4
2019-09-04 19:45:38.068  INFO : User(id=5, name=name_4, age=77, married=false, birthday=Sun Jul 28 23:39:32 CST 2019)
2019-09-04 19:45:38.074  INFO : User(id=6, name=name_5, age=41, married=false, birthday=Wed Aug 28 14:02:03 CST 2019)
2019-09-04 19:45:38.074  INFO : User(id=7, name=name_6, age=66, married=true, birthday=Wed Jul 31 01:14:33 CST 2019)
2019-09-04 19:45:38.074  INFO : User(id=8, name=name_7, age=64, married=true, birthday=Sat Aug 31 22:06:11 CST 2019)
```

### 3.5 selectList
``` java
@Test
public void selectList() throws Exception {
    List<User> userList = userService.selectList(new SelectWrapper<User>()
            .eq(User::getName, "阿西吧1")
            .isNotNull(User::getAge)
    );
    for (User user : userList) {
        logger.info("user:{}", user);
    }
}
```
```
2019-09-04 20:22:42.950 DEBUG : ==>  Preparing: SELECT * FROM `user` WHERE `name` = ? AND `age` IS NOT NULL
2019-09-04 20:22:42.980 DEBUG : ==> Parameters: 阿西吧1(String)
2019-09-04 20:22:43.011 DEBUG : <==      Total: 2
2019-09-04 20:22:43.014  INFO : user:User(id=11, name=阿西吧1, age=22, married=false, birthday=Sat Aug 24 10:52:02 CST 2019)
2019-09-04 20:22:43.017  INFO : user:User(id=12, name=阿西吧1, age=64, married=false, birthday=Mon Aug 26 17:36:51 CST 2019)
```
### 3.6 deleteById
``` java
@Test
public void deleteById() throws Exception {
    boolean deleteSuccess = userService.deleteById(2);
    logger.info("deleteSuccess:{}", deleteSuccess);
}
```

```
2019-09-04 19:47:55.523 DEBUG : ==>  Preparing: DELETE FROM `user` WHERE id=?
2019-09-04 19:47:55.562 DEBUG : ==> Parameters: 2(Integer)
2019-09-04 19:47:55.565 DEBUG : <==    Updates: 0
2019-09-04 19:47:55.567  INFO : deleteSuccess:false
```

### 3.7 deleteByIdList
``` java
@Test
public void deleteByIdList() throws Exception {
    int deleteCount = userService.deleteByIdList(Arrays.asList(2, 3, 4, 5));
    logger.info("deleteCount:{}", deleteCount);
}
```
```
2019-09-04 19:50:13.186 DEBUG : ==>  Preparing: DELETE FROM `user` WHERE id IN ( ? , ? , ? , ? )
2019-09-04 19:50:13.229 DEBUG : ==> Parameters: 2(Integer), 3(Integer), 4(Integer), 5(Integer)
2019-09-04 19:50:13.242 DEBUG : <==    Updates: 1
2019-09-04 19:50:13.245  INFO : deleteCount:1
```

### 3.8 delete
``` java
@Test
public void delete() throws Exception {
    int deleteCount = userService.delete(new DeleteWrapper<User>()
            .eq(User::getName, "阿西吧1")
            .gt(User::getAge, 20)
            .likeRight(User::getName, "模糊匹配")
    );
    logger.info("deleteCount:{}", deleteCount);
}
```
```
2019-09-04 20:08:40.732 DEBUG : ==>  Preparing: DELETE FROM `user` WHERE `name` = ? AND `age` > ? AND `name` LIKE ?
2019-09-04 20:08:40.771 DEBUG : ==> Parameters: 阿西吧1(String), 20(Integer), 模糊匹配%(String)
2019-09-04 20:08:40.773 DEBUG : <==    Updates: 0
2019-09-04 20:08:40.775  INFO : deleteCount:0
```

### 3.9 updateById
```
@Test
public void updateById() throws Exception {
    User user = new User("阿西吧1");
    user.setAge(22);
    user.setId(11);

    boolean updateSuccess = userService.updateById(user);
    logger.info("updateSuccess:{}", updateSuccess);
}
```
```
2019-09-04 19:58:36.619 DEBUG : ==>  Preparing: UPDATE `user` SET `id`=?, `name`=?, `age`=? WHERE id=?
2019-09-04 19:58:36.658 DEBUG : ==> Parameters: 11(Integer), 阿西吧1(String), 22(Integer), 11(Integer)
2019-09-04 19:58:36.660 DEBUG : <==    Updates: 1
2019-09-04 19:58:36.662  INFO : updateSuccess:true
```

### 3.10 batchUpdateById
``` java
@Test
public void batchUpdateById() throws Exception {
    User user1 = new User("阿西吧1");
    user1.setId(11);
    User user2 = new User("阿西吧2");
    user1.setId(12);

    int updateCount = userService.batchUpdateById(Arrays.asList(user1, user2));
    logger.info("updateCount:{}", updateCount);
}
```
```
2019-09-04 19:56:05.166 DEBUG : ==>  Preparing: update `user` SET `id`=?, `name`=? WHERE `id`=? ; update `user` SET `name`=? WHERE `id`=?
2019-09-04 19:56:05.205 DEBUG : ==> Parameters: 12(Integer), 阿西吧1(String), 12(Integer), 阿西吧2(String), null
2019-09-04 19:56:05.208 DEBUG : <==    Updates: 1
2019-09-04 19:56:05.214  INFO : updateCount:1
```

### 3.11 update
``` java
@Test
public void update() throws Exception {
    int updateCount = userService.update(new UpdateWrapper<User>()
            .set(User::getName, "李四啊")
            .set(User::getAge, 15)
            .eq(User::getName, "阿西吧1")
            .gt(User::getAge, 20)
            .likeRight(User::getName, "模糊匹配")
    );
    logger.info("updateCount:{}", updateCount);
}
```
```
2019-09-04 20:18:42.894 DEBUG : ==>  Preparing: UPDATE `user` SET `name`="李四啊", `age`=15 WHERE `name` = ? AND `age` > ? AND `name` LIKE ?
2019-09-04 20:18:42.937 DEBUG : ==> Parameters: 阿西吧1(String), 20(Integer), 模糊匹配%(String)
2019-09-04 20:18:42.939 DEBUG : <==    Updates: 0
2019-09-04 20:18:42.942  INFO : updateCount:0
```

### 3.12 insertOrUpdateWithUniqueIndex
``` java
@Test
public void updateOnDuplate() throws Exception {
    UserInfo userInfo = new UserInfo();
    //user_id有唯一索引
    userInfo.setUserId(10);
    userInfo.setName("阿西吧");
    userInfo.setAge(20);
    boolean success = userService.insertOrUpdateWithUniqueIndex(userInfo);
    logger.info("success:{}", success);
}
```
```
2019-09-04 20:28:26.543 DEBUG : ==>  Preparing: INSERT INTO `user_info` ( `user_id`, `name`, `age` ) VALUES ( ?, ?, ? ) on duplicate key update `user_id` = ?, `name` = ?, `age` = ?
2019-09-04 20:28:26.585 DEBUG : ==> Parameters: 10(Integer), 阿西吧(String), 20(Integer), 10(Integer), 阿西吧(String), 20(Integer)
2019-09-04 20:28:26.588 DEBUG : <==    Updates: 1
2019-09-04 20:28:26.606  INFO : success:true
```


TODO:
1. 自动驼峰转换
2. 自动解析表名
3. timestamp插入比较问题