package io.github.happytimor.mybatis.helper.core.common;

/**
 * @author chenpeng
 */
public enum SqlMethod {
    /**
     * insert one row
     */
    INSERT_ONE("insert", "<script>\nINSERT INTO `%s` \n %s \n VALUES \n %s\n</script>"),
    /**
     * insert or update one row(update will replace insert when unique index conflict)
     */
    INSERT_OR_UPDATE_WITH_UNIQUE_INDEX("insertOrUpdateWithUniqueIndex", "<script>\nINSERT INTO `%s` \n %s \n VALUES \n %s\n on duplicate key update %s</script>"),
    /**
     * batch insert rows
     */
    BATCH_INSERT("batchInsert", "<script>\nINSERT INTO `%s` %s VALUES %s\n</script>"),
    /**
     * batch delete rows
     */
    DELETE("delete", "<script>\nDELETE FROM `%s` %s %s %s\n</script>"),
    /**
     * delete row with id
     */
    DELETE_BY_ID("deleteById", "<script>\nDELETE FROM `%s` WHERE %s=#{%s}\n</script>"),
    /**
     * delete row with id list
     */
    DELETE_BY_ID_LIST("deleteByIdList", "<script>\nDELETE FROM `%s` WHERE %s IN %s\n</script>"),
    /**
     * update rows
     */
    UPDATE("update", "<script>\nUPDATE `%s` %s %s %s %s\n</script>"),
    /**
     * update row with id
     */
    UPDATE_BY_ID("updateById", "<script>\nUPDATE `%s` %s WHERE %s=#{%s}\n</script>"),
    /**
     * batch update row with id
     */
    BATCH_UPDATE_BY_ID("batchUpdateById", "<script>\n%s\n</script>"),
    /**
     * query one row with id
     */
    SELECT_BY_ID("selectById", "SELECT %s FROM `%s` WHERE %s=#{%s}"),
    /**
     * query batch row with id
     */
    SELECT_BY_ID_LIST("selectByIdList", "<script>\nSELECT %s FROM `%s` WHERE %s IN %s\n</script>"),
    /**
     * query rows with customized condition
     */
    SELECT_LIST("selectList", "<script>\nSELECT %s FROM `%s` %s %s %s %s %s %s %s %s\n</script>"),
    /**
     * query one row with customized condition
     */
    SELECT_SINGLE_VALUE("selectSingleValue", "<script>\nSELECT %s FROM `%s` %s %s %s %s %s %s %s %s\n</script>"),
    /**
     * query object map
     */
    SELECT_MAP_LIST("selectMapList", "<script>\nSELECT %s FROM `%s`%s %s %s %s %s %s %s %s\n</script>"),
    /**
     * query count with condition
     */
    SELECT_COUNT("selectCount", "<script>\n%sSELECT COUNT(*) FROM `%s` %s %s %s %s%s%s\n</script>"),
    /**
     * query one row
     */
    SELECT_ONE("selectOne", "<script>\nSELECT %s FROM `%s` %s %s %s %s %s %s LIMIT 1 %s\n</script>"),
    /**
     * query map object
     */
    SELECT_MAP("selectMap", "<script>\nSELECT %s FROM `%s` %s %s %s %s %s %s %s %s\n</script>");

    private final String method;
    private final String sql;

    SqlMethod(String method, String sql) {
        this.method = method;
        this.sql = sql;
    }

    public String getMethod() {
        return method;
    }


    public String getSql() {
        return sql;
    }
}
