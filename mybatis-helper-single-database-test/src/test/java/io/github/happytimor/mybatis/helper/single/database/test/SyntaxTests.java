package io.github.happytimor.mybatis.helper.single.database.test;

import io.github.happytimor.mybatis.helper.core.common.Operation;
import io.github.happytimor.mybatis.helper.core.function.SqlFunction;
import io.github.happytimor.mybatis.helper.core.wrapper.SelectWrapper;
import io.github.happytimor.mybatis.helper.core.wrapper.UpdateWrapper;
import io.github.happytimor.mybatis.helper.single.database.test.domain.User;
import io.github.happytimor.mybatis.helper.single.database.test.service.GenerateService;
import io.github.happytimor.mybatis.helper.single.database.test.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 语法单元测试
 *
 * @author chenpeng
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SyntaxTests {

    @Resource
    private GenerateService generateService;

    @Resource
    private UserService userService;

    /**
     * 大于
     */
    @Test
    public void gt() {
        this.generateService.generateBatch((flag, userList) -> {
            int randomAge = new Random().nextInt(100);
            long count = userList.stream().filter(user -> user.getAge() > randomAge).count();
            long dbCount = this.userService.selectCount(new SelectWrapper<User>()
                    .gt(User::getAge, randomAge)
                    .eq(User::getFlag, flag)
            );
            assert count == dbCount;
        });
    }

    /**
     * 大于等于
     */
    @Test
    public void ge() {
        this.generateService.generateBatch((flag, userList) -> {
            int randomAge = new Random().nextInt(100);
            long count = userList.stream().filter(user -> user.getAge() >= randomAge).count();
            long dbCount = this.userService.selectCount(new SelectWrapper<User>()
                    .ge(User::getAge, randomAge)
                    .eq(User::getFlag, flag)
            );
            assert count == dbCount;
        });
    }

    /**
     * 等于
     */
    @Test
    public void eq() {
        this.generateService.generateBatch((flag, userList) -> {
            int randomAge = new Random().nextInt(100);
            long count = userList.stream().filter(user -> user.getAge() == randomAge).count();
            long dbCount = this.userService.selectCount(new SelectWrapper<User>()
                    .eq(User::getAge, randomAge)
                    .eq(User::getFlag, flag)
            );
            assert count == dbCount;
        });
    }

    /**
     * eq嵌套查询
     */
    @Test
    public void eqNested() {
        this.generateService.generateBatch((flag, userList) -> {
            int randomAge = new Random().nextInt(100);
            long count = userList.stream().filter(user -> user.getAge() == randomAge).count();
            long dbCount = this.userService.selectCount(new SelectWrapper<User>()
                    .eqNested(User::getAge, t -> t.applySelectWrapper(User.class)
                            .select(User::getAge)
                            .eq(User::getAge, randomAge)
                            .limit(1)
                    )
                    .eq(User::getFlag, flag)
            );
            assert Objects.equals(count, dbCount);
        });
    }

    @Test
    public void columnOperation() {
        this.generateService.generateBatch((flag, userList) -> {
            for (User user : userList) {
                user.setUserGrade(0);
            }
            this.userService.batchUpdateById(userList);
            this.userService.update(new UpdateWrapper<User>()
                    .set(User::getUserGrade, new Operation<User>().plus(User::getGradeOfMath, User::getGradeOfScience).minus(10).plus(15))
                    .between(User::getAge, 15, 30)
            );

            List<User> dbUserList = this.userService.selectList(new SelectWrapper<User>().eq(User::getFlag, flag));
            for (User user : dbUserList) {
                if (user.getAge() >= 15 && user.getAge() <= 30) {
                    assert Objects.equals(user.getUserGrade(), user.getGradeOfMath() + user.getGradeOfScience() - 10 + 15);
                } else {
                    assert Objects.equals(user.getUserGrade(), 0);
                }
            }
        });
    }

    /**
     * 小于等于
     */
    @Test
    public void le() {
        this.generateService.generateBatch((flag, userList) -> {
            int randomAge = new Random().nextInt(100);
            long count = userList.stream().filter(user -> user.getAge() <= randomAge).count();
            long dbCount = this.userService.selectCount(new SelectWrapper<User>()
                    .le(User::getAge, randomAge)
                    .eq(User::getFlag, flag)
            );
            assert count == dbCount;
        });
    }

    /**
     * 小于
     */
    @Test
    public void lt() {
        this.generateService.generateBatch((flag, userList) -> {
            int randomAge = new Random().nextInt(100);
            long count = userList.stream().filter(user -> user.getAge() < randomAge).count();
            long dbCount = this.userService.selectCount(new SelectWrapper<User>()
                    .lt(User::getAge, randomAge)
                    .eq(User::getFlag, flag)
            );
            assert count == dbCount;
        });
    }


    /**
     * 不等于
     */
    @Test
    public void ne() {
        this.generateService.generateBatch((flag, userList) -> {
            int randomAge = new Random().nextInt(100);
            long count = userList.stream().filter(user -> user.getAge() != randomAge).count();
            long dbCount = this.userService.selectCount(new SelectWrapper<User>()
                    .ne(User::getAge, randomAge)
                    .eq(User::getFlag, flag)
            );
            assert count == dbCount;
        });
    }


    /**
     * 模糊匹配(%abc%)
     */
    @Test
    public void like() {
        this.generateService.generateBatch((flag, userList) -> {
            String randomName = String.valueOf(new Random().nextInt(500));
            long count = userList.stream().filter(user -> user.getName().contains(randomName)).count();
            long dbCount = this.userService.selectCount(new SelectWrapper<User>()
                    .like(User::getName, randomName)
                    .eq(User::getFlag, flag)
            );
            assert count == dbCount;
        });
    }

    /**
     * 左模糊匹配(abc%)
     */
    @Test
    public void likeLeft() {
        this.generateService.generateBatch((flag, userList) -> {
            String randomName = userList.get(0).getName().substring(7);
            long count = userList.stream().filter(user -> user.getName().startsWith(randomName)).count();
            long dbCount = this.userService.selectCount(new SelectWrapper<User>()
                    .likeLeft(User::getName, randomName)
                    .eq(User::getFlag, flag)
            );
            assert count == dbCount;
        });
    }

    /**
     * 右模糊匹配(%abc)
     */
    @Test
    public void likeRight() {
        this.generateService.generateBatch((flag, userList) -> {
            String name = userList.get(0).getName();
            String randomName = name.substring(name.length() - 3);
            long count = userList.stream().filter(user -> user.getName().endsWith(randomName)).count();
            long dbCount = this.userService.selectCount(new SelectWrapper<User>()
                    .likeRight(User::getName, randomName)
                    .eq(User::getFlag, flag)
            );
            assert count == dbCount;
        });
    }

    /**
     * 反向模糊匹配
     */
    public void notLike() {
        this.generateService.generateBatch((flag, userList) -> {
            String randomName = String.valueOf(new Random().nextInt(100));
            long count = userList.stream().filter(user -> !user.getName().contains(randomName)).count();
            long dbCount = this.userService.selectCount(new SelectWrapper<User>()
                    .notLike(User::getName, randomName)
                    .eq(User::getFlag, flag)
            );
            assert count == dbCount;
        });
    }

    /**
     * 区间查找
     */
    @Test
    public void between() {
        this.generateService.generateBatch((flag, userList) -> {
            int start = new Random().nextInt(50);
            int end = start + new Random().nextInt(50);

            long count = userList.stream().filter(user -> user.getAge() >= start && user.getAge() <= end).count();
            long dbCount = this.userService.selectCount(new SelectWrapper<User>()
                    .between(User::getAge, start, end)
                    .eq(User::getFlag, flag)
            );
            assert count == dbCount;
        });
    }

    /**
     * 反向区间查找
     */
    @Test
    public void notBetween() {
        this.generateService.generateBatch((flag, userList) -> {
            int start = new Random().nextInt(50);
            int end = start + new Random().nextInt(50);

            long count = userList.stream().filter(user -> user.getAge() < start || user.getAge() > end).count();
            long dbCount = this.userService.selectCount(new SelectWrapper<User>()
                    .notBetween(User::getAge, start, end)
                    .eq(User::getFlag, flag)
            );
            assert count == dbCount;
        });
    }

    /**
     * 为空
     */
    @Test
    public void isNull() {
        this.generateService.generateBatch((flag, userList) -> {
            long nullCount = userList.stream().filter(user -> user.getNullableAge() == null).count();
            long dbCount = this.userService.selectCount(new SelectWrapper<User>()
                    .isNull(User::getNullableAge)
                    .eq(User::getFlag, flag)
            );
            assert nullCount == dbCount;
        });
    }

    /**
     * 不为空
     */
    @Test
    public void isNotNull() {
        this.generateService.generateBatch((flag, userList) -> {
            long notNullCount = userList.stream().filter(user -> user.getNullableAge() != null).count();
            long dbCount = this.userService.selectCount(new SelectWrapper<User>()
                    .isNotNull(User::getNullableAge)
                    .eq(User::getFlag, flag)
            );
            assert notNullCount == dbCount;
        });
    }

    /**
     * 空字符串
     */
    @Test
    public void isEmpty() {
        this.generateService.generateBatch((flag, userList) -> {
            Random random = new Random();
            long emptyCount = 0L;
            for (User user : userList) {
                boolean setEmtpy = random.nextBoolean();
                if (setEmtpy) {
                    emptyCount++;
                }
                user.setName(setEmtpy ? "" : user.getName());
            }
            this.userService.batchUpdateById(userList);
            long dbCount = this.userService.selectCount(new SelectWrapper<User>()
                    .isEmpty(User::getName)
                    .eq(User::getFlag, flag)
            );
            assert Objects.equals(emptyCount, dbCount);
        });
    }

    /**
     * 非空字符串
     */
    @Test
    public void isNotEmpty() {
        this.generateService.generateBatch((flag, userList) -> {
            Random random = new Random();
            long notEmptyCount = 0L;
            for (User user : userList) {
                boolean setEmtpy = random.nextBoolean();
                user.setName(setEmtpy ? "" : user.getName());
                if (!StringUtils.isEmpty(user.getName())) {
                    notEmptyCount++;
                }
            }
            this.userService.batchUpdateById(userList);
            long dbCount = this.userService.selectCount(new SelectWrapper<User>()
                    .isNotEmpty(User::getName)
                    .eq(User::getFlag, flag)
            );
            assert Objects.equals(notEmptyCount, dbCount);
        });
    }

    /**
     * 嵌套in查询
     */
    @Test
    public void in() {
        this.generateService.generateBatch((flag, userList) -> {
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                list.add(new Random().nextInt(1000));
            }

            long inCount = userList.stream().filter(user -> list.contains(user.getAge())).count();
            long dbCount = this.userService.selectCount(new SelectWrapper<User>()
                    .in(User::getAge, list)
                    .eq(User::getFlag, flag)
            );
            assert inCount == dbCount;
        });
    }

    /**
     * 嵌套not in查询
     */
    @Test
    public void notIn() {
        this.generateService.generateBatch((flag, userList) -> {
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                list.add(new Random().nextInt(1000));
            }

            long notInCount = userList.stream().filter(user -> !list.contains(user.getAge())).count();
            long dbCount = this.userService.selectCount(new SelectWrapper<User>()
                    .notIn(User::getAge, list)
                    .eq(User::getFlag, flag)
            );
            assert notInCount == dbCount;
        });
    }

    /**
     * 排序测试
     */
    @Test
    public void orderBy() {
        this.generateService.generateBatch((flag, userList) -> {
            //重新查一遍数据库，需要带上id
            List<User> idUserList = this.userService.selectList(new SelectWrapper<User>()
                    .eq(User::getFlag, flag)
            );
            //内存排序(年龄升序+id降序)
            idUserList.sort((o1, o2) -> {
                if (o1.getAge().equals(o2.getAge())) {
                    return o2.getId() - o1.getId();
                }
                return o1.getAge() - o2.getAge();
            });


            List<User> dbUserList = this.userService.selectList(new SelectWrapper<User>()
                    .eq(User::getFlag, flag)
                    .orderByAsc(User::getAge)
                    .orderByDesc(User::getId)
            );

            for (int i = 0; i < dbUserList.size(); i++) {
                assert dbUserList.get(i).getId().equals(idUserList.get(i).getId());
            }
        });
    }

    /**
     * 随机排序测试
     */
    @Test
    public void orderByRandom() {
        this.generateService.generateBatch((flag, userList) -> {
            List<User> sortedList = this.userService.selectList(new SelectWrapper<User>().eq(User::getFlag, flag));
            for (int i = 0; i < sortedList.size() - 1; i++) {
                assert sortedList.get(i).getId() < sortedList.get(i + 1).getId();
            }

            List<User> randomList = this.userService.selectList(new SelectWrapper<User>().eq(User::getFlag, flag).orderByRandom());
            for (int i = 0; i < randomList.size() - 1; i++) {
                if (randomList.get(i).getId() > randomList.get(i + 1).getId()) {
                    return;
                }
            }
            throw new RuntimeException("is not random");
        });
    }


    /**
     * 分组测试
     */
    @Test
    public void group() {
        this.generateService.generateBatch((flag, userList) -> {
            Map<Integer, List<User>> ageGroup = userList.stream().collect(Collectors.groupingBy(User::getAge));

            List<Map<String, Object>> dbAgeMapList = this.userService.selectMapList(new SelectWrapper<User>().select(User::getAge, SqlFunction.count(User::getAge, "count")).eq(User::getFlag, flag).groupBy(User::getAge));
            for (Map<String, Object> dbAgeMap : dbAgeMapList) {
                Number age = (Number) dbAgeMap.get("age");
                Number count = (Number) dbAgeMap.get("count");
                assert ageGroup.get(age.intValue()).size() == count.intValue();

            }
        });
    }

}
