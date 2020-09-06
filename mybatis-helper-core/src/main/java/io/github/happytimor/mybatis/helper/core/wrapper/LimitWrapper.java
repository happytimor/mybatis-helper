package io.github.happytimor.mybatis.helper.core.wrapper;


/**
 * @author chenpeng
 */
public class LimitWrapper<T> extends AbstractWrapper<T> {


    /**
     * limit
     *
     * @param startRow 开始行
     * @param count    条数
     * @return wrapper
     */
    public final AbstractWrapper<T> limit(int startRow, int count) {
        limit = String.format("LIMIT %s, %s", startRow, count);
        return this;
    }

    /**
     * limit
     *
     * @param execute 是否执行
     * @param startRow  开始行
     * @param count     条数
     * @return wrapper
     */
    public final AbstractWrapper<T> limit(boolean execute, int startRow, int count) {
        if (execute) {
            return this.limit(startRow, count);
        }
        return this;
    }

    /**
     * limit
     *
     * @param count 条数
     * @return wrapper
     */
    public final AbstractWrapper<T> limit(int count) {
        limit = String.format("LIMIT %s", count);
        return this;
    }

    /**
     * limit
     *
     * @param execute 是否执行
     * @param count     条数
     * @return wrapper
     */
    public final AbstractWrapper<T> limit(boolean execute, int count) {
        if (execute) {
            return this.limit(count);
        }
        return this;
    }
}
