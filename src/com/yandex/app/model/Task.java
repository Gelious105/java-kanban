package com.yandex.app.model;

import java.util.Objects;

public class Task {
    protected String name;
    protected String description;
    protected int taskId;
    protected Status status;
    protected TaskType taskType;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
        this.taskType = TaskType.TASK;
    }

    public Status getStatus() {
        return status;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskId == task.taskId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(taskId);
    }

    @Override
    public String toString() {
        return "com.yandex.app.model.Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", taskId=" + taskId +
                ", status=" + status +
                ", taskType=" + taskType +
                '}';
    }
}
