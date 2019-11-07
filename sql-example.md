# sql example

## 主键查询
> SELECT * FROM `user` WHERE `id` = 1;

``` java
User user = userService.selectById(1);
```

## 字段与字段进行比较
> SELECT * FROM `user` WHERE `id` = `parent_id`

```java
userService.selectList(new SelectWrapper<User>()
                .eq(User::getId, User::getParentId)
        );
```


## 自定义更新
> UPDATE `user` SET `name` = 'zhangsan', `age` = 3 WHERE `id` = 3;

```java
userService.update(new UpdateWrapper<User>()
                .set(User::getName, "zhangsan")
                .set(User::getAge, 3)
                .eq(User::getId, 3)
        );
```

## 关联查询
> SELECT `id` FROM `user` WHERE `name` LIKE '%zhangsan%' AND `id` IN (SELECT `user_id` from `login_info` WHERE `last_login_date` >= '2019-11-11')

```java
userService.selectList(new SelectWrapper<User>()
                .select(User::getId)
                .like(User::getName, "zhangsan")
                .in(User::getAge, t->t.applySelectWrapper(LoginInfo.class).select(LoginInfo::getUserId).gt(User::getLastLoginDate, new Date))
        );
```