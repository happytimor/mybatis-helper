package io.github.happytimor.mybatis.helper.core.wrapper;


/**
 * @author chenpeng
 */
public class LimitWrapper<T> extends AbstractWrapper<T> {


    /**
     * limit
     *
     * @param startRow start row
     * @param count    limit count
     * @return wrapper
     */
    public final AbstractWrapper<T> limit(Number startRow, Number count) {
        limit = String.format("LIMIT %s, %s", startRow, count);
        return this;
    }

    /**
     * limit
     *
     * @param execute  will execute method if `execute` is true
     * @param startRow start row
     * @param count    limit count
     * @return wrapper
     */
    public final AbstractWrapper<T> limit(boolean execute, Number startRow, Number count) {
        if (execute) {
            return this.limit(startRow, count);
        }
        return this;
    }

    /**
     * limit
     *
     * @param count limit count
     * @return wrapper
     */
    public final AbstractWrapper<T> limit(Number count) {
        limit = String.format("LIMIT %s", count);
        return this;
    }

    /**
     * limit
     *
     * @param execute will execute method if `execute` is true
     * @param count   limit count
     * @return wrapper
     */
    public final AbstractWrapper<T> limit(boolean execute, Number count) {
        if (execute) {
            return this.limit(count);
        }
        return this;
    }
}
