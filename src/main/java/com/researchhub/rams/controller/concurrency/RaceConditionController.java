package com.researchhub.rams.controller.concurrency;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.researchhub.rams.service.concurrency.demo.RaceConditionService;

@RestController
@RequestMapping("/race")
public class RaceConditionController {

    private final RaceConditionService service;

    public RaceConditionController(RaceConditionService service) {
        this.service = service;
    }

    @GetMapping("/test")
    public String runTest(
            @RequestParam(defaultValue = "50") int threads,
            @RequestParam(defaultValue = "1000") int increments
    ) throws InterruptedException {

        return service.runTest(threads, increments);
    }
}