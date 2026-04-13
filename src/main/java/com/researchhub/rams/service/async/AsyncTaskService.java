package com.researchhub.rams.service.async;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.researchhub.rams.dto.async.TaskStatus;

@Service
public class AsyncTaskService {

    private final TaskStorage taskStorage;
    private final AsyncWorker asyncWorker;

    public AsyncTaskService(TaskStorage taskStorage, AsyncWorker asyncWorker) {
        this.taskStorage = taskStorage;
        this.asyncWorker = asyncWorker;
    }

    public String startTask() {
        String taskId = UUID.randomUUID().toString();

        taskStorage.put(taskId, TaskStatus.PROCESSING);

        asyncWorker.processTask(taskId);

        return taskId;
    }

    public TaskStatus getStatus(String taskId) {
        return taskStorage.get(taskId);
    }
}