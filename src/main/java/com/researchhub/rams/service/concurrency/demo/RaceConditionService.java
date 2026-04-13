package com.researchhub.rams.service.concurrency.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.researchhub.rams.service.concurrency.CounterService;

@Service
public class RaceConditionService {

    private final CounterService nonAtomic;
    private final CounterService atomic;
    private final CounterService sync;

    public RaceConditionService(
            @Qualifier("nonAtomicCounter") CounterService nonAtomic,
            @Qualifier("atomicCounter") CounterService atomic,
            @Qualifier("synchronizedCounter") CounterService sync
    ) {
        this.nonAtomic = nonAtomic;
        this.atomic = atomic;
        this.sync = sync;
    }

    public String runTest(int threads, int increments) throws InterruptedException {

        int expected = threads * increments;

        nonAtomic.reset();
        atomic.reset();
        sync.reset();

        int nonAtomicResult = executeTest(nonAtomic, threads, increments);
        int atomicResult = executeTest(atomic, threads, increments);
        int syncResult = executeTest(sync, threads, increments);

        return """
                ===== RACE CONDITION TEST =====
                Threads: %d
                Increments per thread: %d
                Expected: %d

                Non-Atomic Result: %d
                Atomic Result: %d
                Synchronized Result: %d
                """.formatted(
                threads,
                increments,
                expected,
                nonAtomicResult,
                atomicResult,
                syncResult
        );
    }

    private int executeTest(CounterService counter, int threads, int increments)
            throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(threads);

        for (int i = 0; i < threads; i++) {
            executor.submit(() -> {
                for (int j = 0; j < increments; j++) {
                    counter.increment();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        return counter.get();
    }
}