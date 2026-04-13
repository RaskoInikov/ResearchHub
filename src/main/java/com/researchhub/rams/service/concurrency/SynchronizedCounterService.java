package com.researchhub.rams.service.concurrency;

import org.springframework.stereotype.Service;

@Service("synchronizedCounter")
public class SynchronizedCounterService implements CounterService {

    private int counter = 0;

    @Override
    public synchronized void increment() {
        counter++;
    }

    @Override
    public synchronized int get() {
        return counter;
    }

    @Override
    public synchronized void reset() {
        counter = 0;
    }
}