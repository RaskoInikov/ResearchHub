package com.researchhub.rams.service.concurrency;

import org.springframework.stereotype.Service;

@Service("nonAtomicCounter")
public class NonAtomicCounterService implements CounterService {

    private int counter = 0;

    @Override
    public void increment() {
        counter++;
    }

    @Override
    public int get() {
        return counter;
    }

    @Override
    public void reset() {
        counter = 0;
    }
}