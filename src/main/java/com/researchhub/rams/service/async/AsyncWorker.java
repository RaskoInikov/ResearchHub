package com.researchhub.rams.service.async;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.researchhub.rams.dto.async.TaskStatus;

@Service
public class AsyncWorker {

    private final TaskStorage taskStorage;

    public AsyncWorker(TaskStorage taskStorage) {
        this.taskStorage = taskStorage;
    }

    @Async
    public CompletableFuture<Void> processTask(String taskId) {
        try {
            Thread.sleep(20000);

            taskStorage.update(taskId, TaskStatus.FINISHED);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            taskStorage.update(taskId, TaskStatus.FAILED);

        } catch (Exception e) {
            taskStorage.update(taskId, TaskStatus.FAILED);
        }

        return CompletableFuture.completedFuture(null);
    }
}