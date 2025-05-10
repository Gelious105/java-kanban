package com.yandex.app.model;

import java.util.ArrayList;

public class Epic extends Task {
    protected ArrayList<Integer> subtaskIds;

    public Epic(String name, String description) {
        super(name, description);
        this.taskType = TaskType.EPIC;
        this.subtaskIds = new ArrayList<>();
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    public void addSubtaskId(int subtaskId) {
        if (subtaskId != getTaskId()){
            subtaskIds.add(subtaskId);
        }
    }

    public void removeSubtaskId(int subtaskId) {
        subtaskIds.remove(subtaskId);
    }

    public void clearSubtaskIds() {
        subtaskIds.clear();
    }

    @Override
    public String toString() {
        return "com.yandex.app.model.Epic{" +
                "subtaskIds.length=" + subtaskIds.size() +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", taskId=" + taskId +
                ", status=" + status +
                ", taskType=" + taskType +
                '}';
    }
}
