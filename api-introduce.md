# mybatis-helper-api

## 1. 方法简介
mybatis-helper包含了三种基础service
- BaseService: 适用于单表有主键的表
- MultipleTableService: 适用于多表有主键的表
- NoPrimaryKeyService: 适用于无主键表

|  方法名   |  方法描述   | BaseService | MultipleTableService | NoPrimaryKeyService |
| --- | --- | --- | --- | --- | --- |
|   insert  |  单条插入   |  支持  |  支持   |  支持   |
|   batchInsert  |   批量插入  |  支持  |  支持   |  支持   |
|   deleteById  |   根据主键单条删除  |  支持  |  支持   | 不支持    |
|   deleteByIdList  |   根据主键批量删除  |  支持  |  支持   |  不支持   |
|   delete  |   自定义条件删除  |  支持  |   支持  |  支持   |
|   updateById  |   根据主键单条更新  |  支持  |  支持   |  不支持   |
|   batchUpdateById  |   根据主键批量更新  |   支持 |  支持   |  不支持   |
|   update  |   自定义条件更新  |  支持  |  支持   |    支持 |
|   selectById  |   根据主键查询  | 支持   |  支持   |  不支持   |
|   selectByIdList  |    根据主键批量查询 |   支持 |  支持   |  不支持   |
|   selectList  |   自定义条件查询  |   支持 |  支持   |   支持  |
|   selectPage  |   分页查询  |   支持 |  支持   |   支持  |
|   selectCount  |   自定义条件查询总数  |   支持 |   支持  |    支持 |
|   selectOne  |  自定义条件查询一条数据   |  支持  |   支持  |  支持   |
|   insertOrUpdateWithUniqueIndex  |  存在唯一索引前提下, 插入或更新   |   支持 |   支持  |   支持  |
正常是使用是这样的
``` java
@Service
public class UserService extends BaseService<UserMapper, User> {
    //不需要写任何方法
}
```
这样的话, UserService就能继承BaseService的所有方法了。

## 2. wrapper方法介绍
|   方法名  |  方法介绍   | 举例 | 备注 |
| --- | --- |  --- | --- |
|  eq  |  和 = 等价 |  .eq(User::getId,1) == > id = 1 ||
|  gt  |  和 > 等价  |  .gt(User::getId,1) == > id > 1  ||
|  ge  | 和 >= 等价   |  .ge(User::getId,1) == > id >= 1  ||
|  lt  | 和 < 等价  |   .lt(User::getId,1) == > id < 1  ||
|  le  |  和 <= 等价  |  .le(User::getId,1) == > id <= 1   ||
|  ne  |  和 != 等价  |  .ne(User::getId,1) == > id != 1   ||
|  in  |  in查询  |  .in(User::getId,Arrays.asList(1,2,3,4)) == > id in (1,2,3,4)  |如果只有一个值,则会变成 = 查询|
|  notIn  |  not in 查询  |  .notIn(User::getId,Arrays.asList(1,2,3,4)) == > id not in (1,2,3,4)  |如果只有一个值,则会变成 != 查询|
|  like  |  like查询  |  .like(User::getName,"helper") == > name like '%helper%'   ||
|  likeLeft  |  左模糊  |  .likeLeft(User::getName,"helper") == > name like '%helper'   ||
|  likeRight |  右模糊  |  .likeRight(User::getName,"helper") == > name like 'helper%'   ||
|  notLike |  反向模糊  |  .notLike(User::getName,"helper") == > name not like '%helper%'  | |
|  or  |  or连接符  |  .eq(User::getId,1).or().eq(User::getId,2) => id=1 or id=2 ||
|  and  |  and嵌套查询  | .and(t->t.eq(User::getId,1).or().eq(User::getId,2))) => and(id=1 or id=2)  ||
|  between  |  between查询  |  .between(User::getId,1,2)  => id between 1 and 2 ||
|  notBetween  |  not between查询  |   .notBetween(User::getId,1,2)  => id not between 1 and 2  ||
|  isNull  |  is null判断  |  .isNull(User::getId) => id is null ||
|  isNotNull  |  is not null判断  |  .isNotNull(User::getId) => id is not null  ||

举一个具体调用的例子:
``` java
int updateCount = userService.update(new UpdateWrapper<User>().set(User::getAge, 12).set(User::getMarried, true).set(User::getBirthday, "2019-09-09")
        .eq(User::getName, newName + System.currentTimeMillis())
        .gt(User::getAge, 12)
        .lt(User::getAge, 13)
        .isNotNull(User::getMarried)
        .isNull(User::getAge)
        .between(User::getAge, 12, 15)
        .notBetween(User::getAge, 14, 19)
        .like(User::getName, "mybatis")
        .notLike(User::getName, "mybatis")
        .likeLeft(User::getName, "mybatis")
        .likeRight(User::getName, "mybatis")
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