package com.researchhub.rams.controller.concurrency;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.researchhub.rams.service.concurrency.CounterService;

@RestController
@RequestMapping("/counter")
public class CounterController {

    private final CounterService nonAtomic;
    private final CounterService atomic;
    private final CounterService sync;

    public CounterController(
            @Qualifier("nonAtomicCounter") CounterService nonAtomic,
            @Qualifier("atomicCounter") CounterService atomic,
            @Qualifier("synchronizedCounter") CounterService sync
    ) {
        this.nonAtomic = nonAtomic;
        this.atomic = atomic;
        this.sync = sync;
    }

    @PostMapping("/non-atomic/increment")
    public void incrementNonAtomic() {
        nonAtomic.increment();
    }

    @GetMapping("/non-atomic")
    public int getNonAtomic() {
        return nonAtomic.get();
    }

    @PostMapping("/atomic/increment")
    public void incrementAtomic() {
        atomic.increment();
    }

    @GetMapping("/atomic")
    public int getAtomic() {
        return atomic.get();
    }

    @PostMapping("/sync/increment")
    public void incrementSync() {
        sync.increment();
    }

    @GetMapping("/sync")
    public int getSync() {
        return sync.get();
    }

    @PostMapping("/reset")
    public void resetAll() {
        nonAtomic.reset();
        atomic.reset();
        sync.reset();
    }
}