package com.researchhub.rams.controller.async;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.researchhub.rams.dto.async.TaskResponseDto;
import com.researchhub.rams.dto.async.TaskStatus;
import com.researchhub.rams.service.async.AsyncTaskService;

@RestController
@RequestMapping("/tasks")
public class AsyncController {

    private final AsyncTaskService asyncTaskService;

    public AsyncController(AsyncTaskService asyncTaskService) {
        this.asyncTaskService = asyncTaskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponseDto> startTask() {
        String taskId = asyncTaskService.startTask();

        return ResponseEntity.ok(
                new TaskResponseDto(taskId, TaskStatus.PROCESSING)
        );
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> getStatus(@PathVariable String taskId) {

        TaskStatus status = asyncTaskService.getStatus(taskId);

        return ResponseEntity.ok(
                new TaskResponseDto(taskId, status)
        );
    }
}