package com.researchhub.rams.service.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

@Service("atomicCounter")
public class AtomicCounterService implements CounterService {

    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void increment() {
        counter.incrementAndGet();
    }

    @Override
    public int get() {
        return counter.get();
    }

    @Override
    public void reset() {
        counter.set(0);
    }
}