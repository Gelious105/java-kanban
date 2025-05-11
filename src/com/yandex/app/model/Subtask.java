package com.yandex.app.model;

public class Subtask extends Task {
    protected int epicParentId;

    public Subtask(String name, String description, int epicParentId) {
        super(name, description);
        this.epicParentId = epicParentId;
        this.taskType = TaskType.SUBTASK;
    }

    public int getEpicParentId() {
        return epicParentId;
    }

    @Override
    public String toString() {
        return "com.yandex.app.model.Subtask{" +
                "epicParentId=" + epicParentId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", taskId=" + taskId +
                ", status=" + status +
                ", taskType=" + taskType +
                '}';
    }
}
