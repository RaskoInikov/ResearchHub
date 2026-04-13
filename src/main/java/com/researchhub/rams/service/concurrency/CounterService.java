package com.researchhub.rams.service.concurrency;

public interface CounterService {
    void increment();
    int get();
    void reset();
}