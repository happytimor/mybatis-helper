package io.github.happytimor.mybatis.helper.core.common;

/**
 * @author chenpeng
 * @date 2019-08-21
 */
public enum SqlMethod {
    /**
     * 插入
     */
    INSERT_ONE("insert", "单条数据插入", "<script>\nINSERT INTO `%s` \n %s \n VALUES \n %s\n</script>"),

    INSERT_OR_UPDATE_WITH_UNIQUE_INDEX("insertOrUpdateWithUniqueIndex", "插入或更新", "<script>\nINSERT INTO `%s` \n %s \n VALUES \n %s\n on duplicate key update %s</script>"),

    BATCH_INSERT("batchInsert", "批量数据插入", "<script>\nINSERT INTO `%s` %s VALUES %s\n</script>"),

    /**
     * 删除
     */
    DELETE("delete", "批量删除数据", "<script>\nDELETE FROM `%s` %s\n</script>"),
    DELETE_BY_ID("deleteById", "根据ID 删除一条数据", "<script>\nDELETE FROM `%s` WHERE %s=#{%s}\n</script>"),
    DELETE_BY_ID_LIST("deleteByIdList", "根据ID集合，批量删除数据", "<script>\nDELETE FROM `%s` WHERE %s IN %s\n</script>"),

    /**
     * 修改
     */
    UPDATE("update", "自由更新", "<script>\nUPDATE `%s` %s %s\n</script>"),
    UPDATE_BY_ID("updateById", "根据ID 选择修改数据", "<script>\nUPDATE `%s` %s WHERE %s=#{%s}\n</script>"),
    BATCH_UPDATE_BY_ID("batchUpdateById", "根据主键批量更新数据", "<script>\n%s\n</script>"),

    /**
     * 查询
     */
    SELECT_BY_ID("selectById", "根据ID 查询一条数据", "SELECT %s FROM `%s` WHERE %s=#{%s}"),
    SELECT_BY_ID_LIST("selectByIdList", "根据ID集合，批量查询数据", "<script>\nSELECT %s FROM `%s` WHERE %s IN %s\n</script>"),
    SELECT_LIST("selectList", "查询满足条件所有数据", "<script>\nSELECT %s FROM `%s` %s %s %s\n</script>"),
    SELECT_COUNT("selectCount", "查询总数", "<script>\nSELECT COUNT(*) FROM `%s` %s\n</script>"),

    SELECT_ONE("selectOne", "只返回一条记录", "<script>\nSELECT %s FROM `%s` %s LIMIT 1\n</script>");

    private final String method;
    private final String desc;
    private final String sql;

    SqlMethod(String method, String desc, String sql) {
        this.method = method;
        this.desc = desc;
        this.sql = sql;
    }

    public String getMethod() {
        return method;
    }

    public String getDesc() {
        return desc;
    }

    public String getSql() {
        return sql;
    }
}
