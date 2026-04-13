package com.researchhub.rams.dto.async;

public class TaskResponseDto {

    private String taskId;
    private TaskStatus status;

    public TaskResponseDto(String taskId, TaskStatus status) {
        this.taskId = taskId;
        this.status = status;
    }

    public String getTaskId() {
        return taskId;
    }

    public TaskStatus getStatus() {
        return status;
    }
}