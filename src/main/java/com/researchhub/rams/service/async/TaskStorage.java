package com.researchhub.rams.service.async;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.researchhub.rams.dto.async.TaskStatus;

@Component
public class TaskStorage {

    private final ConcurrentHashMap<String, TaskStatus> tasks = new ConcurrentHashMap<>();

    public void put(String taskId, TaskStatus status) {
        tasks.put(taskId, status);
    }

    public TaskStatus get(String taskId) {
        return tasks.get(taskId);
    }

    public void update(String taskId, TaskStatus status) {
        tasks.put(taskId, status);
    }
}