package io.github.happytimor.mybatis.helper.single.database.test.service;

import io.github.happytimor.mybatis.helper.core.service.IdentifierGenerator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author chenpeng
 */
@Service
public class MyIdGenerator implements IdentifierGenerator {
    AtomicLong atomicLong = new AtomicLong(5000);

    @Override
    public Number nextId(String identity) {
        return atomicLong.incrementAndGet();
    }

    @Override
    public List<Number> nextIdBatch(String identity, int batchSize) {
        long value = atomicLong.addAndGet(batchSize);
        List<Number> list = new ArrayList<>();
        for (int i = batchSize; i > 0; i--) {
            list.add(value - i + 1);
        }
        return list;
    }
}
